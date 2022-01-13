package lilypuree.wandering_trapper;

import lilypuree.wandering_trapper.client.WanderingTrapperModel;
import lilypuree.wandering_trapper.client.WanderingTrapperRenderer;
import lilypuree.wandering_trapper.core.RegistryObjects;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.WolfRenderer;

public class ClientSetupFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(RegistryObjects.PELT_SCRAPING_LOG, RenderType.translucent());
        ColorProviderRegistry.ITEM.register((stack, i) -> (i == 0) ? 0xD7D0CB : 0x862122, RegistryObjects.WANDERING_TRAPPER_SPAWN_EGG);
        EntityRendererRegistry.register(RegistryObjects.WANDERING_TRAPPER, WanderingTrapperRenderer::new);
        EntityRendererRegistry.register(RegistryObjects.TRAPPER_DOG, WolfRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(WanderingTrapperModel.TRAPPER_LAYER, WanderingTrapperModel::createBodyLayer);
    }
}
