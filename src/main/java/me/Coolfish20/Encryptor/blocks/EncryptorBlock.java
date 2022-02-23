package me.Coolfish20.Encryptor.blocks;

import me.Coolfish20.Encryptor.init.Register;
import me.Coolfish20.Encryptor.tileentities.EncryptorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class EncryptorBlock extends Block {


    public EncryptorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Override
    public EncryptorTileEntity createTileEntity(BlockState state, IBlockReader reader){

        return new EncryptorTileEntity();
    }




    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity p, Hand hand, BlockRayTraceResult hit){
    Item k = p.getMainHandItem().getItem();
    TileEntity tile = world.getBlockEntity(pos);

    if(!world.isClientSide()){
        String empty = "";
        if(tile.getTileData().getString("message") != empty){
                       if (k == (Items.PAPER)) {
                        String key = tile.getTileData().getString("key");
                        System.out.println("2 IF!");

                        //Name string for items is indexed by 6 (in this case)
                        // use .getHoverName().getString()  to get items name without square brackets
                        String name = p.getMainHandItem().getHoverName().getString(6);
                        if (name.equals(key)) {
                            System.out.println("3 IF!");
                            double x2 = tile.getBlockPos().getX();
                            double y2 = tile.getBlockPos().getY() + 1;
                            double z2 = tile.getBlockPos().getZ();



                            p.getMainHandItem().setCount(p.getMainHandItem().getCount()-1);
                            Item message_item = Items.PAPER;
                            ItemStack message_stack = message_item.getDefaultInstance();
                            message_stack.setCount(1);
                            TextComponent message = new StringTextComponent(tile.getTileData().getString("message"));
                            message_stack.setHoverName(message);
                            ItemEntity message_stack_entity = new ItemEntity(world, x2, y2, z2);
                            message_stack_entity.setItem(message_stack);
                            world.addFreshEntity(message_stack_entity);


                            tile.getTileData().remove("message");
                            tile.getLevel().getChunkAt(pos).markUnsaved();
                            tile.setChanged();
                            System.out.println("MESSAGE DECRYPTED!");
                        }






                    }



        } else {//Does not have a message
            System.out.println("MESSAGE RECEIVED!");
                    if (k == (Items.PAPER)) {
                        //Fix the "message" variable
                        String message = p.getMainHandItem().getHoverName().getString(40);
                        if(!message.equals(tile.getTileData().getString("key"))) {
                            //Saving the items Name to tileData
                            System.out.println("VALID ITEM!");
                            tile.getTileEntity().getTileData().putString("message", message);
                            tile.getLevel().getChunkAt(pos).markUnsaved();
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
                                tile.getLevel().getChunkAt(pos).markUnsaved();
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
                                ItemEntity stack_entity = new ItemEntity(world, x1, y1, z1);
                                stack_entity.setItem(stack);
                                world.addFreshEntity(stack_entity);
                            }


                        }


                    }





        }

        
    }


        return ActionResultType.SUCCESS;
    }

}
