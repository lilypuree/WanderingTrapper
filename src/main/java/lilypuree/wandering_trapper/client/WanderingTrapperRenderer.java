package lilypuree.wandering_trapper.client;

import lilypuree.wandering_trapper.WanderingTrapper;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WanderingTrapperRenderer <T extends WanderingTrapperEntity> extends MobRenderer<T, WanderingTrapperModel<T>> {

    private static final ResourceLocation TRAPPER_TEXTURE = new ResourceLocation(WanderingTrapper.MODID, "textures/entity/wandering_trapper.png");

    public WanderingTrapperRenderer(EntityRendererManager rendererManagerIn){
        super(rendererManagerIn, new WanderingTrapperModel<>(0.0F), 0.5F);
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new HeldItemLayer<>(this));
    }

    @Override
    public ResourceLocation getEntityTexture(T entity) {
        return TRAPPER_TEXTURE;
    }


}
