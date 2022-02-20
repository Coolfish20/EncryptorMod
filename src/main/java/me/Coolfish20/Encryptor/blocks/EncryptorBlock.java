package me.Coolfish20.Encryptor.blocks;

import me.Coolfish20.Encryptor.tileentities.EncryptorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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
                System.out.println("1 IF!");
                    if (k == (Items.PAPER)) {
                        StringTextComponent key = new StringTextComponent(tile.getTileData().getString("key"));
                        System.out.println("2 IF!");
                        ITextComponent name = k.getDefaultInstance().getDisplayName();
                        if (name == key) {
                            System.out.println("3 IF!");
                            Item message = Items.PAPER;
                            ItemStack stack2 = message.getItem().getDefaultInstance();
                            ITextComponent component1 = ITextComponent.nullToEmpty(tile.getTileData().getString("message"));
                            stack2.setCount(1);
                            stack2.setHoverName(component1);
                            ItemEntity message_entity = new ItemEntity(world, tile.getBlockPos().getX(), tile.getBlockPos().getY()+1, tile.getBlockPos().getZ());
                            message_entity.setItem(stack2);
                            tile.getTileData().remove("message");
                            world.addFreshEntity(message_entity);
                            tile.setChanged();
                            System.out.println("MESSAGE DECRYPTED!");
                        }






                    }



        } else {//Does not have a message
            System.out.println("MESSAGE RECEIVED!");
                    if (k == (Items.PAPER)) {
                        //Saving the items Name to tileData
                        System.out.println("VALID ITEM!");
                        String message = k.getItem().getName(k.getDefaultInstance()).toString();
                        tile.getTileEntity().getTileData().putString("message", message);
                        tile.setChanged();
                        p.getMainHandItem().setCount(p.getMainHandItem().getCount()-1);
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
                            double y1 = tile.getBlockPos().getY()+1;
                            double z1 = tile.getBlockPos().getZ();
                            Item key = Items.PAPER;
                            ItemStack stack = key.getItem().getDefaultInstance();
                            ITextComponent component = ITextComponent.nullToEmpty(tile.getTileData().getString("key"));
                            stack.setCount(1);
                            stack.setHoverName(component);
                            ItemEntity stack_entity = new ItemEntity(world, x1,y1,z1);
                            stack_entity.setItem(stack);
                            world.addFreshEntity(stack_entity);
                        }

                    }





        }

        
    }


        return ActionResultType.SUCCESS;
    }

}
