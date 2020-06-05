package lilypuree.wandering_trapper.compat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;

public interface IWeaponSelector {

    public Item getWeapon();

    public double getMoveSpeedAmp();

    public int getAttackCooldown();

    public int getWeaponLoadTime();

    public float getProjectileSpeed(int loadTime);

    public float getMaxAttackDistance();

    public Entity getProjectile(LivingEntity shooter,float distanceFactor);

    public Entity shoot(Entity projectile, double dirX, double dirY, double dirZ, int difficulty);

    public SoundEvent getShootSound();

    public boolean isGun();
}