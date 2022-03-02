package me.Coolfish20.Encryptor.init;

import me.Coolfish20.Encryptor.Encryptor;
import me.Coolfish20.Encryptor.blocks.EncryptorBlock;
import me.Coolfish20.Encryptor.tileentities.EncryptorTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Register {

    //Registering Encryptor BLOCK
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Encryptor.MOD_ID);
    public static final RegistryObject<EncryptorBlock> ENCRYPTOR = BLOCKS.register("encryptor_block",
            ()-> new EncryptorBlock(AbstractBlock.Properties.of(Material.METAL).sound(SoundType.ANVIL).strength(5.0f)));
    //Registering encryptor BlockItem
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Encryptor.MOD_ID);
    public static final RegistryObject<BlockItem> ENCRYPTOR_ITEM = ITEMS.register("encryptor_block",
            ()-> new BlockItem(ENCRYPTOR.get(), new Item.Properties().tab(ItemGroup.TAB_REDSTONE).stacksTo(1)));

    //Registering encryptor tile
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Encryptor.MOD_ID);

    public static RegistryObject<TileEntityType<EncryptorTileEntity>> ENCRYPTOR_TILE_ENTITY = TILE_ENTITIES.register("encryptor_tile_entity",
            ()-> TileEntityType.Builder.of(EncryptorTileEntity::new, ENCRYPTOR.get()).build(null));


    //Registering soundEvents
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Encryptor.MOD_ID);

    //Default Encryptor sound
    public static RegistryObject<SoundEvent> ENCRYPTOR_DEFAULT = SOUND_EVENTS.register("encryptor_default",
            ()-> new SoundEvent(new ResourceLocation(Encryptor.MOD_ID, "encryptor_default")));



}
