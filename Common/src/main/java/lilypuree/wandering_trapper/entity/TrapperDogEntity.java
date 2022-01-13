package lilypuree.wandering_trapper.entity;

import lilypuree.wandering_trapper.core.RegistryObjects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.EnumSet;

public class TrapperDogEntity extends Wolf {

    public TrapperDogEntity(EntityType<? extends TrapperDogEntity> type, Level worldIn) {
        super(type, worldIn);
//        this.forcedLoading = true;
    }

    private int despawnDelay = 47999; //47999

    public boolean isTrapperDog() {
        return true;
    }

    protected Wolf createChild() {
        return RegistryObjects.TRAPPER_DOG.create(this.level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new FollowTrapperGoal(this));
    }

    private void handleDespawn() {
        if (this.canDespawn()) {
            this.despawnDelay = this.isLeashedToTrapper() ? ((WanderingTrapperEntity) this.getLeashHolder()).getDespawnDelay() - 1 : this.despawnDelay - 1;
            if (this.despawnDelay <= 0) {
                this.dropLeash(true, false);
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide) {
            this.handleDespawn();
        }
    }

    private boolean canDespawn() {
        return !this.isLeashedToStranger();
    }


    private boolean isLeashedToTrapper() {
        return this.getLeashHolder() instanceof WanderingTrapperEntity;
    }

    private boolean isLeashedToStranger() {
        return this.isLeashed() && !this.isLeashedToTrapper();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason,  SpawnGroupData spawnDataIn, CompoundTag dataTag) {
        SpawnGroupData ilivingentitydata = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        if (reason == MobSpawnType.EVENT) {
            this.setAge(0);
        }
        return ilivingentitydata;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("DespawnDelay", this.despawnDelay);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("DespawnDelay", 99)) {
            this.despawnDelay = compound.getInt("DespawnDelay");
        }
    }

    public class FollowTrapperGoal extends TargetGoal {
        private final Wolf wolfEntity;
        private LivingEntity targetEntity;
        private int revengeTime;

        public FollowTrapperGoal(Wolf wolfEntityIn) {
            super(wolfEntityIn, false);
            this.wolfEntity = wolfEntityIn;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        public boolean canUse() {
            if (!this.wolfEntity.isLeashed()) {
                return false;
            } else {
                Entity entity = this.wolfEntity.getLeashHolder();
                if (!(entity instanceof WanderingTrapperEntity)) {
                    return false;
                } else {
                    WanderingTrapperEntity wanderingTrapperEntity = (WanderingTrapperEntity) entity;
                    this.targetEntity = wanderingTrapperEntity.getLastHurtByMob();

                    int i = wanderingTrapperEntity.getLastHurtByMobTimestamp();
                    return i != this.revengeTime && this.canAttack(this.targetEntity, TargetingConditions.DEFAULT);
                }
            }
        }

        public void start() {
            this.mob.setTarget(this.targetEntity);
            Entity entity = this.wolfEntity.getLeashHolder();
            if (entity instanceof WanderingTrapperEntity) {
                this.wolfEntity.dropLeash(true, false);
                this.revengeTime = ((WanderingTrapperEntity) entity).getLastHurtByMobTimestamp();
            }
            super.start();
        }
    }
}