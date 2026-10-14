package com.masonblomquist95.lifesystemmod;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class LifeSystemCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("randomlife")
            .requires(source -> source.hasPermission(2))
            .executes(context -> {
                ServerPlayer player = context.getSource().getPlayer();
                if (player != null) {
                    LifeSystem.giveRandomLife(player);
                    return 1;
                }
                return 0;
            })
            .then(Commands.literal("@a")
                .executes(context -> {
                    context.getSource().getServer().getPlayerList().getPlayers().forEach(LifeSystem::giveRandomLife);
                    context.getSource().sendSuccess(() -> Component.translatable("command.lifesystemmod.random_life.description"), true);
                    return 1;
                })));
    }
}