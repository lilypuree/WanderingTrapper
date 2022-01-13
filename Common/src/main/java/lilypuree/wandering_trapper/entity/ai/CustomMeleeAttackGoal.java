package lilypuree.wandering_trapper.entity.ai;

import lilypuree.wandering_trapper.compat.IWeaponSelector;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.item.ItemStack;

public class CustomMeleeAttackGoal extends MeleeAttackGoal {
    private IWeaponSelector weaponSelector;

    public CustomMeleeAttackGoal(PathfinderMob creature, double speedIn, boolean useLongMemory, IWeaponSelector weaponSelectorIn){
        super(creature, speedIn, useLongMemory);
        this.weaponSelector = weaponSelectorIn;
    }

    @Override
    public boolean canUse() {
        return isWeaponInMainhand() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !isWeaponInMainhand() && super.canContinueToUse();
    }

    protected boolean isWeaponInMainhand(){
        ItemStack main = mob.getMainHandItem();
        ItemStack off= mob.getOffhandItem();
        return main.getItem() == weaponSelector.getWeapon() || off.getItem() == weaponSelector.getWeapon() ;
    }
}