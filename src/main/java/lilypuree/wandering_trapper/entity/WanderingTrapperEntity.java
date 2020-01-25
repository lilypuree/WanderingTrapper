package lilypuree.wandering_trapper.entity;

import lilypuree.wandering_trapper.compat.IWeaponSelector;
import lilypuree.wandering_trapper.entity.ai.CustomMeleeAttackGoal;
import lilypuree.wandering_trapper.entity.ai.CustomRangedAttackGoal;
import lilypuree.wandering_trapper.entity.ai.NotInvisibleTargetGoal;
import lilypuree.wandering_trapper.entity.ai.UseOffhandItemGoal;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class WanderingTrapperEntity extends AbstractVillagerEntity implements IRangedAttackMob{

    @Nullable
    private BlockPos wanderTarget;
    private int despawnDelay;
    public static IWeaponSelector weaponSelector;
    public WanderingTrapperEntity(EntityType<? extends WanderingTrapperEntity> type, World worldIn){
        super(type, worldIn);
        this.forceSpawn=true;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(0, new UseOffhandItemGoal<>(this, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.STRONG_HEALING), SoundEvents.ENTITY_GENERIC_DRINK, (p_213733_1_) -> {
            return this.world.rand.nextFloat() < 0.0052F && this.getHealth() < this.getMaxHealth();
        }));
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(1, new LookAtCustomerGoal(this));
        this.goalSelector.addGoal(2, new CustomMeleeAttackGoal(this, 0.5D, true, weaponSelector));
        this.goalSelector.addGoal(2, new CustomRangedAttackGoal<>(this, weaponSelector));
        this.goalSelector.addGoal(2, new MoveToGoal(this, 2.5D, 0.4D));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.55D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 0.55D));
        this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this){

        });
        this.targetSelector.addGoal(2, new NotInvisibleTargetGoal<>(this, AbstractIllagerEntity.class, true));
        this.targetSelector.addGoal(3, new NotInvisibleTargetGoal<>(this, VexEntity.class, true));
        this.targetSelector.addGoal(3, new NotInvisibleTargetGoal<>(this, ZombieEntity.class, true));
        this.targetSelector.addGoal(3, new NotInvisibleTargetGoal<>(this, FoxEntity.class, true));
        this.targetSelector.addGoal(3, new NotInvisibleTargetGoal<>(this, PolarBearEntity.class, true));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(28.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.2D);
        if(weaponSelector.isGun()){
            this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.1D);
        }else {
            this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
        }
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    public boolean func_213705_dZ() {
        return false;
    }

    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = itemstack.getItem() == Items.NAME_TAG;
        if (flag) {
            itemstack.interactWithEntity(player, this, hand);
            return true;
        } else if (itemstack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.hasCustomer() && !this.isChild()) {
            if (hand == Hand.MAIN_HAND) {
                player.addStat(Stats.TALKED_TO_VILLAGER);
            }

            if (this.getOffers().isEmpty()) {
                return super.processInteract(player, hand);
            } else {
                if (!this.world.isRemote) {
                    this.setCustomer(player);
                    this.openMerchantContainer(player, this.getDisplayName(), 1);
                }

                return true;
            }
        } else {
            return super.processInteract(player, hand);
        }
    }

    @Override
    protected void populateTradeData() {
        VillagerTrades.ITrade[] trades = TrapperTrades.trades.get(1);
        VillagerTrades.ITrade[] trades1 = TrapperTrades.trades.get(2);
        if (trades != null && trades1 != null) {
            MerchantOffers merchantoffers = this.getOffers();
            this.addTrades(merchantoffers, trades, 3);
            this.addTrades(merchantoffers, trades1, 2);
        }
    }

    @Override
    protected float getDropChance(EquipmentSlotType slotIn) {
       return 0.3F;
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("DespawnDelay", this.despawnDelay);
        if (this.wanderTarget != null) {
            compound.put("WanderTarget", NBTUtil.writeBlockPos(this.wanderTarget));
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("DespawnDelay", 99)) {
            this.despawnDelay = compound.getInt("DespawnDelay");
        }

        if (compound.contains("WanderTarget")) {
            this.wanderTarget = NBTUtil.readBlockPos(compound.getCompound("WanderTarget"));
        }

        this.setGrowingAge(Math.max(0, this.getGrowingAge()));
    }

    public void setDespawnDelay(int delay) {
        this.despawnDelay = delay;
    }

    public int getDespawnDelay() {
        return this.despawnDelay;
    }

    public void setWanderTarget(@Nullable BlockPos wanderTarget){
        this.wanderTarget= wanderTarget;
    }
    @Nullable
    public BlockPos getWanderTarget(){return  this.wanderTarget;}

    protected SoundEvent getAmbientSound() {
        return this.hasCustomer() ? SoundEvents.ENTITY_WANDERING_TRADER_TRADE : SoundEvents.ENTITY_WANDERING_TRADER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_WANDERING_TRADER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WANDERING_TRADER_DEATH;
    }

    protected SoundEvent getDrinkSound(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.MILK_BUCKET ? SoundEvents.ENTITY_WANDERING_TRADER_DRINK_MILK : SoundEvents.ENTITY_WANDERING_TRADER_DRINK_POTION;
    }

    protected SoundEvent getVillagerYesNoSound(boolean getYesSound) {
        return getYesSound ? SoundEvents.ENTITY_WANDERING_TRADER_YES : SoundEvents.ENTITY_WANDERING_TRADER_NO;
    }

    public SoundEvent getYesSound() {
        return SoundEvents.ENTITY_WANDERING_TRADER_YES;
    }


    private void handleDespawn(){
        if(this.despawnDelay > 0 && !this.hasCustomer() && --this.despawnDelay == 0){
            this.remove();
        }
    }

    public void livingTick() {
        super.livingTick();
        if(this.getRevengeTarget() != null){
            this.setRevengeTarget(this.getRevengeTarget());
        }
        if (!this.world.isRemote) {
            this.handleDespawn();
        }
    }

    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        ItemStack ammo = this.findAmmo(this.getHeldItem(ProjectileHelper.getHandWith(this, weaponSelector.getWeapon())));
        Entity projectileEntity = weaponSelector.getProjectile(this, distanceFactor);
        double d0 = target.posX - this.posX;
        double d1 = target.getBoundingBox().minY + (double)(target.getHeight() / 3.0F) - projectileEntity.posY;
        double d2 = target.posZ - this.posZ;
        projectileEntity = weaponSelector.shoot(projectileEntity, d0, d1, d2, this.world.getDifficulty().getId());
        this.playSound(weaponSelector.getShootSound(), 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.addEntity(projectileEntity);
    }


    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setHeldItem(Hand.MAIN_HAND, new ItemStack(weaponSelector.getWeapon()));
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected void onVillagerTrade(MerchantOffer offer) {
        if (offer.getDoesRewardExp()) {
            int i = 3 + this.rand.nextInt(4);
            this.world.addEntity(new ExperienceOrbEntity(this.world, this.posX, this.posY + 0.5D, this.posZ, i));
        }
    }

    class MoveToGoal extends Goal {
        final WanderingTrapperEntity trapperEntity;
        final double maxDistance;
        final double speed;

        MoveToGoal(WanderingTrapperEntity traderEntityIn, double distanceIn, double speedIn) {
            this.trapperEntity = traderEntityIn;
            this.maxDistance = distanceIn;
            this.speed = speedIn;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            this.trapperEntity.setWanderTarget((BlockPos)null);
            WanderingTrapperEntity.this.navigator.clearPath();
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            BlockPos blockpos = this.trapperEntity.getWanderTarget();
            return blockpos != null && this.isWithinDistance(blockpos, this.maxDistance);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            BlockPos blockpos = this.trapperEntity.getWanderTarget();
            if (blockpos != null && WanderingTrapperEntity.this.navigator.noPath()) {
                if (this.isWithinDistance(blockpos, 10.0D)) {
                    Vec3d vec3d = (new Vec3d((double)blockpos.getX() - this.trapperEntity.posX, (double)blockpos.getY() - this.trapperEntity.posY, (double)blockpos.getZ() - this.trapperEntity.posZ)).normalize();
                    Vec3d vec3d1 = vec3d.scale(10.0D).add(this.trapperEntity.posX, this.trapperEntity.posY, this.trapperEntity.posZ);
                    WanderingTrapperEntity.this.navigator.tryMoveToXYZ(vec3d1.x, vec3d1.y, vec3d1.z, this.speed);
                } else {
                    WanderingTrapperEntity.this.navigator.tryMoveToXYZ((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), this.speed);
                }
            }

        }

        private boolean isWithinDistance(BlockPos pos, double distance) {
            return !pos.withinDistance(this.trapperEntity.getPositionVec(), distance);
        }
    }
}
