package me.Coolfish20.Encryptor.entity;

import me.Coolfish20.Encryptor.entity.goals.ExplodeGoal;
import me.Coolfish20.Encryptor.entity.goals.RunAwayFromTntGoal;
import me.Coolfish20.Encryptor.init.Register;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.brain.task.WalkRandomlyTask;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpyBombEntity extends MobEntity {
    public SpyBombEntity(EntityType<? extends MobEntity> type, World w) {
        super(Register.BOMB.get(), w);
    }

    public static DataParameter<String> TARGET_NAME = EntityDataManager.defineId(SpyBombEntity.class, DataSerializers.STRING);

    public static AttributeModifierMap.MutableAttribute setAtributes(){
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0f)
                .add(Attributes.MOVEMENT_SPEED, 3.5f).add(Attributes.ARMOR, 3.5f);
    }


    @Override
    protected void registerGoals(){
        this.goalSelector.addGoal(1, new ExplodeGoal(this, this.level));
        this.goalSelector.addGoal(2, new RunAwayFromTntGoal(this));
        this.goalSelector.addGoal(3,new SwimGoal(this));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
    }

    @Override
    protected void defineSynchedData(){
    super.defineSynchedData();
    this.entityData.define(TARGET_NAME, "");
    }

    @Override
    public void tick(){
        BlockPos y1 = new BlockPos(this.getX(),this.getY()-1,this.getZ());
        if(!this.level.getBlockState(y1).isAir()) {
            if(this.getActiveEffects().contains(new EffectInstance(Effects.SLOW_FALLING))){
                this.removeEffect(Effects.SLOW_FALLING);
            }

        }else {
            EffectInstance instance = new EffectInstance(Effects.SLOW_FALLING);
            this.addEffect(instance);
        }
        super.tick();
    }




}
