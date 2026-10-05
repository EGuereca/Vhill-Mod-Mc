package net.vhill.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.vhill.VhillMod;

@Mod(VhillMod.MOD_ID)
public class VhillModForge {
    public VhillModForge() {
        EventBuses.registerModEventBus(VhillMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        VhillMod.init();
    }
}
