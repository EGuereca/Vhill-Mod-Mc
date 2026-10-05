package net.vhill.item;

import net.minecraft.ChatFormatting;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public enum VhillFlavor {
    FRESA("fresa", MobEffects.REGENERATION, 200, 0, ChatFormatting.LIGHT_PURPLE),
    MENTA("menta", MobEffects.MOVEMENT_SPEED, 200, 0, ChatFormatting.AQUA),
    ADRENALINA("adrenalina", MobEffects.DAMAGE_BOOST, 200, 0, ChatFormatting.GOLD);

    private final String id;
    private final MobEffect effect;
    private final int duration; // In ticks (200 ticks = 10 seconds)
    private final int amplifier;
    private final ChatFormatting formatting;

    VhillFlavor(String id, MobEffect effect, int duration, int amplifier, ChatFormatting formatting) {
        this.id = id;
        this.effect = effect;
        this.duration = duration;
        this.amplifier = amplifier;
        this.formatting = formatting;
    }

    public String getId() {
        return id;
    }

    public MobEffect getEffect() {
        return effect;
    }

    public int getDuration() {
        return duration;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public ChatFormatting getFormatting() {
        return formatting;
    }

    public MobEffectInstance createEffectInstance() {
        return new MobEffectInstance(this.effect, this.duration, this.amplifier);
    }
}
