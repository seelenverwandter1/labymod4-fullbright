package org.burgerbude.labymod.addons.fullbright.v1_17.mixins;

import com.mojang.blaze3d.platform.NativeImage;
import net.labymod.api.Laby;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.burgerbude.labymod.addons.fullbright.core.event.UpdateLightmapTextureEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightTexture.class)
public class MixinLightTexture {

  @Shadow
  @Final
  private NativeImage lightPixels;

  @Shadow
  @Final
  private DynamicTexture lightTexture;
  @Shadow
  private boolean updateLightTexture;
  private boolean fullbright$updated = false;

  @Inject(method = "updateLightTexture", at = @At("HEAD"), cancellable = true)
  private void fullbright$updateLightTexture(float v, CallbackInfo ci) {
    final var event = Laby.fireEvent(new UpdateLightmapTextureEvent());
    if (event.isCancelled()) {
      if (!this.fullbright$updated) {
        this.fullbright$writeWhiteTexture();
      }
      this.fullbright$updated = true;
      this.lightTexture.upload();
      ci.cancel();
      return;
    }

    // Is need for the singleplayer, if the user is in a screen,
    // the tick method is not called and therefore the light level is not updated
    if (this.fullbright$updated) {
      this.updateLightTexture = true;
    }

    this.fullbright$updated = false;
  }

  private void fullbright$writeWhiteTexture() {
    for (int x = 0; x < 16; x++) {
      for (int y = 0; y < 16; y++) {
        this.lightPixels.setPixelRGBA(x, y, 0xFFFFFFFF);
      }
    }
  }

}
