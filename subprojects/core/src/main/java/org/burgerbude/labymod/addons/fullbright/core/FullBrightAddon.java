package org.burgerbude.labymod.addons.fullbright.core;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.event.Subscribe;
import net.labymod.api.models.addon.annotation.AddonListener;
import org.burgerbude.labymod.addons.fullbright.core.configuration.FullBrightConfiguration;
import org.burgerbude.labymod.addons.fullbright.core.event.UpdateLightmapTextureEvent;

import javax.inject.Singleton;

@Singleton
@AddonListener
public class FullBrightAddon extends LabyAddon<FullBrightConfiguration> {


  @Override
  protected void enable() {
    this.registerSettingCategory();
  }

  @Override
  protected Class<FullBrightConfiguration> configurationClass() {
    return FullBrightConfiguration.class;
  }

  @Subscribe
  public void onUpdateLighmapTexture(UpdateLightmapTextureEvent event) {
    if (this.configuration().enabled().get()) {
      event.setCancelled(true);
    }
  }

}
