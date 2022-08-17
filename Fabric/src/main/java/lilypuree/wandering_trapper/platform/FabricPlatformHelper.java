package lilypuree.wandering_trapper.platform;

import lilypuree.wandering_trapper.Constants;
import lilypuree.wandering_trapper.mixins.POITypesInvoker;
import lilypuree.wandering_trapper.platform.services.IPlatformHelper;
import lilypuree.wandering_trapper.registration.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.function.Supplier;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public Supplier<PoiType> registerPoiType(String name, Supplier<PoiType> poiType) {
        ResourceKey<PoiType> resourceKey = ResourceKey.create(Registry.POINT_OF_INTEREST_TYPE_REGISTRY, new ResourceLocation(Constants.MOD_ID, name));
        var registry = Registry.register(Registry.POINT_OF_INTEREST_TYPE, resourceKey, poiType.get());
        POITypesInvoker.invokeRegisterBlockStates(Registry.POINT_OF_INTEREST_TYPE.getHolderOrThrow(resourceKey));
        return () -> registry;
    }
}
