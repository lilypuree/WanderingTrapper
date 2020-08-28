package lilypuree.wandering_trapper.setup;

import lilypuree.wandering_trapper.WanderingTrapper;
import lilypuree.wandering_trapper.client.WanderingTrapperRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(modid = WanderingTrapper.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(Registration.WANDERING_TRAPPER.get(), WanderingTrapperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Registration.TRAPPER_DOG.get(), WolfRenderer::new);
        RenderTypeLookup.setRenderLayer(Registration.PELT_SCRAPING_LOG.get(), RenderType.getTranslucent());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, i) -> (i == 0) ? 0xD7D0CB : 0x862122, Registration.WANDERING_TRADER_SPAWN_EGG.get());
    }


}
