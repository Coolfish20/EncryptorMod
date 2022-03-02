package me.Coolfish20.Encryptor.events;

import me.Coolfish20.Encryptor.init.Register;
import me.Coolfish20.Encryptor.tileentities.EncryptorTileEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerRightClickBlockEvent {

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event){
    if(event.getEntity() instanceof PlayerEntity){
        PlayerEntity p = (PlayerEntity) event.getEntity();
        World world = p.level;
        BlockPos pos = event.getPos();
        TileEntity tile = world.getBlockEntity(pos);
    if(tile instanceof EncryptorTileEntity) {
        Item k = p.getMainHandItem().getItem();


        if (!world.isClientSide()) {
            String empty = "";
            if (!p.isSpectator()) {
                if (tile.getTileData().getString("message") != empty) {
                    if (k == (Items.PAPER)) {
                        String key = tile.getTileData().getString("key");


                        // use .getHoverName().getString()  to get items name without square brackets
                        String name = p.getMainHandItem().getHoverName().getString(6);//6 means how many characters I want to get from the name
                        if (name.equals(key)) {
                            double x2 = tile.getBlockPos().getX();
                            double y2 = tile.getBlockPos().getY() + 1;
                            double z2 = tile.getBlockPos().getZ();


                            p.getMainHandItem().setCount(p.getMainHandItem().getCount() - 1);
                            Item message_item = Items.PAPER;
                            ItemStack message_stack = message_item.getDefaultInstance();
                            message_stack.setCount(1);
                            TextComponent message = new StringTextComponent(tile.getTileData().getString("message"));
                            message_stack.setHoverName(message);
                            ItemEntity message_stack_entity = new ItemEntity(world, x2, y2, z2);
                            message_stack_entity.setItem(message_stack);
                            world.playSound(null, tile.getBlockPos(), Register.ENCRYPTOR_DEFAULT.get(),
                                    SoundCategory.BLOCKS, 5.0f, 1.5f);
                            world.addFreshEntity(message_stack_entity);



                            tile.getTileData().remove("message");
                            tile.setChanged();
                        }


                    }


                } else {//Does not have a message
                    if (k == (Items.PAPER)) {
                        //Fix the "message" variable
                        String message = p.getMainHandItem().getHoverName().getString(40);
                        if (!message.equals(tile.getTileData().getString("key"))) {
                            //Saving the items Name to tileData
                            world.playSound(null, tile.getBlockPos(), Register.ENCRYPTOR_DEFAULT.get(), SoundCategory.BLOCKS, 5.0f, 1.5f);
                            tile.getTileEntity().getTileData().putString("message", message);
                            tile.setChanged();
                            p.getMainHandItem().setCount(p.getMainHandItem().getCount() - 1);
                            if (tile.getTileEntity().getTileData().getString("key") == empty) {
                                String n1 = String.valueOf((int) (Math.random() * 9));
                                String n2 = String.valueOf((int) (Math.random() * 9));
                                String n3 = String.valueOf((int) (Math.random() * 9));
                                String n4 = String.valueOf((int) (Math.random() * 9));
                                String n5 = String.valueOf((int) (Math.random() * 9));
                                String sum = n1 + n2 + n3 + n4 + n5;
                                //Adding the key to the tileEntity
                                tile.getTileEntity().getTileData().putString("key", sum);
                                tile.setChanged();

                                //Creating the key paper
                                double x1 = tile.getBlockPos().getX();
                                double y1 = tile.getBlockPos().getY() + 1;
                                double z1 = tile.getBlockPos().getZ();
                                Item key = Items.PAPER;
                                ItemStack stack = key.getItem().getDefaultInstance();
                                ITextComponent component = ITextComponent.nullToEmpty(tile.getTileData().getString("key"));
                                stack.setCount(1);
                                stack.setHoverName(component);
                                CompoundNBT nbt = new CompoundNBT();
                                stack.addTagElement("element", nbt);
                                ItemEntity stack_entity = new ItemEntity(world, x1, y1, z1);
                                stack_entity.setItem(stack);
                                world.addFreshEntity(stack_entity);
                            }


                        }


                    }


                }

            }

        }


    }




        }




    }
}
