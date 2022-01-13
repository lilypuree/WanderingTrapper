package lilypuree.wandering_trapper;

import lilypuree.wandering_trapper.compat.BowWeapon;
import lilypuree.wandering_trapper.compat.MusketWeapon;
import lilypuree.wandering_trapper.core.RegistryObjects;
import lilypuree.wandering_trapper.entity.FurrierTrades;
import lilypuree.wandering_trapper.entity.TrapperDogEntity;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.loader.api.FabricLoader;

public class WanderingTrapperFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Registration.onRegistration();
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
}
