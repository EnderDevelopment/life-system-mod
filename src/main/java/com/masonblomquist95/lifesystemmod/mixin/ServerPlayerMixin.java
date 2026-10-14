package com.masonblomquist95.lifesystemmod.mixin;

import com.masonblomquist95.lifesystemmod.LifeSystem;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.PlayerTeam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer)(Object)this;
        int lives = player.getPersistentData().getInt("lives");

        if (lives == LifeSystem.CRITICAL_LIVES) {
            PlayerTeam team = player.getScoreboard().getPlayersTeam(player.getScoreboardName());
            if (team == null) {
                team = new PlayerTeam(player.getScoreboard(), "lifesystemmod");
                player.getScoreboard().addPlayerToTeam(player.getScoreboardName(), team);
            }
            team.setColor(net.minecraft.ChatFormatting.LIGHT_PURPLE);
            team.setPrefix(Component.literal("✧ "));
        } else if (lives == LifeSystem.MAX_LIVES - 1) {
            PlayerTeam team = player.getScoreboard().getPlayersTeam(player.getScoreboardName());
            if (team == null) {
                team = new PlayerTeam(player.getScoreboard(), "lifesystemmod");
                player.getScoreboard().addPlayerToTeam(player.getScoreboardName(), team);
            }
            team.setColor(net.minecraft.ChatFormatting.BLUE);
            team.setPrefix(Component.literal("★ "));
        }
    }
}