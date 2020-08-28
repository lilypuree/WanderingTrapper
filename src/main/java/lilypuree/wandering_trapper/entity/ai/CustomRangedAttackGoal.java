package lilypuree.wandering_trapper.entity.ai;

import lilypuree.wandering_trapper.compat.IWeaponSelector;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;

public class CustomRangedAttackGoal<T extends MobEntity & IRangedAttackMob> extends Goal {
    private final T entity;
    private final double moveSpeedAmp;
    private int attackCooldown;
    private final float maxAttackDistance;
    private int attackTime = -1;
    private int seeTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;
    private IWeaponSelector weaponSelector;

    public CustomRangedAttackGoal(T mob, IWeaponSelector selector) {
        this.weaponSelector = selector;
        this.entity = mob;
        this.moveSpeedAmp = selector.getMoveSpeedAmp();
        this.attackCooldown = selector.getAttackCooldown();
        this.maxAttackDistance = selector.getMaxAttackDistance() * selector.getMaxAttackDistance();
        this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    @Override
    public boolean shouldExecute() {
        return this.entity.getAttackTarget() == null ? false : this.isWeaponInMainhand();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return (this.shouldExecute() || !this.entity.getNavigator().noPath()) && this.isWeaponInMainhand();
    }

    @Override
    public void startExecuting() {
        super.startExecuting();

        this.entity.setAggroed(true);
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.entity.setAggroed(false);
        this.seeTime = 0;
        this.attackTime = -1;
        this.entity.resetActiveHand();
    }

    protected boolean isWeaponInMainhand() {
        ItemStack main = entity.getHeldItemMainhand();
        ItemStack off = entity.getHeldItemOffhand();
        return main.getItem() == weaponSelector.getWeapon() || off.getItem() == weaponSelector.getWeapon();
    }

    public void tick() {
        LivingEntity livingentity = this.entity.getAttackTarget();
        if (livingentity != null) {
            double d0 = this.entity.getDistanceSq(livingentity.getPosX(), livingentity.getBoundingBox().minY, livingentity.getPosZ());
            boolean canSeeEntity = this.entity.getEntitySenses().canSee(livingentity);
            boolean seeing = this.seeTime > 0;
            boolean isGun = this.weaponSelector.isGun();
            if (canSeeEntity != seeing) {
                this.seeTime = 0;
            }

            if (canSeeEntity) {
                ++this.seeTime;
            } else {
                --this.seeTime;
            }

            if (!(d0 > (double)this.maxAttackDistance) && this.seeTime >= 20) {
                this.entity.getNavigator().clearPath();
                ++this.strafingTime;
            } else {
                this.entity.getNavigator().tryMoveToEntityLiving(livingentity, this.moveSpeedAmp);
                this.strafingTime = -1;
            }

            if (this.strafingTime >= 20) {
                if ((double)this.entity.getRNG().nextFloat() < 0.3D) {
                    this.strafingClockwise = !this.strafingClockwise;
                }

                if ((double)this.entity.getRNG().nextFloat() < 0.3D) {
                    this.strafingBackwards = !this.strafingBackwards;
                }

                this.strafingTime = 0;
            }

            if (this.strafingTime > -1) {
                if (d0 > (double)(this.maxAttackDistance * 0.75F)) {
                    this.strafingBackwards = false;
                } else if (d0 < (double)(this.maxAttackDistance * 0.25F)) {
                    this.strafingBackwards = true;
                }

                this.entity.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                this.entity.faceEntity(livingentity, 30.0F, 30.0F);
            } else {
                this.entity.getLookController().setLookPositionWithEntity(livingentity, 30.0F, 30.0F);
            }

            if (this.entity.isHandActive()) {
                if (!canSeeEntity && this.seeTime < -60) {
                    this.entity.resetActiveHand();
                } else if (canSeeEntity) {
                    int i = this.entity.getItemInUseMaxCount();
                    if (i >= weaponSelector.getWeaponLoadTime()) {
                        this.entity.resetActiveHand();
                        ((IRangedAttackMob)this.entity).attackEntityWithRangedAttack(livingentity, weaponSelector.getProjectileSpeed(i));
                        this.attackTime = this.attackCooldown;
                    }
                }
            } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
                this.entity.setActiveHand(ProjectileHelper.getHandWith(this.entity, weaponSelector.getWeapon()));
                this.entity.setActiveHand(ProjectileHelper.getHandWith(this.entity, weaponSelector.getWeapon()));
            }
        }
    }

}