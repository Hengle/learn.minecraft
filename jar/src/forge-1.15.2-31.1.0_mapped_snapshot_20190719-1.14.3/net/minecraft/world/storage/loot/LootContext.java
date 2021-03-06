package net.minecraft.world.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.ForgeHooks;

public class LootContext {
   private final Random random;
   private final float luck;
   private final ServerWorld world;
   private final Function<ResourceLocation, LootTable> lootTableManager;
   private final Set<LootTable> lootTables;
   private final Function<ResourceLocation, ILootCondition> field_227499_f_;
   private final Set<ILootCondition> field_227500_g_;
   private final Map<LootParameter<?>, Object> parameters;
   private final Map<ResourceLocation, LootContext.IDynamicDropProvider> field_216037_g;

   private LootContext(Random p_i225885_1_, float p_i225885_2_, ServerWorld p_i225885_3_, Function<ResourceLocation, LootTable> p_i225885_4_, Function<ResourceLocation, ILootCondition> p_i225885_5_, Map<LootParameter<?>, Object> p_i225885_6_, Map<ResourceLocation, LootContext.IDynamicDropProvider> p_i225885_7_) {
      this.lootTables = Sets.newLinkedHashSet();
      this.field_227500_g_ = Sets.newLinkedHashSet();
      this.random = p_i225885_1_;
      this.luck = p_i225885_2_;
      this.world = p_i225885_3_;
      this.lootTableManager = p_i225885_4_;
      this.field_227499_f_ = p_i225885_5_;
      this.parameters = ImmutableMap.copyOf(p_i225885_6_);
      this.field_216037_g = ImmutableMap.copyOf(p_i225885_7_);
   }

   public boolean has(LootParameter<?> p_216033_1_) {
      return this.parameters.containsKey(p_216033_1_);
   }

   public void func_216034_a(ResourceLocation p_216034_1_, Consumer<ItemStack> p_216034_2_) {
      LootContext.IDynamicDropProvider lootcontext$idynamicdropprovider = (LootContext.IDynamicDropProvider)this.field_216037_g.get(p_216034_1_);
      if (lootcontext$idynamicdropprovider != null) {
         lootcontext$idynamicdropprovider.add(this, p_216034_2_);
      }

   }

   @Nullable
   public <T> T get(LootParameter<T> p_216031_1_) {
      return this.parameters.get(p_216031_1_);
   }

   public boolean addLootTable(LootTable p_186496_1_) {
      return this.lootTables.add(p_186496_1_);
   }

   public void removeLootTable(LootTable p_186490_1_) {
      this.lootTables.remove(p_186490_1_);
   }

   public boolean func_227501_a_(ILootCondition p_227501_1_) {
      return this.field_227500_g_.add(p_227501_1_);
   }

   public void func_227503_b_(ILootCondition p_227503_1_) {
      this.field_227500_g_.remove(p_227503_1_);
   }

   public LootTable func_227502_a_(ResourceLocation p_227502_1_) {
      return (LootTable)this.lootTableManager.apply(p_227502_1_);
   }

   public ILootCondition func_227504_b_(ResourceLocation p_227504_1_) {
      return (ILootCondition)this.field_227499_f_.apply(p_227504_1_);
   }

   public Random getRandom() {
      return this.random;
   }

   public float getLuck() {
      return this.luck;
   }

   public ServerWorld getWorld() {
      return this.world;
   }

   public int getLootingModifier() {
      return ForgeHooks.getLootingLevel((Entity)this.get(LootParameters.THIS_ENTITY), (Entity)this.get(LootParameters.KILLER_ENTITY), (DamageSource)this.get(LootParameters.DAMAGE_SOURCE));
   }

   // $FF: synthetic method
   LootContext(Random p_i225886_1_, float p_i225886_2_, ServerWorld p_i225886_3_, Function p_i225886_4_, Function p_i225886_5_, Map p_i225886_6_, Map p_i225886_7_, Object p_i225886_8_) {
      this(p_i225886_1_, p_i225886_2_, p_i225886_3_, p_i225886_4_, p_i225886_5_, p_i225886_6_, p_i225886_7_);
   }

   @FunctionalInterface
   public interface IDynamicDropProvider {
      void add(LootContext var1, Consumer<ItemStack> var2);
   }

   public static enum EntityTarget {
      THIS("this", LootParameters.THIS_ENTITY),
      KILLER("killer", LootParameters.KILLER_ENTITY),
      DIRECT_KILLER("direct_killer", LootParameters.DIRECT_KILLER_ENTITY),
      KILLER_PLAYER("killer_player", LootParameters.LAST_DAMAGE_PLAYER);

      private final String targetType;
      private final LootParameter<? extends Entity> parameter;

