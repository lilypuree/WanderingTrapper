package lilypuree.wandering_trapper.capability;

import lilypuree.wandering_trapper.Constants;
import net.minecraft.core.Direction;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class HuntingExperienceProvider implements ICapabilitySerializable<Tag> {

    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Constants.MOD_ID, "hunting_experience");
    public static final Capability<IHuntingExperience> HUNTING_EXP_CAP = CapabilityManager.get(new CapabilityToken<IHuntingExperience>() {
    });

    private final HuntingExperience backend = new HuntingExperience();
    private LazyOptional<IHuntingExperience> instance = LazyOptional.of(() -> backend);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == HUNTING_EXP_CAP ? instance.cast() : LazyOptional.empty();
    }



    @Override
    public Tag serializeNBT() {
        return IntTag.valueOf(instance.orElseThrow(IllegalArgumentException::new).getExperience());
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        instance.orElseThrow(IllegalArgumentException::new).setExperience(((NumericTag) nbt).getAsInt());
    }
}
