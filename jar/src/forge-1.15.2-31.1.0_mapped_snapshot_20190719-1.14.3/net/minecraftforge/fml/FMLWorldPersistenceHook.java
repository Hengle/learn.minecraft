package net.minecraftforge.fml;

import com.google.common.collect.Multimap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public final class FMLWorldPersistenceHook implements WorldPersistenceHooks.WorldPersistenceHook {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Marker WORLDPERSISTENCE = MarkerManager.getMarker("WP");

   public String getModId() {
      return "fml";
   }

   public CompoundNBT getDataForWriting(SaveHandler handler, WorldInfo info) {
      CompoundNBT fmlData = new CompoundNBT();
      ListNBT modList = new ListNBT();
      ModList.get().getMods().forEach((mi) -> {
         CompoundNBT mod = new CompoundNBT();
         mod.putString("ModId", mi.getModId());
         mod.putString("ModVersion", MavenVersionStringHelper.artifactVersionToString(mi.getVersion()));
         modList.add(mod);
      });
      fmlData.put("LoadingModList", modList);
      CompoundNBT registries = new CompoundNBT();
      fmlData.put("Registries", registries);
      LOGGER.debug(WORLDPERSISTENCE, "Gathering id map for writing to world save {}", info.getWorldName());
      Iterator var6 = RegistryManager.ACTIVE.takeSnapshot(true).entrySet().iterator();

      while(var6.hasNext()) {
         Entry<ResourceLocation, ForgeRegistry.Snapshot> e = (Entry)var6.next();
         registries.put(((ResourceLocation)e.getKey()).toString(), ((ForgeRegistry.Snapshot)e.getValue()).write());
      }

      return fmlData;
   }

   public void readData(SaveHandler handler, WorldInfo info, CompoundNBT tag) {
      CompoundNBT regs;
      String modVersion;
      if (tag.contains("LoadingModList")) {
         ListNBT modList = tag.getList("LoadingModList", 10);

         for(int i = 0; i < modList.size(); ++i) {
            regs = modList.getCompound(i);
            String modId = regs.getString("ModId");
            if (!Objects.equals("minecraft", modId)) {
               modVersion = regs.getString("ModVersion");
               Optional<? extends ModContainer> container = ModList.get().getModContainerById(modId);
               if (!container.isPresent()) {
                  LOGGER.error(WORLDPERSISTENCE, "This world was saved with mod {} which appears to be missing, things may not work well", modId);
               } else if (!Objects.equals(modVersion, MavenVersionStringHelper.artifactVersionToString(((ModContainer)container.get()).getModInfo().getVersion()))) {
                  LOGGER.warn(WORLDPERSISTENCE, "This world was saved with mod {} version {} and it is now at version {}, things may not work well", modId, modVersion, MavenVersionStringHelper.artifactVersionToString(((ModContainer)container.get()).getModInfo().getVersion()));
               }
            }
         }
      }

      Multimap<ResourceLocation, ResourceLocation> failedElements = null;
      if (!tag.contains("ModItemData") && !tag.contains("ItemData")) {
         if (tag.contains("Registries")) {
            Map<ResourceLocation, ForgeRegistry.Snapshot> snapshot = new HashMap();
            regs = tag.getCompound("Registries");
            Iterator var13 = regs.keySet().iterator();

            while(var13.hasNext()) {
               modVersion = (String)var13.next();
               snapshot.put(new ResourceLocation(modVersion), ForgeRegistry.Snapshot.read(regs.getCompound(modVersion)));
            }

            failedElements = GameData.injectSnapshot(snapshot, true, true);
         }
      } else {
         StartupQuery.notify("This save predates 1.7.10, it can no longer be loaded here. Please load in 1.7.10 or 1.8 first");
         StartupQuery.abort();
      }

      if (failedElements != null && !failedElements.isEmpty()) {
         StringBuilder buf = new StringBuilder();
         buf.append("Forge Mod Loader could not load this save.\n\n").append("There are ").append(failedElements.size()).append(" unassigned registry entries in this save.\n").append("You will not be able to load until they are present again.\n\n");
         failedElements.asMap().forEach((name, entries) -> {
            buf.append("Missing ").append(name).append(":\n");
            entries.forEach((rl) -> {
               buf.append("    ").append(rl).append("\n");
            });
         });
         StartupQuery.notify(buf.toString());
         StartupQuery.abort();
      }

   }
}
