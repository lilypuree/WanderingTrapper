package lilypuree.wandering_trapper.entity;

import lilypuree.wandering_trapper.compat.IWeaponSelector;
import lilypuree.wandering_trapper.core.RegistryObjects;
import lilypuree.wandering_trapper.entity.ai.CustomMeleeAttackGoal;
import lilypuree.wandering_trapper.entity.ai.CustomRangedAttackGoal;
import lilypuree.wandering_trapper.entity.ai.NotInvisibleTargetGoal;
import lilypuree.wandering_trapper.entity.ai.UseOffhandItemGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class WanderingTrapperEntity extends AbstractVillager implements RangedAttackMob {

    private BlockPos wanderTarget;
    private int despawnDelay;

    public static IWeaponSelector weaponSelector;

    public WanderingTrapperEntity(Level worldIn) {
        super(RegistryObjects.WANDERING_TRAPPER, worldIn);
    }

    public WanderingTrapperEntity(EntityType<? extends AbstractVillager> type, Level world) {
        super(type, world);
//        this.forcedLoading = true;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new UseOffhandItemGoal<>(this, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_HEALING), SoundEvents.GENERIC_DRINK, (p_213733_1_) -> {
            return this.level.random.nextFloat() < 0.0052F && this.getHealth() < this.getMaxHealth();
        }));
        this.goalSelector.addGoal(2, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(2, new LookAtTradingPlayerGoal(this));
        this.goalSelector.addGoal(3, new CustomMeleeAttackGoal(this, 0.5D, true, weaponSelector));
        this.goalSelector.addGoal(3, new CustomRangedAttackGoal<>(this, weaponSelector));
        this.goalSelector.addGoal(3, new MoveToGoal(this, 2.5D, 0.4D));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.55D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.55D));
        this.goalSelector.addGoal(9, new InteractGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this) {

        });
        this.targetSelector.addGoal(2, new NotInvisibleTargetGoal<>(this, AbstractIllager.class, true));
        this.targetSelector.addGoal(3, new NotInvisibleTargetGoal<>(this, Vex.class, true));
        this.targetSelector.addGoal(3, new NotInvisibleTargetGoal<>(this, Zombie.class, true));
        this.targetSelector.addGoal(3, new NotInvisibleTargetGoal<>(this, Fox.class, true));
        this.targetSelector.addGoal(3, new NotInvisibleTargetGoal<>(this, PolarBear.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder attribute = LivingEntity.createLivingAttributes().add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 28.0D)
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.ARMOR, 1.2D);
        attribute.add(Attributes.ATTACK_DAMAGE, 2.0D);
        attribute.add(Attributes.ATTACK_KNOCKBACK);

        return attribute;
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
        return null;
    }

    public boolean showProgressBar() {
        return false;
    }

    public void setDespawnDelay(int delay) {
        this.despawnDelay = delay;
    }

    public int getDespawnDelay() {
        return this.despawnDelay;
    }

    public void setWanderTarget(BlockPos wanderTarget) {
        this.wanderTarget = wanderTarget;
    }

    public BlockPos getWanderTarget() {
        return this.wanderTarget;
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.isTrading() && !this.isBaby()) {
            if (hand == InteractionHand.MAIN_HAND) {
                player.awardStat(Stats.TALKED_TO_VILLAGER);
            }

            if (this.getOffers().isEmpty()) {
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            } else {
                if (!this.level.isClientSide) {
                    this.setTradingPlayer(player);
                    this.openTradingScreen(player, this.getDisplayName(), 1);
                }

                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
        } else {
            return super.mobInteract(player, hand);
        }
    }


    @Override
    protected void updateTrades() {
        VillagerTrades.ItemListing[] trades = TrapperTrades.trades.get(1);
        VillagerTrades.ItemListing[] trades1 = TrapperTrades.trades.get(2);
        if (trades != null && trades1 != null) {
            MerchantOffers merchantoffers = this.getOffers();
            this.addOffersFromItemListings(merchantoffers, trades, 3);
            this.addOffersFromItemListings(merchantoffers, trades1, 2);
        }
    }

    @Override
    protected float getEquipmentDropChance(EquipmentSlot slotIn) {
        return 0.3F;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("DespawnDelay", this.despawnDelay);
        if (this.wanderTarget != null) {
            compound.put("WanderTarget", NbtUtils.writeBlockPos(this.wanderTarget));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("DespawnDelay", 99)) {
            this.despawnDelay = compound.getInt("DespawnDelay");
        }

        if (compound.contains("WanderTarget")) {
            this.wanderTarget = NbtUtils.readBlockPos(compound.getCompound("WanderTarget"));
        }

        this.setAge(Math.max(0, this.getAge()));
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected void rewardTradeXp(MerchantOffer offer) {
        if (offer.shouldRewardExp()) {
            int i = 3 + this.random.nextInt(4);
            this.level.addFreshEntity(new ExperienceOrb(this.level, this.getX(), this.getY() + 0.5D, this.getZ(), i));
        }
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return this.isTrading() ? SoundEvents.WANDERING_TRADER_TRADE : SoundEvents.WANDERING_TRADER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.WANDERING_TRADER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WANDERING_TRADER_DEATH;
    }

    @Override
    protected SoundEvent getDrinkingSound(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.MILK_BUCKET ? SoundEvents.WANDERING_TRADER_DRINK_MILK : SoundEvents.WANDERING_TRADER_DRINK_POTION;
    }

    @Override
    protected SoundEvent getTradeUpdatedSound(boolean getYesSound) {
        return getYesSound ? SoundEvents.WANDERING_TRADER_YES : SoundEvents.WANDERING_TRADER_NO;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return SoundEvents.WANDERING_TRADER_YES;
    }


    private void handleDespawn() {
        if (this.despawnDelay > 0 && !this.isTrading() && --this.despawnDelay == 0) {
            this.discard();
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.getLastHurtByMob() != null) {
            this.setLastHurtByMob(this.getLastHurtByMob());
        }
        if (!this.level.isClientSide) {
            this.handleDespawn();
        }
    }


    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
//        ItemStack ammo = this.findAmmo(this.getHeldItem(ProjectileHelper.getHandWith(this, weaponSelector.getWeapon())));
        Entity projectileEntity = weaponSelector.getProjectile(this, distanceFactor);
        double d0 = target.getX() - this.getX();
        double d1 = target.getBoundingBox().minY + (double) (target.getBbHeight() * 0.7f) - projectileEntity.getY();
        double d2 = target.getZ() - this.getZ();
        weaponSelector.shoot(this, projectileEntity, d0, d1, d2, this.level.getDifficulty().getId());
        this.playSound(weaponSelector.getShootSound(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
    }


    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(weaponSelector.getWeapon()));
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }


    class MoveToGoal extends Goal {
        final WanderingTrapperEntity trapperEntity;
        final double maxDistance;
        final double speed;

        MoveToGoal(WanderingTrapperEntity traderEntityIn, double distanceIn, double speedIn) {
            this.trapperEntity = traderEntityIn;
            this.maxDistance = distanceIn;
            this.speed = speedIn;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            this.trapperEntity.setWanderTarget((BlockPos) null);
            WanderingTrapperEntity.this.navigation.stop();
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            BlockPos blockpos = this.trapperEntity.getWanderTarget();
            return blockpos != null && this.isWithinDistance(blockpos, this.maxDistance);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            BlockPos blockpos = this.trapperEntity.getWanderTarget();
            if (blockpos != null && WanderingTrapperEntity.this.navigation.isDone()) {
                if (this.isWithinDistance(blockpos, 10.0D)) {
                    Vec3 vec3d = (new Vec3((double) blockpos.getX() - this.trapperEntity.getX(), (double) blockpos.getY() - this.trapperEntity.getY(), (double) blockpos.getZ() - this.trapperEntity.getZ())).normalize();
                    Vec3 vec3d1 = vec3d.scale(10.0D).add(this.trapperEntity.getX(), this.trapperEntity.getY(), this.trapperEntity.getZ());
                    WanderingTrapperEntity.this.navigation.moveTo(vec3d1.x, vec3d1.y, vec3d1.z, this.speed);
                } else {
                    WanderingTrapperEntity.this.navigation.moveTo((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), this.speed);
                }
            }
        }

        private boolean isWithinDistance(BlockPos pos, double distance) {
            return !pos.closerThan(this.trapperEntity.position(), distance);
        }
    }
}
