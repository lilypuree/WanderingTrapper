package lilypuree.wandering_trapper;

import lilypuree.wandering_trapper.compat.BowWeapon;
import lilypuree.wandering_trapper.compat.MusketWeapon;
import lilypuree.wandering_trapper.core.RegistryHelper;
import lilypuree.wandering_trapper.core.RegistryNames;
import lilypuree.wandering_trapper.core.RegistryObjects;
import lilypuree.wandering_trapper.entity.FurrierTrades;
import lilypuree.wandering_trapper.entity.TrapperDogEntity;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class WanderingTrapperFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        RegistryObjects.registerBlocks(new RegistryHelperFabric<>(Registry.BLOCK));
        RegistryObjects.registerEntities(new RegistryHelperFabric<>(Registry.ENTITY_TYPE));
        RegistryObjects.registerItems(new RegistryHelperFabric<>(Registry.ITEM));
        registerOther();
        FabricDefaultAttributeRegistry.register(RegistryObjects.WANDERING_TRAPPER, WanderingTrapperEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(RegistryObjects.TRAPPER_DOG, TrapperDogEntity.createAttributes());
        addVillagerTrades();
        setTrapperWeapon();
    }

    public static void addVillagerTrades() {
        FurrierTrades.init();
        TradeOfferHelper.registerVillagerOffers(RegistryObjects.FURRIER, 1, factories -> {
            factories.addAll(FurrierTrades.noviceTrades);
        });
        TradeOfferHelper.registerVillagerOffers(RegistryObjects.FURRIER, 2, factories -> factories.addAll(FurrierTrades.apprenticeTrades));
        TradeOfferHelper.registerVillagerOffers(RegistryObjects.FURRIER, 3, factories -> factories.addAll(FurrierTrades.journeymanTrades));
        TradeOfferHelper.registerVillagerOffers(RegistryObjects.FURRIER, 4, factories -> factories.addAll(FurrierTrades.expertTrades));
    }

    public static void setTrapperWeapon() {

        if (FabricLoader.getInstance().isModLoaded("musketmod")) {
            WanderingTrapperEntity.weaponSelector = new MusketWeapon();
        } else {
            WanderingTrapperEntity.weaponSelector = new BowWeapon();
        }
    }

    public static class RegistryHelperFabric<T> implements RegistryHelper<T> {
        Registry<T> registry;

        public RegistryHelperFabric(Registry<T> registry) {
            this.registry = registry;
        }

        @Override
        public void register(T entry, ResourceLocation name) {
            Registry.register(registry, name, entry);
        }
    }

    public static void registerOther() {
        RegistryObjects.FURRIER_POI = PointOfInterestHelper.register(RegistryNames.FURRIER, 1, 1, RegistryObjects.PELT_SCRAPING_LOG);

        RegistryObjects.FURRIER = VillagerProfessionBuilder.create().id(RegistryNames.FURRIER).workstation(RegistryObjects.FURRIER_POI).workSound(SoundEvents.VILLAGER_WORK_LEATHERWORKER).build();
        Registry.register(Registry.VILLAGER_PROFESSION, RegistryNames.FURRIER, RegistryObjects.FURRIER);
    }
}
