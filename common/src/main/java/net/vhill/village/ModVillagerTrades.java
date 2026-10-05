package net.vhill.village;

import dev.architectury.registry.level.entity.trade.TradeRegistry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.vhill.item.ModItems;

import java.util.function.Supplier;

public class ModVillagerTrades {
    public static void register() {
        // Nivel 1 (Novato): Vhills 3k de Fresa y Menta
        TradeRegistry.registerVillagerTrade(VillagerProfession.CLERIC, 1,
                new LazyTrade(new ItemStack(Items.EMERALD, 5), ModItems.VHILL_FRESA_3K, 8, 2, 0.05F),
                new LazyTrade(new ItemStack(Items.EMERALD, 5), ModItems.VHILL_MENTA_3K, 8, 2, 0.05F)
        );

        // Nivel 2 (Aprendiz): Vhills 3k de Adrenalina
        TradeRegistry.registerVillagerTrade(VillagerProfession.CLERIC, 2,
                new LazyTrade(new ItemStack(Items.EMERALD, 6), ModItems.VHILL_ADRENALINA_3K, 8, 4, 0.05F)
        );

        // Nivel 3 (Compañero): Vhills 12k de Fresa y Menta
        TradeRegistry.registerVillagerTrade(VillagerProfession.CLERIC, 3,
                new LazyTrade(new ItemStack(Items.EMERALD, 15), ModItems.VHILL_FRESA_12K, 6, 10, 0.05F),
                new LazyTrade(new ItemStack(Items.EMERALD, 15), ModItems.VHILL_MENTA_12K, 6, 10, 0.05F)
        );

        // Nivel 4 (Experto): Vhills 12k de Adrenalina
        TradeRegistry.registerVillagerTrade(VillagerProfession.CLERIC, 4,
                new LazyTrade(new ItemStack(Items.EMERALD, 18), ModItems.VHILL_ADRENALINA_12K, 6, 15, 0.05F)
        );

        // Nivel 5 (Maestro): Vhills 32k (todos los sabores)
        TradeRegistry.registerVillagerTrade(VillagerProfession.CLERIC, 5,
                new LazyTrade(new ItemStack(Items.EMERALD, 32), ModItems.VHILL_FRESA_32K, 4, 25, 0.05F),
                new LazyTrade(new ItemStack(Items.EMERALD, 32), ModItems.VHILL_MENTA_32K, 4, 25, 0.05F),
                new LazyTrade(new ItemStack(Items.EMERALD, 36), ModItems.VHILL_ADRENALINA_32K, 4, 30, 0.05F)
        );
    }

    public record LazyTrade(ItemStack price, Supplier<? extends Item> saleItem, int maxTrades, int xp, float priceMultiplier) implements VillagerTrades.ItemListing {
        @Override
        public MerchantOffer getOffer(Entity entity, RandomSource random) {
            return new MerchantOffer(price, new ItemStack(saleItem.get()), maxTrades, xp, priceMultiplier);
        }
    }
}
