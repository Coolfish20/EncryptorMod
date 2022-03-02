package me.Coolfish20.Encryptor;

import me.Coolfish20.Encryptor.events.BlockPlaceEvent;
import me.Coolfish20.Encryptor.events.PlayerRightClickBlockEvent;
import me.Coolfish20.Encryptor.init.Register;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Encryptor.MOD_ID)
public class Encryptor
{
    public static final String MOD_ID = "encrypted";
    public Encryptor() {

        MinecraftForge.EVENT_BUS.register(this);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        Register.BLOCKS.register(bus);
        Register.TILE_ENTITIES.register(bus);
        Register.ITEMS.register(bus);
        Register.SOUND_EVENTS.register(bus);
        MinecraftForge.EVENT_BUS.register(new BlockPlaceEvent());
        MinecraftForge.EVENT_BUS.register(new PlayerRightClickBlockEvent());
    }


}
