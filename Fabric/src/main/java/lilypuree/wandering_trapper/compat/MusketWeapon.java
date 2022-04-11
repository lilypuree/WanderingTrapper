//package lilypuree.wandering_trapper.compat;
//
//import ewewukek.musketmod.*;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.phys.Vec3;
//
//
//public class MusketWeapon implements IWeaponSelector {
//    @Override
//    public Item getWeapon() {
//        return Items.MUSKET;
//    }
//
//    @Override
//    public double getMoveSpeedAmp() {
//        return 0.4D;
//    }
//
//    @Override
//    public int getAttackCooldown() {
//        return MusketItem.RELOAD_DURATION;
//    }
//
//    @Override
//    public int getWeaponLoadTime() {
//        return MusketItem.LOADING_STAGE_1 + MusketItem.LOADING_STAGE_2 + MusketItem.LOADING_STAGE_3;
//    }
//
//    @Override
//    public float getProjectileSpeed(int loadTime) {
//        return 1.0F;
//    }
//
//    @Override
//    public float getMaxAttackDistance() {
//        return 120.0F;
//    }
//
//    @Override
//    public Entity getProjectile(LivingEntity shooter, float distanceFactor) {
//        BulletEntity bullet = new BulletEntity(shooter.level);
//        bullet.setOwner(shooter);
//        bullet.setPos(shooter.getX(), shooter.getY() + shooter.getEyeHeight(), shooter.getZ());
//        return bullet;
//    }
//
//    @Override
//    public Entity shoot(LivingEntity shooter, Entity projectile, double dirX, double dirY, double dirZ, int difficulty) {
//        if (projectile instanceof BulletEntity) {
//            Vec3 dir = new Vec3(dirX, dirY, dirZ);
//            dir = dir.normalize().scale(1.1D);
//            ((GunItem) Items.MUSKET).fire(shooter, dir);
//        }
//        return projectile;
//    }
//
//    @Override
//    public SoundEvent getShootSound() {
//        return Sounds.MUSKET_FIRE;
//    }
//
//    @Override
//    public boolean isGun() {
//        return true;
//    }
//}