package net.minecraftforge.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;

public class ForgeCommand {
   public ForgeCommand(CommandDispatcher<CommandSource> dispatcher) {
      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)LiteralArgumentBuilder.literal("forge").then(CommandTps.register())).then(CommandTrack.register())).then(CommandEntity.register())).then(CommandGenerate.register())).then(CommandDimensions.register())).then(CommandSetDimension.register())).then(CommandModList.register()));
   }
}
