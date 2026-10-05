package net.vhill;

import net.vhill.item.ModItemGroup;
import net.vhill.item.ModItems;
import net.vhill.village.ModVillagerTrades;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VhillMod {
    public static final String MOD_ID = "vhill";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        LOGGER.info("Inicializando Vhill Mod en arquitectura Multi-loader (Architectury)!");
        ModItemGroup.register();
        ModItems.register();
        ModVillagerTrades.register();
    }
}
