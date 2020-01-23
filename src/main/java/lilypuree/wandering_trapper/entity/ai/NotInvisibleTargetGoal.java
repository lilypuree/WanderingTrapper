package lilypuree.wandering_trapper.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.potion.Effects;

import java.util.function.Predicate;

public class NotInvisibleTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal {
    public NotInvisibleTargetGoal(MobEntity p_i50313_1_, Class<T> p_i50313_2_, boolean p_i50313_3_) {
        super(p_i50313_1_, p_i50313_2_, p_i50313_3_, false);
    }

    public NotInvisibleTargetGoal(MobEntity p_i50314_1_, Class<T> p_i50314_2_, boolean p_i50314_3_, boolean p_i50314_4_) {
        super(p_i50314_1_, p_i50314_2_, 10, p_i50314_3_, p_i50314_4_, (Predicate<LivingEntity>)null);
    }

    @Override
    public boolean shouldExecute() {
        return this.goalOwner.getActivePotionEffect(Effects.INVISIBILITY) == null && super.shouldExecute();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.goalOwner.getActivePotionEffect(Effects.INVISIBILITY) == null && super.shouldContinueExecuting();
    }
}
