package com.masonblomquist95.lifesystemmod.mixin;

import com.masonblomquist95.lifesystemmod.LifeSystem;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        LifeSystem.initializePlayerLives((Player)(Object)this);
    }

    @Inject(method = "die", at = @At("HEAD"))
    private void onDeath(CallbackInfo ci) {
        LifeSystem.handlePlayerDeath((Player)(Object)this);
    }
}