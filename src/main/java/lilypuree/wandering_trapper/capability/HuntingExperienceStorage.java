package lilypuree.wandering_trapper.capability;

import net.minecraft.nbt.*;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class HuntingExperienceStorage implements Capability.IStorage<IHuntingExperience> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IHuntingExperience> capability, IHuntingExperience instance, Direction side) {
        return IntNBT.func_229692_a_(instance.getExperience());
    }

    @Override
    public void readNBT(Capability<IHuntingExperience> capability, IHuntingExperience instance, Direction side, INBT nbt) {
        instance.setExperience(((NumberNBT)nbt).getInt());
    }
}
