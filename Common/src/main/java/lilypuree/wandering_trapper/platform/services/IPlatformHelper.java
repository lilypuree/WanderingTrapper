package lilypuree.wandering_trapper.platform.services;

import lilypuree.wandering_trapper.registration.RegistryObject;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;

import java.util.function.Supplier;

public interface IPlatformHelper {
    Supplier<PoiType> registerPoiType(String name, Supplier<PoiType> poiType);
}
