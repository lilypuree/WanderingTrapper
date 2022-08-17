package lilypuree.wandering_trapper;

import lilypuree.wandering_trapper.client.WanderingTrapperModel;
import lilypuree.wandering_trapper.client.WanderingTrapperRenderer;
import lilypuree.wandering_trapper.core.RegistryObjects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ClientSetupForge {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(RegistryObjects.PELT_SCRAPING_LOG.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(RegistryObjects.POLARBEAR_RUG.get(), RenderType.cutout());
        });
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WanderingTrapperModel.TRAPPER_LAYER, WanderingTrapperModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRendereres(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(RegistryObjects.WANDERING_TRAPPER.get(), WanderingTrapperRenderer::new);
        event.registerEntityRenderer(RegistryObjects.TRAPPER_DOG.get(), WolfRenderer::new);
    }

    @SubscribeEvent
    public static void onItemColor(RegisterColorHandlersEvent.Item event) {
        event.register((stack, i) -> i == 0 ? 0xD7D0CB : 0x862122, RegistryObjects.WANDERING_TRAPPER_SPAWN_EGG.get());
    }
}
