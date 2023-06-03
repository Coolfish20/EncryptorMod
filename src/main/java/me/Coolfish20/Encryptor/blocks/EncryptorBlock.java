package me.Coolfish20.Encryptor.blocks;

import me.Coolfish20.Encryptor.init.Register;
import me.Coolfish20.Encryptor.tileentities.EncryptorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
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


    @Override//Using these methods, until I find some better alternatives
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState state2, boolean p_196243_5_) {
        TileEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof EncryptorTileEntity) {
            String tilekey = tileEntity.getTileData().getString("key");
            String tilemessage = tileEntity.getTileData().getString("message");
            ItemStack tilestack = new ItemStack(Register.ENCRYPTOR_ITEM.get());
            if(tileEntity.getTileData().getString("key") != "") {
                CompoundNBT nbt1 = new CompoundNBT();
                tilestack.addTagElement("tilekey", nbt1);//Creating CompoundNBT and assigning the key to it
                tilestack.getTagElement("tilekey").putString("key", tilekey);
            }
            if(tileEntity.getTileData().getString("message") !="") {
                CompoundNBT nbt2 = new CompoundNBT();
                tilestack.addTagElement("tilemessage", nbt2);
                tilestack.getTagElement("tilemessage").putString("message", tilemessage);
            }
            ItemEntity tilestackentity = new ItemEntity(tileEntity.getLevel(), pos.getX(), pos.getY(), pos.getZ());
            tilestackentity.setItem(tilestack);
            tileEntity.setRemoved();
            world.addFreshEntity(tilestackentity);


        }
    }
}
