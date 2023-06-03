package me.Coolfish20.Encryptor.tileentities;

import me.Coolfish20.Encryptor.init.Register;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class EncryptorTileEntity extends TileEntity{


    public EncryptorTileEntity() {
    //ALWAYS pass in the REGISTERED TileEntityType
        super(Register.ENCRYPTOR_TILE_ENTITY.get());
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt){
    super.save(nbt);
    for(String s: this.getTileData().getAllKeys()){
    if(s.equals("message") || s.equals("key")){
        nbt.putString(s, this.getTileData().getString(s));
    }
    }
        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt){
    super.load(state,nbt);
    for(String s : nbt.getAllKeys()){
    if(s.equals("message") || s.equals("key")){
    this.getTileData().putString(s, nbt.getString(s));
    }
    }
    }


}



