package com.xkx.lumod.event;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

class MegaItemEvent extends PlayerEvent {
    private ItemStack stack;
    public MegaItemEvent(Player player, ItemStack itemStack) {
        super(player);
        stack = itemStack;
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }
}

@Mixin(MultiPlayerGameMode.class)
class MultiPlayerGameModeMixin {
    @Inject(method = "useItem", at = @At("HEAD"))
    public void useItem(Player p_105236_, Level p_105237_, InteractionHand p_105238_, CallbackInfoReturnable<InteractionResult> cir) {
        UseItemEvent event = new UseItemEvent(p_105236_, p_105236_.getItemInHand(p_105238_), p_105238_, p_105237_);
        MinecraftForge.EVENT_BUS.post(event);
    }
}

public class CancelSwordEvent {


}
class UseItemEvent extends MegaItemEvent{
    private final InteractionHand interactionHand;
    private final Level level;
    public UseItemEvent(Player player, ItemStack stack, InteractionHand hand, Level level) {
        super(player, stack);
        interactionHand = hand;
        this.level = level;
    }

    public InteractionHand getInteractionHand() {
        return interactionHand;
    }

    public boolean isClient() {
        return level.isClientSide;
    }

    public Level getLevel() {
        return level;
    }
}