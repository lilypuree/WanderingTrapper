package lilypuree.wandering_trapper.core;

import lilypuree.wandering_trapper.Constants;
import lilypuree.wandering_trapper.blocks.BearRugBlock;
import lilypuree.wandering_trapper.blocks.PeltScrapingLogBlock;
import lilypuree.wandering_trapper.entity.TrapperDogEntity;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import lilypuree.wandering_trapper.item.PeltItem;
import lilypuree.wandering_trapper.item.RugItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Supplier;

public class RegistrySuppliers {

    protected static final Supplier<Block> PELT_SCRAPING_LOG = () -> new PeltScrapingLogBlock(Block.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD));
    protected static final Supplier<Block> POLARBEAR_RUG = () -> new BearRugBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).noOcclusion().noCollission().instabreak().sound(SoundType.WOOL));

    private static final Item.Properties properties = new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS);
    protected static final Supplier<Item> PELT_SCRAPING_LOG_ITEM = () -> new BlockItem(RegistryObjects.PELT_SCRAPING_LOG.get(), properties);
    protected static final Supplier<Item> PELT_ITEM = PeltItem::new;
    protected static final Supplier<Item> POLARBEAR_PELT = () -> new RugItem(RegistryObjects.POLARBEAR_RUG.get(), new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));

    protected static final Supplier<EntityType<WanderingTrapperEntity>> WANDERING_TRAPPER = () -> EntityType.Builder.<WanderingTrapperEntity>of(WanderingTrapperEntity::new, MobCategory.CREATURE)
            .clientTrackingRange(80)
            .updateInterval(3)
            .canSpawnFarFromPlayer()
            .sized(0.6F, 1.95F)
            .build(Constants.MOD_ID + ":wandering_trapper");

    protected static final Supplier<EntityType<TrapperDogEntity>> TRAPPER_DOG = () -> EntityType.Builder.of(TrapperDogEntity::new, MobCategory.CREATURE).
            sized(0.6F, 0.85F)
            .canSpawnFarFromPlayer()
            .build(Constants.MOD_ID + "trapper_dog");
}
