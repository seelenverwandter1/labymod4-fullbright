package org.burgerbude.labymod.addons.fullbright.v1_8.mixins;


import net.labymod.api.Laby;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.burgerbude.labymod.addons.fullbright.core.event.UpdateLightmapTextureEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

  @Shadow
  @Final
  private DynamicTexture lightmapTexture;

  @Shadow
  private boolean lightmapUpdateNeeded;
  private boolean fullbright$updated = false;

  @Inject(
          method = "updateLightmap",
          at = @At("HEAD"),
          cancellable = true
  )
  private void fullbright$updateLightTexture(float v, CallbackInfo ci) {
    UpdateLightmapTextureEvent event = Laby.fireEvent(new UpdateLightmapTextureEvent());
    if (event.isCancelled()) {
      if (!this.fullbright$updated) {
        this.fullbright$writeWhiteTexture();
      }
      this.fullbright$updated = true;
      this.lightmapTexture.updateDynamicTexture();
      ci.cancel();
      return;
    }

    if (this.fullbright$updated) {
      this.lightmapUpdateNeeded = true;
    }

    this.fullbright$updated = false;

  }

  private void fullbright$writeWhiteTexture() {
    for (int i = 0; i < 256; i++) {
      this.lightmapTexture.getTextureData()[i] = 0xFFFFFFFF;
    }
  }

}
