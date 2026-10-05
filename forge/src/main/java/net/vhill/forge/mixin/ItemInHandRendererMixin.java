package net.vhill.forge.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.vhill.item.VhillItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Inject(
            method = "renderArmWithItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
                    shift = At.Shift.BEFORE
            )
    )
    private void vhill$applyInhalePose(AbstractClientPlayer player, float partialTick, float pitch, InteractionHand hand,
                                       float swingProgress, ItemStack stack, float equipProgress, PoseStack poseStack,
                                       MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        if (stack.getItem() instanceof VhillItem) {
            if (player.isUsingItem() && player.getUseItem() == stack) {
                poseStack.translate(0.15F, -0.20F, -0.40F);
                poseStack.mulPose(Axis.XP.rotationDegrees(-40.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(20.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(10.0F));
            }
        }
    }
}