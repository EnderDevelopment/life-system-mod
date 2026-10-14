package com.masonblomquist95.lifesystemmod;

import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameRules;

public class LifeSystem {
    public static final int MAX_LIVES = 4;
    public static final int CRITICAL_LIVES = 2;

    public static void initializePlayerLives(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.getPersistentData().putInt("lives", MAX_LIVES);
        }
    }

    public static void handlePlayerDeath(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            int lives = serverPlayer.getPersistentData().getInt("lives") - 1;
            serverPlayer.getPersistentData().putInt("lives", lives);

            if (lives <= 0) {
                serverPlayer.sendSystemMessage(Component.translatable("message.lifesystemmod.death_banned"));
                serverPlayer.setGameMode(GameRules.GameModeRule.byId("spectator"));
            } else if (lives == 1) {
                serverPlayer.setHealth(10.0f);
            }

            serverPlayer.sendSystemMessage(Component.translatable("message.lifesystemmod.lives_left", lives));
        }
    }

    public static void giveRandomLife(Player player) {
        if (player instanceof ServerPlayer serverPlayer && !serverPlayer.hasPermissions(2)) {
            int lives = serverPlayer.getPersistentData().getInt("lives") + 1;
            if (lives <= MAX_LIVES) {
                serverPlayer.getPersistentData().putInt("lives", lives);
                serverPlayer.sendSystemMessage(Component.translatable("message.lifesystemmod.lives_left", lives));
            }
        }
    }
}