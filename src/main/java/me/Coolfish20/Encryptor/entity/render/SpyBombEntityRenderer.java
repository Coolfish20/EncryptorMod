package me.Coolfish20.Encryptor.entity.render;

import me.Coolfish20.Encryptor.Encryptor;
import me.Coolfish20.Encryptor.entity.SpyBombEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;


public class SpyBombEntityRenderer extends MobRenderer<SpyBombEntity, SpyBombModel<SpyBombEntity>> {
    public SpyBombEntityRenderer(EntityRendererManager p_i50961_1_) {
        super(p_i50961_1_, new SpyBombModel(), 0.8f);
    }

    @Override
    public ResourceLocation getTextureLocation(SpyBombEntity mob) {

        return SPY_LOCATION;
    }


    private static final ResourceLocation SPY_LOCATION = new ResourceLocation(Encryptor.MOD_ID, "textures/entity/encrypted/spy_bomb.png");

}
