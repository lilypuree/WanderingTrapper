package lilypuree.wandering_trapper.compat;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public interface IWeaponSelector {

    public Item getWeapon();

    public double getMoveSpeedAmp();

    public int getAttackCooldown();

    public int getWeaponLoadTime();

    public float getProjectileSpeed(int loadTime);

    public float getMaxAttackDistance();

    public Entity getProjectile(LivingEntity shooter, float distanceFactor);

    public Entity shoot(LivingEntity shooter, Entity projectile, double dirX, double dirY, double dirZ, int difficulty);

    public SoundEvent getShootSound();

    public boolean isGun();
}