package me.Coolfish20.Encryptor.items;

import me.Coolfish20.Encryptor.entity.SpyBombEntity;
import me.Coolfish20.Encryptor.init.Register;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;


public class BombCreateEgg extends Item {
    public BombCreateEgg(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if(!context.getLevel().isClientSide){
            SpyBombEntity s = new SpyBombEntity(Register.BOMB.get(),context.getLevel());
            s.setPos(context.getPlayer().getX(),context.getPlayer().getY(),context.getPlayer().getZ());
            context.getLevel().addFreshEntity(s);
            if(!context.getPlayer().isCreative()){
                context.getItemInHand().setCount(context.getItemInHand().getCount()-1);
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity p, Hand p_77659_3_) {
        if(!world.isClientSide){
            SpyBombEntity s = new SpyBombEntity(Register.BOMB.get(),world);
            s.setPos(p.getX(),p.getY(),p.getZ());
            world.addFreshEntity(s);
            if(!p.isCreative()){
            if(p.getMainHandItem().equals(this.getDefaultInstance())) {
                p.getItemInHand(Hand.MAIN_HAND).setCount(p.getMainHandItem().getCount()-1);
                return ActionResult.success(p.getMainHandItem());
            }else {
                p.getOffhandItem().setCount(p.getOffhandItem().getCount()-1);
                return ActionResult.success(p.getOffhandItem());
            }
            }
        }

        return ActionResult.pass(p.getUseItem());
    }
}
