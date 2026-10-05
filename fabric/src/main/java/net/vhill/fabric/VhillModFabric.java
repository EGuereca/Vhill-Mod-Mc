package net.vhill.fabric;

import net.fabricmc.api.ModInitializer;
import net.vhill.VhillMod;

public class VhillModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VhillMod.init();
    }
}
