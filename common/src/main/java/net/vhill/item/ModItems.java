package net.vhill.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.vhill.VhillMod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(VhillMod.MOD_ID, Registries.ITEM);
    private static final List<RegistrySupplier<VhillItem>> REGISTERED_VHILLS = new ArrayList<>();

    // Fresa (Regeneración)
    public static final RegistrySupplier<VhillItem> VHILL_FRESA_3K = registerVhill("vhill_fresa_3k", VhillCategory.K3, VhillFlavor.FRESA);
    public static final RegistrySupplier<VhillItem> VHILL_FRESA_12K = registerVhill("vhill_fresa_12k", VhillCategory.K12, VhillFlavor.FRESA);
    public static final RegistrySupplier<VhillItem> VHILL_FRESA_32K = registerVhill("vhill_fresa_32k", VhillCategory.K32, VhillFlavor.FRESA);

    // Menta (Velocidad)
    public static final RegistrySupplier<VhillItem> VHILL_MENTA_3K = registerVhill("vhill_menta_3k", VhillCategory.K3, VhillFlavor.MENTA);
    public static final RegistrySupplier<VhillItem> VHILL_MENTA_12K = registerVhill("vhill_menta_12k", VhillCategory.K12, VhillFlavor.MENTA);
    public static final RegistrySupplier<VhillItem> VHILL_MENTA_32K = registerVhill("vhill_menta_32k", VhillCategory.K32, VhillFlavor.MENTA);

    // Adrenalina (Fuerza)
    public static final RegistrySupplier<VhillItem> VHILL_ADRENALINA_3K = registerVhill("vhill_adrenalina_3k", VhillCategory.K3, VhillFlavor.ADRENALINA);
    public static final RegistrySupplier<VhillItem> VHILL_ADRENALINA_12K = registerVhill("vhill_adrenalina_12k", VhillCategory.K12, VhillFlavor.ADRENALINA);
    public static final RegistrySupplier<VhillItem> VHILL_ADRENALINA_32K = registerVhill("vhill_adrenalina_32k", VhillCategory.K32, VhillFlavor.ADRENALINA);

    // Wax (32k especial con múltiples efectos y único crafteable)
    public static final RegistrySupplier<WaxItem> WAX = ITEMS.register("wax", () ->
            new WaxItem(new Item.Properties().arch$tab(ModItemGroup.VHILL_TAB)));

    public static final List<RegistrySupplier<VhillItem>> ALL_VHILLS = Collections.unmodifiableList(REGISTERED_VHILLS);

    private static RegistrySupplier<VhillItem> registerVhill(String name, VhillCategory category, VhillFlavor flavor) {
        RegistrySupplier<VhillItem> item = ITEMS.register(name, () ->
                new VhillItem(category, flavor, new Item.Properties().arch$tab(ModItemGroup.VHILL_TAB)));
        REGISTERED_VHILLS.add(item);
        return item;
    }

    public static void register() {
        ITEMS.register();
    }
}
