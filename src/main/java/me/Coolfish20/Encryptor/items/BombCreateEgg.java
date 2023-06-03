package me.Coolfish20.Encryptor.items;

import me.Coolfish20.Encryptor.entity.SpyBombEntity;
import me.Coolfish20.Encryptor.init.Register;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BombCreateEgg extends Item {
    public BombCreateEgg(Properties properties) {
        super(properties);
    }
    
    @Override
    public ActionResultType useOn(ItemUseContext context){
    if(!context.getLevel().isClientSide && context.getHand() == Hand.MAIN_HAND){
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if(!state.isAir()){
            World w = context.getLevel();
            SpyBombEntity spyBomb = new SpyBombEntity(Register.BOMB.get(),context.getLevel());
            spyBomb.setPos(context.getClickedPos().getX()+0.5,context.getClickedPos().getY()+1,context.getClickedPos().getZ()-0.5);
            w.addFreshEntity(spyBomb);
        }
    }

        return ActionResultType.PASS;
    }
    
    
}
