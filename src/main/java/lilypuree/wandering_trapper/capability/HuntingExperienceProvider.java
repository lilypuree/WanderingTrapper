package lilypuree.wandering_trapper.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HuntingExperienceProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(IHuntingExperience.class)
    public static final Capability<IHuntingExperience> HUNTING_EXP_CAP = null;

    private LazyOptional<IHuntingExperience> instance = LazyOptional.of(HUNTING_EXP_CAP::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == HUNTING_EXP_CAP ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return HUNTING_EXP_CAP.getStorage().writeNBT(HUNTING_EXP_CAP, this.instance.orElseThrow(()->new IllegalArgumentException()), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        HUNTING_EXP_CAP.getStorage().readNBT(HUNTING_EXP_CAP,this.instance.orElseThrow(()->new IllegalArgumentException()), null,nbt);
    }
}
