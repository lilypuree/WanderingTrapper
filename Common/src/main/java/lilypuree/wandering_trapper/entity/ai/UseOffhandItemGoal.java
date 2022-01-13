package lilypuree.wandering_trapper.entity.ai;


import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class UseOffhandItemGoal<T extends Mob> extends Goal {
    private final T mob;
    private final ItemStack item;
    private final Predicate<? super T> canUseSelector;
    private final SoundEvent finishUsingSound;

    public UseOffhandItemGoal(T p_i50319_1_, ItemStack p_i50319_2_, SoundEvent p_i50319_3_, Predicate<? super T> p_i50319_4_) {
        this.mob = p_i50319_1_;
        this.item = p_i50319_2_;
        this.finishUsingSound = p_i50319_3_;
        this.canUseSelector = p_i50319_4_;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse() {
        return this.canUseSelector.test(this.mob);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        return this.mob.isUsingItem();
    }


    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.mob.setItemSlot(EquipmentSlot.OFFHAND, this.item.copy());
        this.mob.startUsingItem(InteractionHand.OFF_HAND);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.mob.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        if (this.finishUsingSound != null) {
            this.mob.playSound(this.finishUsingSound, 1.0F, this.mob.getRandom().nextFloat() * 0.2F + 0.9F);
        }

    }
}
