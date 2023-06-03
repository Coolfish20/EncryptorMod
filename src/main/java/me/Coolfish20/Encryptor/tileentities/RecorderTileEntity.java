package me.Coolfish20.Encryptor.tileentities;

import me.Coolfish20.Encryptor.init.Register;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class RecorderTileEntity extends TileEntity implements ITickableTileEntity {
    public RecorderTileEntity() {
        super(Register.RECORDER_TILE_ENTITY.get());
    }

    public static AxisAlignedBB recordertile;

    @Override
    public void tick() {
    double x = this.getBlockPos().getX();
    double y = this.getBlockPos().getY();
    double z = this.getBlockPos().getZ();
    recordertile = new AxisAlignedBB(x,y,z,x,y,z);
    }


    @Override
    public CompoundNBT save(CompoundNBT nbt){
        super.save(nbt);
        for(String s: this.getTileData().getAllKeys()){
            if(s.startsWith("message_")){
                nbt.putString(s, this.getTileData().getString(s));
            }
        }
        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt){
        super.load(state,nbt);
        for(String s : nbt.getAllKeys()){
            if(s.startsWith("message_")){
                this.getTileData().putString(s, nbt.getString(s));
            }
        }
    }
}
