package lilypuree.wandering_trapper.setup;

import lilypuree.wandering_trapper.WanderingTrapper;
import lilypuree.wandering_trapper.client.WanderingTrapperRenderer;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = WanderingTrapper.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event){
        RenderingRegistry.registerEntityRenderingHandler(WanderingTrapperEntity.class, WanderingTrapperRenderer::new);
    }


    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, i) -> (i==0)?0xD7D0CB:0x862122, Registration.WANDERING_TRADER_SPAWN_EGG.get());
    }


}
