package me.Coolfish20.Encryptor.events;

import me.Coolfish20.Encryptor.init.Register;
import me.Coolfish20.Encryptor.tileentities.EncryptorTileEntity;
import me.Coolfish20.Encryptor.tileentities.RecorderTileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.ObjectUtils;

import javax.swing.text.html.parser.TagElement;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class BlockPlaceEvent {

        @SubscribeEvent
        public void onBlockPlaceEvent(BlockEvent.EntityPlaceEvent event){
            Entity e = event.getEntity();
            if(e instanceof PlayerEntity) {
                PlayerEntity p = (PlayerEntity) e.getEntity();
                BlockPos pos = event.getPos();
                TileEntity tile = event.getWorld().getBlockEntity(pos);
                if (tile instanceof EncryptorTileEntity) {
                    try {
                        if (p.getMainHandItem().getItem() == Register.ENCRYPTOR_ITEM.get()) {//Clean up this section
                            //This is to prevent huge errors in the logs
                            //Message
                            String message = p.getMainHandItem().getTagElement("tilemessage").getString("message");
                            tile.getTileData().putString("message", message);
                            //Key
                            String key = p.getMainHandItem().getTagElement("tilekey").getString("key");
                            tile.getTileData().putString("key", key);
                            tile.setChanged();

                            //Offhand
                        } else {
                            if (p.getOffhandItem().getItem() == Register.ENCRYPTOR_ITEM.get()) {

                                //Message
                                String message2 = p.getOffhandItem().getTagElement("tilemessage").getString("message");
                                tile.getTileData().putString("message", message2);
                                //Key
                                String key2 = p.getOffhandItem().getTagElement("tilekey").getString("key");
                                tile.getTileData().putString("key", key2);
                                tile.setChanged();
                            }
                        }
                    } catch (NullPointerException ignored) {
                    }
                }
                //Recorder Tile
                try{
                if (tile instanceof RecorderTileEntity) {
                    if (p.getMainHandItem().getItem() == Register.RECORDER_ITEM.get()) {//Fix the main hand
                        ItemStack recorder_item = p.getMainHandItem();//Always use getMainHandItem()/offhandItem() method, when you want to get data from an item

                        int message_number1 = recorder_item.getTagElement("message_number").getInt("message_number");
                        if (message_number1 != 0) {
                            tile.getTileData().putInt("message_number", message_number1);
                            for (int f = 0; f < message_number1; f++) {
                                String message = String.valueOf(recorder_item.getTagElement("recordings").getString("message_" + (f+1)));
                                tile.getTileData().putString("message_" + (f+1), message);
                            }
                            tile.setChanged();
                        }
                    }
                    //Offhand
                    if (p.getOffhandItem().getItem() == Register.RECORDER_ITEM.get()) {
                        ItemStack recorder_item = p.getOffhandItem();
                        int message_number1 = recorder_item.getTagElement("message_number").getInt("message_number");
                        if (message_number1 != 0) {
                            tile.getTileData().putInt("message_number", message_number1);
                            for (int i = 0; i < message_number1; i++) {
                                String message = String.valueOf(recorder_item.getTagElement("recordings").getString("message_" + (i+1)));
                                tile.getTileData().putString("message_" + (i+1), message);
                            }
                            tile.setChanged();
                        }
                    }
                }
            }catch (NullPointerException ignored){}



        }
    }
}