package me.Coolfish20.Encryptor.entity.goals;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

import static me.Coolfish20.Encryptor.entity.SpyBombEntity.*;

public class ExplodeGoal extends Goal {

    MobEntity mob;
    World w;



    public ExplodeGoal(MobEntity mob, World level){
        this.mob = mob;
        this.w = level;
    }



    @Override
    public boolean canUse() {
        return true;
    }
    @Override
    public void tick(){
        for(Entity e : w.getEntities((Entity) null, mob.getBoundingBox().inflate(10))){
            if (e instanceof PlayerEntity) {
                if (e.getName().getString(9).toString().equals(mob.getEntityData().get(TARGET_NAME))) {
                    if (mob.distanceTo(e) < 4.0f) {
                        TNTEntity tnt = new TNTEntity(w, e.getX(),e.getY(),e.getZ(),mob);
                        tnt.setFuse(8);
                        tnt.setInvisible(true);
                        w.addFreshEntity(tnt);
                        mob.getEntityData().set(TARGET_NAME, "");
                        mob.getNavigation().stop();
                        break;
                    } else {
                            mob.getNavigation().moveTo(e.getX(), e.getY(),e.getZ(), 0.2f);

                    }
                }
            }
        }
        super.tick();
    }

    }


