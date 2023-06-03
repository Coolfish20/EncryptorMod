package me.Coolfish20.Encryptor.entity.goals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;



public class RunAwayFromTntGoal extends Goal {


    MobEntity mob;
    public RunAwayFromTntGoal(MobEntity mob){
        this.mob = mob;
    }


    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void tick() {
        for (Entity e : mob.level.getEntities((Entity) null, mob.getBoundingBox().inflate(5))){
        if(e instanceof TNTEntity){
        if(mob.distanceTo(e) < 4){
        Vector3d v = mob.getDeltaMovement().multiply(-3, -3, -3);

        mob.getNavigation().moveTo(v.x, v.y, v.z, 0.2f);
            BlockPos y1 = new BlockPos(mob.getX(),mob.getY()-1,mob.getZ());
        }
        }
        }
        super.tick();
    }
}
