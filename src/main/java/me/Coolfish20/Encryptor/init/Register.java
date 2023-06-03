package me.Coolfish20.Encryptor.init;

import me.Coolfish20.Encryptor.Encryptor;
import me.Coolfish20.Encryptor.blocks.EncryptorBlock;
import me.Coolfish20.Encryptor.blocks.RecorderBlock;
import me.Coolfish20.Encryptor.entity.SpyBombEntity;
import me.Coolfish20.Encryptor.items.BombCreateEgg;
import me.Coolfish20.Encryptor.items.RecorderTapeItem;
import me.Coolfish20.Encryptor.tileentities.EncryptorTileEntity;
import me.Coolfish20.Encryptor.tileentities.RecorderTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Properties;
import java.util.Random;
import java.util.function.Supplier;

public class Register {

    //Registering Encryptor BLOCK
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Encryptor.MOD_ID);
    public static final RegistryObject<EncryptorBlock> ENCRYPTOR = BLOCKS.register("encryptor_block",
             ()-> new EncryptorBlock(AbstractBlock.Properties.of(Material.METAL).sound(SoundType.ANVIL).strength(5.0f)));

    //Recorder block
    public static final RegistryObject<RecorderBlock> RECORDER = BLOCKS.register("recorder_block",
            ()-> new RecorderBlock(AbstractBlock.Properties.of(Material.METAL).sound(SoundType.ANVIL).strength(4.0f)));



    //Registering encryptor BlockItem
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Encryptor.MOD_ID);
    public static final RegistryObject<BlockItem> ENCRYPTOR_ITEM = ITEMS.register("encryptor_block",
            ()-> new BlockItem(ENCRYPTOR.get(), new Item.Properties().tab(ItemGroup.TAB_REDSTONE).stacksTo(1)));
    //Recorder BlockItem
    public static final RegistryObject<BlockItem> RECORDER_ITEM = ITEMS.register("recorder_block",
            ()-> new BlockItem(RECORDER.get(), new Item.Properties().tab(ItemGroup.TAB_REDSTONE).stacksTo(1)));

    public static final RegistryObject<RecorderTapeItem> RECORDER_TAPE_ITEM= ITEMS.register("recorder_tape",
            ()-> new RecorderTapeItem(new Item.Properties().tab(ItemGroup.TAB_REDSTONE).stacksTo(1)));

    //Registering encryptor tile
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Encryptor.MOD_ID);

    public static RegistryObject<TileEntityType<EncryptorTileEntity>> ENCRYPTOR_TILE_ENTITY = TILE_ENTITIES.register("encryptor_tile_entity",
            ()-> TileEntityType.Builder.of(EncryptorTileEntity::new, ENCRYPTOR.get()).build(null));

    //Recorder tile
    public static RegistryObject<TileEntityType<RecorderTileEntity>> RECORDER_TILE_ENTITY = TILE_ENTITIES.register("recorder_tile_entity",
            ()-> TileEntityType.Builder.of(RecorderTileEntity::new, RECORDER.get()).build(null));

    //Registering soundEvents
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Encryptor.MOD_ID);

    //Default Encryptor sound
    public static RegistryObject<SoundEvent> ENCRYPTOR_DEFAULT = SOUND_EVENTS.register("encryptor_default",
            ()-> new SoundEvent(new ResourceLocation(Encryptor.MOD_ID, "encryptor_default")));


    public static final DeferredRegister<EntityType<?>> ENTITY = DeferredRegister.create(ForgeRegistries.ENTITIES,
            Encryptor.MOD_ID);

    public static RegistryObject<EntityType<SpyBombEntity>> BOMB = ENTITY.register("spy_bomb",()->
            EntityType.Builder.of(SpyBombEntity::new, EntityClassification.MISC).sized(0.2f,0.2f)
                    .build(new ResourceLocation(Encryptor.MOD_ID,"spy_bomb").toString()));

    public static RegistryObject<Item> BOMB_SPAWN = ITEMS.register("bomb_spawn_egg", ()->
            new BombCreateEgg(new Item.Properties().tab(ItemGroup.TAB_COMBAT)));


}
