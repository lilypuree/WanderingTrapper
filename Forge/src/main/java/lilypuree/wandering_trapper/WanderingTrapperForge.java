package lilypuree.wandering_trapper;

import lilypuree.wandering_trapper.compat.BowWeapon;
import lilypuree.wandering_trapper.core.RegistryObjects;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import lilypuree.wandering_trapper.mixins.POITypesInvoker;
import lilypuree.wandering_trapper.platform.ForgePlatformHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class WanderingTrapperForge {

    public WanderingTrapperForge() {
        RegistryObjects.init();
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ForgePlatformHelper.POI_TYPES.register(modBus);
        modBus.addListener(CommonSetup::entityAttributes);
        modBus.addListener(WanderingTrapperForge::setup);

    }

    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            if (ModList.get().isLoaded("musketmod")) {
//                WanderingTrapperEntity.weaponSelector = new MusketWeapon();
            } else {
                WanderingTrapperEntity.weaponSelector = new BowWeapon();
            }
            registerBlockStates();
        });

    }

    public static void registerBlockStates() {
        POITypesInvoker.invokeGetBlockStates(RegistryObjects.PELT_SCRAPING_LOG.get()).forEach((state) -> POITypesInvoker.getTypeByState().put(state, Registry.POINT_OF_INTEREST_TYPE.getHolder(ResourceKey.create(Registry.POINT_OF_INTEREST_TYPE_REGISTRY, new ResourceLocation(Constants.MOD_ID, "furrier"))).get()));
    }
}
