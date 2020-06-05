package lilypuree.wandering_trapper.entity.ai;

import lilypuree.wandering_trapper.compat.IWeaponSelector;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.item.ItemStack;

public class CustomMeleeAttackGoal extends MeleeAttackGoal {
    private IWeaponSelector weaponSelector;

    public CustomMeleeAttackGoal(CreatureEntity creature, double speedIn, boolean useLongMemory, IWeaponSelector weaponSelectorIn){
        super(creature, speedIn, useLongMemory);
        this.weaponSelector = weaponSelectorIn;
    }

    @Override
    public boolean shouldExecute() {
        return isWeaponInMainhand() && super.shouldExecute();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !isWeaponInMainhand() && super.shouldContinueExecuting();
    }

    protected boolean isWeaponInMainhand(){
        ItemStack main = attacker.getHeldItemMainhand();
        ItemStack off= attacker.getHeldItemOffhand();
        return main.getItem() == weaponSelector.getWeapon() || off.getItem() == weaponSelector.getWeapon() ;
    }
}