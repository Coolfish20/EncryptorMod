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

    private String key;
    private String message;

    //WRITING THE DATA TO NBT
    @Override
    public CompoundNBT save(CompoundNBT nbt){
        super.save(nbt);//Super call is required to save tiles location
        nbt.putString("key", this.getTileData().getString("key"));
        nbt.putString("message", this.getTileData().getString("message"));
        return nbt;//Returning the gathered nbt
    }
    //LOADING THE DATA FROM NBT
    @Override
    public void load(BlockState state, CompoundNBT nbt){
    String key = nbt.getString("key"); //Getting the actual string from the saved nbt
    String message =nbt.getString("message");
    nbt.putString("key", key); //Putting the strings value to nbt
    nbt.putString("message", message);
    super.load(state, nbt);//Calling super to load the data back in
    }

}



