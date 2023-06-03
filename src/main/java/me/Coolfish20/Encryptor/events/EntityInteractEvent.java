package me.Coolfish20.Encryptor.events;

import me.Coolfish20.Encryptor.entity.SpyBombEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.awt.*;

import static me.Coolfish20.Encryptor.entity.SpyBombEntity.TARGET_NAME;

public class EntityInteractEvent {

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract e) {
        if (!e.getPlayer().level.isClientSide() && e.getHand().equals(Hand.MAIN_HAND)) {
            if (e.getTarget() instanceof SpyBombEntity) {
                PlayerEntity p = e.getPlayer();
                if (p.getMainHandItem().hasCustomHoverName()) {
                String name = p.getMainHandItem().getHoverName().getString(9);
                e.getTarget().getEntityData().set(TARGET_NAME, name);
                p.sendMessage(ITextComponent.nullToEmpty(TextFormatting.BLUE+"Target is now "+name), p.getUUID());
                p.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 3,1);
                }
            }
        }
    }
}
