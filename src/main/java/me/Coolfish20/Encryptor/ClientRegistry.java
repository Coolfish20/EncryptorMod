package me.Coolfish20.Encryptor;

import me.Coolfish20.Encryptor.entity.SpyBombEntity;
import me.Coolfish20.Encryptor.entity.render.SpyBombEntityRenderer;
import me.Coolfish20.Encryptor.init.Register;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientRegistry {
    @SubscribeEvent//1. Register entity type 2. Register attributes(FMLCommon) 3. Register render(FMLClient)
    public void onClientSetup(FMLClientSetupEvent e){
        RenderingRegistry.registerEntityRenderingHandler(Register.BOMB.get(), SpyBombEntityRenderer::new);
    }

    @SubscribeEvent
    public void onFMLSetup(FMLCommonSetupEvent e){
        GlobalEntityTypeAttributes.put(Register.BOMB.get(), SpyBombEntity.setAtributes().build());
    }

}
