package net.vhill.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VhillItem extends Item {
    protected final VhillCategory category;
    @Nullable
    protected final VhillFlavor flavor;

    public VhillItem(VhillCategory category, @Nullable VhillFlavor flavor, Properties properties) {
        super(properties.durability(category.getMaxDamage()));
        this.category = category;
        this.flavor = flavor;
    }

    public VhillCategory getCategory() {
        return category;
    }

    @Nullable
    public VhillFlavor getFlavor() {
        return flavor;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 25;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseTicks) {
        if (level.isClientSide && livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            if (player.isUsingItem() && player.getTicksUsingItem() == 1) {
                level.playSound(
                        player,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.SNIFFER_SNIFFING,
                        SoundSource.PLAYERS,
                        0.6F,
                        1.2F
                );
            }
        }
        super.onUseTick(level, livingEntity, stack, remainingUseTicks);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (!level.isClientSide) {
            int currentDamage = stack.getDamageValue();
            int maxDamage = stack.getMaxDamage();

            if (currentDamage < maxDamage) {
                stack.setDamageValue(currentDamage + 1);
                applyActiveEffects(livingEntity);
            } else {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
            }
        }

        if (level.isClientSide) {
            spawnInhaleParticles(level, livingEntity);
        }

        return stack;
    }

    protected void applyActiveEffects(LivingEntity user) {
        if (this.flavor != null) {
            user.addEffect(this.flavor.createEffectInstance());
        }
    }

    protected void spawnInhaleParticles(Level level, LivingEntity user) {
        for (int i = 0; i < 5; i++) {
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

        if (flavor != null) {
            tooltipComponents.add(
                    Component.translatable(
                            "tooltip.vhill.flavor",
                            Component.translatable("flavor.vhill." + flavor.getId())
                    ).withStyle(flavor.getFormatting())
            );
        }

        tooltipComponents.add(
                Component.translatable(
                        "tooltip.vhill.category",
                        category.getId()
                ).withStyle(ChatFormatting.GRAY)
        );

        if (remainingUses > 0) {
            tooltipComponents.add(
                    Component.translatable(
                            "tooltip.vhill.uses_left",
                            remainingUses,
                            maxDamage
                    ).withStyle(ChatFormatting.GREEN)
            );
        } else {
            tooltipComponents.add(
                    Component.translatable("tooltip.vhill.exhausted")
                            .withStyle(ChatFormatting.RED, ChatFormatting.BOLD)
            );
        }

        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }
}