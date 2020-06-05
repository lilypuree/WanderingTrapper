package lilypuree.wandering_trapper.compat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;

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
        return BowItem.getArrowVelocity(loadTime);
    }

    @Override
    public float getMaxAttackDistance() {
        return 48.0F;
    }

    @Override
    public Entity getProjectile(LivingEntity shooter, float distanceFactor) {
        return ProjectileHelper.fireArrow(shooter, shooter.findAmmo(shooter.getHeldItem(ProjectileHelper.getHandWith(shooter, Items.BOW))), distanceFactor);
    }

    @Override
    public Entity shoot(Entity projectile, double dirX, double dirY, double dirZ, int difficulty) {
        if(projectile instanceof AbstractArrowEntity){
            double distance = (double) MathHelper.sqrt(dirX*dirX + dirZ*dirZ);
            ((AbstractArrowEntity) projectile).shoot(dirX,dirY+distance*0.2, dirZ, 1.6F,(float)(14-difficulty*4));
        }
        return projectile;
    }

    @Override
    public SoundEvent getShootSound() {
        return SoundEvents.ENTITY_SKELETON_SHOOT;
    }

    @Override
    public boolean isGun() {
        return false;
    }
}