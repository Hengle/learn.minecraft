package net.minecraft.network.datasync;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityDataManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Map<Class<? extends Entity>, Integer> NEXT_ID_MAP = Maps.newHashMap();
   private final Entity entity;
   private final Map<Integer, EntityDataManager.DataEntry<?>> entries = Maps.newHashMap();
   private final ReadWriteLock lock = new ReentrantReadWriteLock();
   private boolean empty = true;
   private boolean dirty;

   public EntityDataManager(Entity p_i46840_1_) {
      this.entity = p_i46840_1_;
   }

   public static <T> DataParameter<T> createKey(Class<? extends Entity> p_187226_0_, IDataSerializer<T> p_187226_1_) {
      try {
         Class<?> oclass = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
         if (!oclass.equals(p_187226_0_)) {
            if (LOGGER.isDebugEnabled()) {
               LOGGER.warn("defineId called for: {} from {}", p_187226_0_, oclass, new RuntimeException());
            } else {
               LOGGER.warn("defineId called for: {} from {}", p_187226_0_, oclass);
            }
         }
      } catch (ClassNotFoundException var5) {
      }

      int j;
      if (NEXT_ID_MAP.containsKey(p_187226_0_)) {
         j = (Integer)NEXT_ID_MAP.get(p_187226_0_) + 1;
      } else {
         int i = 0;
         Class oclass1 = p_187226_0_;

         while(oclass1 != Entity.class) {
            oclass1 = oclass1.getSuperclass();
            if (NEXT_ID_MAP.containsKey(oclass1)) {
               i = (Integer)NEXT_ID_MAP.get(oclass1) + 1;
               break;
            }
         }

         j = i;
      }

      if (j > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + j + "! (Max is " + 254 + ")");
      } else {
         NEXT_ID_MAP.put(p_187226_0_, j);
         return p_187226_1_.createKey(j);
      }
   }

   public <T> void register(DataParameter<T> p_187214_1_, T p_187214_2_) {
      int i = p_187214_1_.getId();
      if (i > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is " + 254 + ")");
      } else if (this.entries.containsKey(i)) {
         throw new IllegalArgumentException("Duplicate id value for " + i + "!");
      } else if (DataSerializers.getSerializerId(p_187214_1_.getSerializer()) < 0) {
         throw new IllegalArgumentException("Unregistered serializer " + p_187214_1_.getSerializer() + " for " + i + "!");
      } else {
         this.setEntry(p_187214_1_, p_187214_2_);
      }
   }

   private <T> void setEntry(DataParameter<T> p_187222_1_, T p_187222_2_) {
      EntityDataManager.DataEntry<T> dataentry = new EntityDataManager.DataEntry(p_187222_1_, p_187222_2_);
      this.lock.writeLock().lock();
      this.entries.put(p_187222_1_.getId(), dataentry);
      this.empty = false;
      this.lock.writeLock().unlock();
   }

   private <T> EntityDataManager.DataEntry<T> getEntry(DataParameter<T> p_187219_1_) {
      this.lock.readLock().lock();

      EntityDataManager.DataEntry dataentry;
      try {
         dataentry = (EntityDataManager.DataEntry)this.entries.get(p_187219_1_.getId());
      } catch (Throwable var9) {
         CrashReport crashreport = CrashReport.makeCrashReport(var9, "Getting synched entity data");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Synched entity data");
         crashreportcategory.addDetail("Data ID", (Object)p_187219_1_);
         throw new ReportedException(crashreport);
      } finally {
         this.lock.readLock().unlock();
      }

      return dataentry;
   }

   public <T> T get(DataParameter<T> p_187225_1_) {
      return this.getEntry(p_187225_1_).getValue();
   }

   public <T> void set(DataParameter<T> p_187227_1_, T p_187227_2_) {
      EntityDataManager.DataEntry<T> dataentry = this.getEntry(p_187227_1_);
      if (ObjectUtils.notEqual(p_187227_2_, dataentry.getValue())) {
         dataentry.setValue(p_187227_2_);
         this.entity.notifyDataManagerChange(p_187227_1_);
         dataentry.setDirty(true);
         this.dirty = true;
      }

   }

   public boolean isDirty() {
      return this.dirty;
   }

   public static void writeEntries(List<EntityDataManager.DataEntry<?>> p_187229_0_, PacketBuffer p_187229_1_) throws IOException {
      if (p_187229_0_ != null) {
         int i = 0;

         for(int j = p_187229_0_.size(); i < j; ++i) {
            writeEntry(p_187229_1_, (EntityDataManager.DataEntry)p_187229_0_.get(i));
         }
      }

      p_187229_1_.writeByte(255);
   }

   @Nullable
   public List<EntityDataManager.DataEntry<?>> getDirty() {
      List<EntityDataManager.DataEntry<?>> list = null;
      if (this.dirty) {
         this.lock.readLock().lock();
         Iterator var2 = this.entries.values().iterator();

         while(var2.hasNext()) {
            EntityDataManager.DataEntry<?> dataentry = (EntityDataManager.DataEntry)var2.next();
            if (dataentry.isDirty()) {
               dataentry.setDirty(false);
               if (list == null) {
                  list = Lists.newArrayList();
               }

               list.add(dataentry.copy());
            }
         }

         this.lock.readLock().unlock();
      }

      this.dirty = false;
      return list;
   }

   @Nullable
   public List<EntityDataManager.DataEntry<?>> getAll() {
      List<EntityDataManager.DataEntry<?>> list = null;
      this.lock.readLock().lock();

      EntityDataManager.DataEntry dataentry;
      for(Iterator var2 = this.entries.values().iterator(); var2.hasNext(); list.add(dataentry.copy())) {
         dataentry = (EntityDataManager.DataEntry)var2.next();
         if (list == null) {
            list = Lists.newArrayList();
         }
      }

      this.lock.readLock().unlock();
      return list;
   }

   private static <T> void writeEntry(PacketBuffer p_187220_0_, EntityDataManager.DataEntry<T> p_187220_1_) throws IOException {
      DataParameter<T> dataparameter = p_187220_1_.getKey();
      int i = DataSerializers.getSerializerId(dataparameter.getSerializer());
      if (i < 0) {
         throw new EncoderException("Unknown serializer type " + dataparameter.getSerializer());
      } else {
         p_187220_0_.writeByte(dataparameter.getId());
         p_187220_0_.writeVarInt(i);
         dataparameter.getSerializer().write(p_187220_0_, p_187220_1_.getValue());
      }
   }

   @Nullable
   public static List<EntityDataManager.DataEntry<?>> readEntries(PacketBuffer p_187215_0_) throws IOException {
      ArrayList list = null;

      short i;
      while((i = p_187215_0_.readUnsignedByte()) != 255) {
         if (list == null) {
            list = Lists.newArrayList();
         }

         int j = p_187215_0_.readVarInt();
         IDataSerializer<?> idataserializer = DataSerializers.getSerializer(j);
         if (idataserializer == null) {
            throw new DecoderException("Unknown serializer type " + j);
         }

         list.add(makeDataEntry(p_187215_0_, i, idataserializer));
      }

      return list;
   }

   private static <T> EntityDataManager.DataEntry<T> makeDataEntry(PacketBuffer p_198167_0_, int p_198167_1_, IDataSerializer<T> p_198167_2_) {
      return new EntityDataManager.DataEntry(p_198167_2_.createKey(p_198167_1_), p_198167_2_.read(p_198167_0_));
   }

   @OnlyIn(Dist.CLIENT)
   public void setEntryValues(List<EntityDataManager.DataEntry<?>> p_187218_1_) {
      this.lock.writeLock().lock();
      Iterator var2 = p_187218_1_.iterator();

      while(var2.hasNext()) {
         EntityDataManager.DataEntry<?> dataentry = (EntityDataManager.DataEntry)var2.next();
         EntityDataManager.DataEntry<?> dataentry1 = (EntityDataManager.DataEntry)this.entries.get(dataentry.getKey().getId());
         if (dataentry1 != null) {
            this.setEntryValue(dataentry1, dataentry);
            this.entity.notifyDataManagerChange(dataentry.getKey());
         }
      }

      this.lock.writeLock().unlock();
      this.dirty = true;
   }

   @OnlyIn(Dist.CLIENT)
   private <T> void setEntryValue(EntityDataManager.DataEntry<T> p_187224_1_, EntityDataManager.DataEntry<?> p_187224_2_) {
      if (!Objects.equals(p_187224_2_.key.getSerializer(), p_187224_1_.key.getSerializer())) {
         throw new IllegalStateException(String.format("Invalid entity data item type for field %d on entity %s: old=%s(%s), new=%s(%s)", p_187224_1_.key.getId(), this.entity, p_187224_1_.value, p_187224_1_.value.getClass(), p_187224_2_.value, p_187224_2_.value.getClass()));
      } else {
         p_187224_1_.setValue(p_187224_2_.getValue());
      }
   }

   public boolean isEmpty() {
      return this.empty;
   }

   public void setClean() {
      this.dirty = false;
      this.lock.readLock().lock();
      Iterator var1 = this.entries.values().iterator();

      while(var1.hasNext()) {
         EntityDataManager.DataEntry<?> dataentry = (EntityDataManager.DataEntry)var1.next();
         dataentry.setDirty(false);
      }

      this.lock.readLock().unlock();
   }

   public static class DataEntry<T> {
      private final DataParameter<T> key;
      private T value;
      private boolean dirty;

      public DataEntry(DataParameter<T> p_i47010_1_, T p_i47010_2_) {
         this.key = p_i47010_1_;
         this.value = p_i47010_2_;
         this.dirty = true;
      }

      public DataParameter<T> getKey() {
         return this.key;
      }

      public void setValue(T p_187210_1_) {
         this.value = p_187210_1_;
      }

      public T getValue() {
         return this.value;
      }

      public boolean isDirty() {
         return this.dirty;
      }

      public void setDirty(boolean p_187208_1_) {
         this.dirty = p_187208_1_;
      }

      public EntityDataManager.DataEntry<T> copy() {
         return new EntityDataManager.DataEntry(this.key, this.key.getSerializer().copyValue(this.value));
      }
   }
}
