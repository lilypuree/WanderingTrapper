package lilypuree.wandering_trapper.platform;

import lilypuree.wandering_trapper.Constants;
import lilypuree.wandering_trapper.platform.services.IPlatformHelper;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ForgePlatformHelper implements IPlatformHelper {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Constants.MOD_ID);

    @Override
    public Supplier<PoiType> registerPoiType(String name, Supplier<PoiType> poiType) {
        return POI_TYPES.register(name, poiType);
    }
}

