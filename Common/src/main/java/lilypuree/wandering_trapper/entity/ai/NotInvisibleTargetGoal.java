package lilypuree.wandering_trapper.entity.ai;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

import java.util.function.Predicate;

public class NotInvisibleTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal {
    public NotInvisibleTargetGoal(Mob mob, Class<T> mobClass, boolean p_i50313_3_) {
        super(mob, mobClass, p_i50313_3_, false);
    }

    public NotInvisibleTargetGoal(Mob p_i50314_1_, Class<T> p_i50314_2_, boolean p_i50314_3_, boolean p_i50314_4_) {
        super(p_i50314_1_, p_i50314_2_, 10, p_i50314_3_, p_i50314_4_, (Predicate<LivingEntity>)null);
    }

    @Override
    public boolean canUse() {
        return this.mob.getEffect(MobEffects.INVISIBILITY) == null && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.getEffect(MobEffects.INVISIBILITY) == null && super.canContinueToUse();
    }
}