      private EntityTarget(String p_i50476_3_, LootParameter<? extends Entity> p_i50476_4_) {
         this.targetType = p_i50476_3_;
         this.parameter = p_i50476_4_;
      }

      public LootParameter<? extends Entity> getParameter() {
         return this.parameter;
      }

      public static LootContext.EntityTarget fromString(String p_186482_0_) {
         LootContext.EntityTarget[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LootContext.EntityTarget lootcontext$entitytarget = var1[var3];
            if (lootcontext$entitytarget.targetType.equals(p_186482_0_)) {
               return lootcontext$entitytarget;
            }
         }

         throw new IllegalArgumentException("Invalid entity target " + p_186482_0_);
      }

      public static class Serializer extends TypeAdapter<LootContext.EntityTarget> {
         public void write(JsonWriter p_write_1_, LootContext.EntityTarget p_write_2_) throws IOException {
            p_write_1_.value(p_write_2_.targetType);
         }

         public LootContext.EntityTarget read(JsonReader p_read_1_) throws IOException {
            return LootContext.EntityTarget.fromString(p_read_1_.nextString());
         }
      }
   }

   public static class Builder {
      private final ServerWorld world;
      private final Map<LootParameter<?>, Object> field_216025_b = Maps.newIdentityHashMap();
      private final Map<ResourceLocation, LootContext.IDynamicDropProvider> field_216026_c = Maps.newHashMap();
      private Random rand;
      private float luck;

      public Builder(ServerWorld p_i46993_1_) {
         this.world = p_i46993_1_;
      }

      public LootContext.Builder withRandom(Random p_216023_1_) {
         this.rand = p_216023_1_;
         return this;
      }

      public LootContext.Builder withSeed(long p_216016_1_) {
         if (p_216016_1_ != 0L) {
            this.rand = new Random(p_216016_1_);
         }

         return this;
      }

      public LootContext.Builder withSeededRandom(long p_216020_1_, Random p_216020_3_) {
         if (p_216020_1_ == 0L) {
            this.rand = p_216020_3_;
         } else {
            this.rand = new Random(p_216020_1_);
         }

         return this;
      }

      public LootContext.Builder withLuck(float p_186469_1_) {
         this.luck = p_186469_1_;
         return this;
      }

      public <T> LootContext.Builder withParameter(LootParameter<T> p_216015_1_, T p_216015_2_) {
         this.field_216025_b.put(p_216015_1_, p_216015_2_);
         return this;
      }

      public <T> LootContext.Builder withNullableParameter(LootParameter<T> p_216021_1_, @Nullable T p_216021_2_) {
         if (p_216021_2_ == null) {
            this.field_216025_b.remove(p_216021_1_);
         } else {
            this.field_216025_b.put(p_216021_1_, p_216021_2_);
         }

         return this;
      }

      public LootContext.Builder withDynamicDrop(ResourceLocation p_216017_1_, LootContext.IDynamicDropProvider p_216017_2_) {
         LootContext.IDynamicDropProvider lootcontext$idynamicdropprovider = (LootContext.IDynamicDropProvider)this.field_216026_c.put(p_216017_1_, p_216017_2_);
         if (lootcontext$idynamicdropprovider != null) {
            throw new IllegalStateException("Duplicated dynamic drop '" + this.field_216026_c + "'");
         } else {
            return this;
         }
      }

      public ServerWorld getWorld() {
         return this.world;
      }

      public <T> T assertPresent(LootParameter<T> p_216024_1_) {
         T t = this.field_216025_b.get(p_216024_1_);
         if (t == null) {
            throw new IllegalArgumentException("No parameter " + p_216024_1_);
         } else {
            return t;
         }
      }

      @Nullable
      public <T> T get(LootParameter<T> p_216019_1_) {
         return this.field_216025_b.get(p_216019_1_);
      }

      public LootContext build(LootParameterSet p_216022_1_) {
         Set<LootParameter<?>> set = Sets.difference(this.field_216025_b.keySet(), p_216022_1_.getAllParameters());
         if (!set.isEmpty()) {
            throw new IllegalArgumentException("Parameters not allowed in this parameter set: " + set);
         } else {
            Set<LootParameter<?>> set1 = Sets.difference(p_216022_1_.getRequiredParameters(), this.field_216025_b.keySet());
            if (!set1.isEmpty()) {
               throw new IllegalArgumentException("Missing required parameters: " + set1);
            } else {
               Random random = this.rand;
               if (random == null) {
                  random = new Random();
               }

               MinecraftServer minecraftserver = this.world.getServer();
               return new LootContext(random, this.luck, this.world, minecraftserver.getLootTableManager()::getLootTableFromLocation, minecraftserver.func_229736_aP_()::func_227517_a_, this.field_216025_b, this.field_216026_c);
            }
         }
      }
   }
}
