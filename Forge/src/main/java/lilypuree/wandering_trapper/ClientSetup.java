package lilypuree.wandering_trapper;

import lilypuree.wandering_trapper.client.WanderingTrapperModel;
import lilypuree.wandering_trapper.client.WanderingTrapperRenderer;
import lilypuree.wandering_trapper.core.RegistryObjects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(RegistryObjects.PELT_SCRAPING_LOG, RenderType.translucent());
        });
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WanderingTrapperModel.TRAPPER_LAYER, WanderingTrapperModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRendereres(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(RegistryObjects.WANDERING_TRAPPER, WanderingTrapperRenderer::new);
        event.registerEntityRenderer(RegistryObjects.TRAPPER_DOG, WolfRenderer::new);
    }

    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, i) -> (i == 0) ? 0xD7D0CB : 0x862122, RegistryObjects.WANDERING_TRAPPER_SPAWN_EGG);
    }
}
