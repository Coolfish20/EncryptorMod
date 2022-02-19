package me.Coolfish20.Encryptor.tileentities;

import me.Coolfish20.Encryptor.init.Register;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;


public class EncryptorTileEntity extends TileEntity{


    public EncryptorTileEntity() {

        super(TileEntityType.Builder.of(EncryptorTileEntity::new, Register.ENCRYPTOR.get()).build(null));
    }



    //WRITING THE DATA TO NBT
    @Override
    public CompoundNBT save(CompoundNBT nbt){
        return nbt;
    }
    //LOADING THE DATA FROM NBT
    @Override
    public void load(BlockState state, CompoundNBT nbt){
    super.load(state, nbt);
    }
}



