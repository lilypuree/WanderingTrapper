package lilypuree.wandering_trapper.entity.ai;

import lilypuree.wandering_trapper.compat.IWeaponSelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;

public class CustomRangedAttackGoal<T extends Mob & RangedAttackMob> extends Goal {
    private final T entity;
    private final double moveSpeedAmp;
    private int attackCooldown;
    private final float maxAttackDistance;
    private final float minAttackDistance = 4.0F;
    private int attackTime = -1;
    private int seeTime;
    private int closeTime = -1;
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
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    @Override
    public boolean canUse() {
        return this.entity.getTarget() == null ? false : this.isWeaponInMainhand();
    }

    @Override
    public boolean canContinueToUse() {
        if (closeTime > 40) return false;
        return (this.canUse() || !this.entity.getNavigation().isDone()) && this.isWeaponInMainhand();
    }

    @Override
    public void start() {
        super.start();
        this.entity.setAggressive(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.entity.setAggressive(false);
        this.seeTime = 0;
        this.attackTime = -1;
        this.closeTime = 0;
        this.entity.stopUsingItem();
    }

    protected boolean isWeaponInMainhand() {
        ItemStack main = entity.getMainHandItem();
        ItemStack off = entity.getOffhandItem();
        return main.getItem() == weaponSelector.getWeapon() || off.getItem() == weaponSelector.getWeapon();
    }

    public void tick() {
        LivingEntity target = this.entity.getTarget();
        if (target != null) {
            double d0 = this.entity.distanceToSqr(target.getX(), target.getBoundingBox().minY, target.getZ());
            boolean canSeeEntity = this.entity.getSensing().hasLineOfSight(target);
            boolean seeing = this.seeTime > 0;
            if (canSeeEntity != seeing) {
                this.seeTime = 0;
                this.closeTime = 0;
            }

            if (canSeeEntity) {
                ++this.seeTime;
            } else {
                --this.seeTime;
            }

            if (d0 < minAttackDistance) {
                closeTime++;
            } else {
                closeTime = 0;
            }

            if (!(d0 > (double) this.maxAttackDistance) && this.seeTime >= 20) {
                this.entity.getNavigation().stop();
                ++this.strafingTime;
            } else {
                this.entity.getNavigation().moveTo(target, this.moveSpeedAmp);
                this.strafingTime = -1;
            }

            if (this.strafingTime >= 20) {
                if ((double) this.entity.getRandom().nextFloat() < 0.3D) {
                    this.strafingClockwise = !this.strafingClockwise;
                }

                if ((double) this.entity.getRandom().nextFloat() < 0.3D) {
                    this.strafingBackwards = !this.strafingBackwards;
                }
                this.strafingTime = 0;
            }

            if (this.strafingTime > -1) {
                if (d0 > (double) (this.maxAttackDistance * 0.75F)) {
                    this.strafingBackwards = false;
                } else if (d0 < (double) (this.maxAttackDistance * 0.25F)) {
                    this.strafingBackwards = true;
                }

                this.entity.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                this.entity.lookAt(target, 30.0F, 30.0F);
            } else {
                this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            if (this.entity.isUsingItem()) {
                if (!canSeeEntity && this.seeTime < -60) {
                    this.entity.stopUsingItem();
                } else if (canSeeEntity) {
                    int i = this.entity.getTicksUsingItem();
                    if (i >= weaponSelector.getWeaponLoadTime()) {
                        this.entity.stopUsingItem();
                        ((RangedAttackMob) this.entity).performRangedAttack(target, weaponSelector.getProjectileSpeed(i));
                        this.attackTime = this.attackCooldown;
                        this.closeTime = 0;
                    }
                }
            } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
                this.entity.startUsingItem(ProjectileUtil.getWeaponHoldingHand(this.entity, weaponSelector.getWeapon()));
            }
        }
    }


}