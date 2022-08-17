package lilypuree.wandering_trapper.compat;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class BowWeapon implements IWeaponSelector {
    @Override
    public Item getWeapon() {
        return Items.BOW;
    }

    @Override
    public double getMoveSpeedAmp() {
        return 0.45D;
    }

    @Override
    public int getAttackCooldown() {
        return 20;
    }

    @Override
    public int getWeaponLoadTime() {
        return 20;
    }

    @Override
    public float getProjectileSpeed(int loadTime) {
        return BowItem.getPowerForTime(loadTime);
    }

    @Override
    public float getMaxAttackDistance() {
        return 48.0F;
    }

    @Override
    public Entity getProjectile(LivingEntity shooter, float distanceFactor) {
        return ProjectileUtil.getMobArrow(shooter, shooter.getProjectile(shooter.getItemInHand(ProjectileUtil.getWeaponHoldingHand(shooter, Items.BOW))), distanceFactor);
    }

    @Override
    public Entity shoot(LivingEntity shooter, Entity projectile, LivingEntity target, int difficulty) {
        if (projectile instanceof AbstractArrow arrow) {
            double dirX = target.getX() - shooter.getX();
            double dirY = target.getY(0.3333333333333333) - projectile.getY();
            double dirZ = target.getZ() - shooter.getZ();
            double distance = (double) Mth.sqrt((float) (dirX * dirX + dirZ * dirZ));
            arrow.shoot(dirX, dirY + distance * 0.20000000298023224, dirZ, 1.6F, (float) (14 - difficulty * 4));
        }
        shooter.level.addFreshEntity(projectile);
        return projectile;
    }

    @Override
    public SoundEvent getShootSound() {
        return SoundEvents.SKELETON_SHOOT;
    }

    @Override
    public boolean isGun() {
        return false;
    }
}