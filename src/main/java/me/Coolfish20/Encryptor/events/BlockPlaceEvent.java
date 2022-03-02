package me.Coolfish20.Encryptor.events;

import me.Coolfish20.Encryptor.init.Register;
import me.Coolfish20.Encryptor.tileentities.EncryptorTileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

    public class BlockPlaceEvent {

        @SubscribeEvent
        public void onBlockPlaceEvent(BlockEvent.EntityPlaceEvent event){
            Entity e = event.getEntity();
            if(e instanceof PlayerEntity){
                PlayerEntity p = (PlayerEntity) e.getEntity();
                BlockPos pos = event.getPos();
                TileEntity tile = event.getWorld().getBlockEntity(pos);
                if(tile instanceof EncryptorTileEntity){
                    if(p.getMainHandItem().getItem() == Register.ENCRYPTOR_ITEM.get()) {
                        try{//This is to prevent huge errors in the logs
                            //Message
                            String message = p.getMainHandItem().getTagElement("tilemessage").getString("message");
                            tile.getTileData().putString("message", message);

                        }catch(NullPointerException ignored){
                            //Doing nothing
                        }
                        try {
                            //Key
                                String key = p.getMainHandItem().getTagElement("tilekey").getString("key");
                                tile.getTileData().putString("key", key);

                        }catch (NullPointerException ignored){

                        }
                        tile.setChanged();

                    //Offhand
                    } else{
                    if(p.getOffhandItem().getItem() == Register.ENCRYPTOR_ITEM.get()) {
                    try {
                        //Message
                            String message2 = p.getOffhandItem().getTagElement("tilemessage").getString("message");
                            tile.getTileData().putString("message", message2);

                    }catch (NullPointerException ignored){

                    }
                    try{
                        //Key
                        String key2 = p.getOffhandItem().getTagElement("tilekey").getString("key");
                        tile.getTileData().putString("key", key2);

                    }catch (NullPointerException ignored){

                    }

                        tile.setChanged();

                    }
                    }







            }

            }
        }
    }






