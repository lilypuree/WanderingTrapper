package lilypuree.wandering_trapper.entity;

import lilypuree.wandering_trapper.setup.Registration;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class TrapperDogEntity extends WolfEntity {

    public TrapperDogEntity(EntityType<? extends TrapperDogEntity> type, World worldIn){
        super(type, worldIn);
        this.forceSpawn=true;
    }

    private int despawnDelay = 47999; //47999

    @OnlyIn(Dist.CLIENT)
    public boolean isTrapperDog() {
        return true;
    }

    protected WolfEntity createChild() {return Registration.TRAPPER_DOG.get().create(this.world);}

    protected void registerGoals(){
        super.registerGoals();
        this.targetSelector.addGoal(1, new TrapperDogEntity.FollowTrapperGoal(this));
    }

    private void handleDespawn(){
        if(this.canDespawn()){
            this.despawnDelay = this.isLeashedToTrapper() ? ((WanderingTrapperEntity)this.getLeashHolder()).getDespawnDelay() -1 : this.despawnDelay -1;
            if(this.despawnDelay <= 0){
                this.clearLeashed(true, false);
                this.remove();
            }
        }
    }

    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {
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
        return this.getLeashed() && !this.isLeashedToTrapper();
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        ILivingEntityData ilivingentitydata = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        if (reason == SpawnReason.EVENT) {
            this.setGrowingAge(0);
        }
        return ilivingentitydata;
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("DespawnDelay", this.despawnDelay);
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("DespawnDelay", 99)) {
            this.despawnDelay = compound.getInt("DespawnDelay");
        }
    }

    public class FollowTrapperGoal extends TargetGoal {
        private final WolfEntity wolfEntity;
        private LivingEntity targetEntity;
        private int revengeTime;

        public FollowTrapperGoal(WolfEntity wolfEntityIn) {
            super(wolfEntityIn, false);
            this.wolfEntity = wolfEntityIn;
            this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
        }

        public boolean shouldExecute() {
            if (!this.wolfEntity.getLeashed()) {
                return false;
            } else {
                Entity entity = this.wolfEntity.getLeashHolder();
                if (!(entity instanceof WanderingTrapperEntity)) {
                    return false;
                } else {
                    WanderingTrapperEntity wanderingTrapperEntity = (WanderingTrapperEntity) entity;
                    this.targetEntity = wanderingTrapperEntity.getRevengeTarget();

                    int i = wanderingTrapperEntity.getRevengeTimer();
                    return i != this.revengeTime && this.isSuitableTarget(this.targetEntity, EntityPredicate.DEFAULT);
                }
            }
        }

        public void startExecuting() {
            this.goalOwner.setAttackTarget(this.targetEntity);
            Entity entity = this.wolfEntity.getLeashHolder();
            if (entity instanceof WanderingTrapperEntity) {
                this.wolfEntity.clearLeashed(true, false);
                this.revengeTime = ((WanderingTrapperEntity)entity).getRevengeTimer();
            }
            super.startExecuting();
        }
    }
}
