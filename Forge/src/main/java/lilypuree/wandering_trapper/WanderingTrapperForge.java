package lilypuree.wandering_trapper;

import lilypuree.wandering_trapper.compat.BowWeapon;
import lilypuree.wandering_trapper.compat.MusketWeapon;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class WanderingTrapperForge {

    public WanderingTrapperForge() {

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(CommonSetup::entityAttributes);
        modBus.addListener(WanderingTrapperForge::setTrapperWeapon);
    }

    public static void setTrapperWeapon(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            if (ModList.get().isLoaded("musketmod")) {
                WanderingTrapperEntity.weaponSelector = new MusketWeapon();
            } else {
                WanderingTrapperEntity.weaponSelector = new BowWeapon();
            }
        });
    }
}
