package net.vhill.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WaxItem extends VhillItem {

    public WaxItem(Properties properties) {
        super(VhillCategory.K32, null, properties);
    }

    @Override
    protected void applyActiveEffects(LivingEntity user) {
        // Regeneración, Fuerza, Velocidad, Náusea y Visión Nocturna (todos por 10 segundos = 200 ticks)
        user.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
        user.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0));
        user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0));
        user.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
        user.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 0));
    }

    @Override
    protected void spawnInhaleParticles(Level level, LivingEntity user) {
        // Emisión de vapor denso místico combinando humo y aliento de dragón
        for (int i = 0; i < 4; i++) {
            level.addParticle(
                    ParticleTypes.DRAGON_BREATH,
                    user.getX() + (level.random.nextDouble() - 0.5) * 0.2,
                    user.getEyeY() - 0.1,
                    user.getZ() + (level.random.nextDouble() - 0.5) * 0.2,
                    (level.random.nextDouble() - 0.5) * 0.02,
                    0.03 + level.random.nextDouble() * 0.02,
                    (level.random.nextDouble() - 0.5) * 0.02
            );
        }
        for (int i = 0; i < 3; i++) {
            level.addParticle(
                    ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    user.getX() + (level.random.nextDouble() - 0.5) * 0.2,
                    user.getEyeY() - 0.1,
                    user.getZ() + (level.random.nextDouble() - 0.5) * 0.2,
                    (level.random.nextDouble() - 0.5) * 0.02,
                    0.05 + level.random.nextDouble() * 0.02,
                    (level.random.nextDouble() - 0.5) * 0.02
            );
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        int currentDamage = stack.getDamageValue();
        int maxDamage = stack.getMaxDamage();
        int remainingUses = maxDamage - currentDamage;

        tooltipComponents.add(Component.translatable("tooltip.vhill.wax_flavor").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
        tooltipComponents.add(Component.translatable("tooltip.vhill.wax_effects").withStyle(ChatFormatting.DARK_PURPLE));
        tooltipComponents.add(Component.translatable("tooltip.vhill.category", category.getId()).withStyle(ChatFormatting.YELLOW));

        if (remainingUses > 0) {
            tooltipComponents.add(Component.translatable("tooltip.vhill.uses_left", remainingUses, maxDamage).withStyle(ChatFormatting.GREEN));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.vhill.exhausted").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
        }
    }
}
