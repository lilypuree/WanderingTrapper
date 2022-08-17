package lilypuree.wandering_trapper.client;

import lilypuree.wandering_trapper.Constants;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class WanderingTrapperRenderer<T extends WanderingTrapperEntity> extends MobRenderer<T, WanderingTrapperModel<T>> {

    private static final ResourceLocation TRAPPER_TEXTURE = new ResourceLocation(Constants.MOD_ID, "textures/entity/wandering_trapper.png");

    public WanderingTrapperRenderer(EntityRendererProvider.Context context) {
        super(context, new WanderingTrapperModel<>(context.bakeLayer(WanderingTrapperModel.TRAPPER_LAYER)), 0.5F);
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TRAPPER_TEXTURE;
    }
}
