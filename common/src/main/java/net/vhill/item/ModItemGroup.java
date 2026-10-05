package net.vhill.item;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.vhill.VhillMod;

public class ModItemGroup {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(VhillMod.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> VHILL_TAB = TABS.register("vhills", () ->
            CreativeTabRegistry.create(
                    Component.translatable("itemGroup.vhill.vhills"),
                    () -> new ItemStack(ModItems.VHILL_FRESA_3K.get())
            ));

    public static void register() {
        TABS.register();
    }
}
