package me.Coolfish20.Encryptor.tileentities;

import me.Coolfish20.Encryptor.init.Register;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.nbt.INBT;

public class EncryptorTileEntity extends TileEntity{


    public EncryptorTileEntity() {

        super(TileEntityType.Builder.of(EncryptorTileEntity::new, Register.ENCRYPTOR.get()).build(null));
    }

    private String key;
    private String message;

    //WRITING THE DATA TO NBT
    @Override
    public CompoundNBT save(CompoundNBT nbt){
    //Always check, if the data is really there!!
        //For some reason the tile entity doesnt have a key...
        //getKey()== item registry name
        System.out.println("ENCRYPTOR KEY: "+TileEntityType.getKey(this.getType()));
        //Encryptors key is null
        super.save(nbt);
        nbt.putString("key", this.getTileData().getString("key"));
        nbt.putString("message", this.getTileData().getString("message"));
        return nbt;
    }
    //LOADING THE DATA FROM NBT
    @Override
    public void load(BlockState state, CompoundNBT nbt){
    String key = nbt.getString("key");
    String message =nbt.getString("message");
    nbt.putString("key", key);
    nbt.putString("message", message);
    System.out.println("LOADING DATA!");
    super.load(state, nbt);
    }

}



