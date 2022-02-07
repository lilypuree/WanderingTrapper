package lilypuree.wandering_trapper.core;

import lilypuree.wandering_trapper.Constants;
import lilypuree.wandering_trapper.blocks.BearRugBlock;
import lilypuree.wandering_trapper.blocks.PeltScrapingLogBlock;
import lilypuree.wandering_trapper.entity.TrapperDogEntity;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import lilypuree.wandering_trapper.item.PeltItem;
import lilypuree.wandering_trapper.item.RugItem;
import lilypuree.wandering_trapper.item.WanderingTrapperSpawnEgg;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class RegistryObjects {
    public static Block PELT_SCRAPING_LOG;
    public static Block POLARBEAR_RUG;
    public static Item PELT_SCRAPING_LOG_ITEM;
    public static Item BEAVER_PELT;
    public static Item MARTEN_PELT;
    public static Item MINK_PELT;
    public static Item FOX_PELT;
    public static Item SNOW_FOX_PELT;
    public static Item POLARBEAR_PELT;
    public static Item WANDERING_TRAPPER_SPAWN_EGG;

    public static PoiType FURRIER_POI;
    public static VillagerProfession FURRIER;
    public static EntityType<WanderingTrapperEntity> WANDERING_TRAPPER;
    public static EntityType<TrapperDogEntity> TRAPPER_DOG;

    public static void registerBlocks(RegistryHelper<Block> helper) {
        PELT_SCRAPING_LOG = new PeltScrapingLogBlock(Block.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD));
        POLARBEAR_RUG = new BearRugBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).noOcclusion().noCollission().instabreak().sound(SoundType.WOOL));
        helper.register(PELT_SCRAPING_LOG, RegistryNames.PELT_SCRAPING_LOG);
        helper.register(POLARBEAR_RUG, RegistryNames.POLARBEAR_RUG);
    }

    public static void registerItems(RegistryHelper<Item> helper) {
        Item.Properties properties = new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS);
        PELT_SCRAPING_LOG_ITEM = new BlockItem(RegistryObjects.PELT_SCRAPING_LOG, properties);
        BEAVER_PELT = new PeltItem();
        MARTEN_PELT = new PeltItem();
        MINK_PELT = new PeltItem();
        FOX_PELT = new PeltItem();
        SNOW_FOX_PELT = new PeltItem();
        POLARBEAR_PELT = new RugItem(POLARBEAR_RUG, new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
        WANDERING_TRAPPER_SPAWN_EGG = new WanderingTrapperSpawnEgg();

        helper.register(PELT_SCRAPING_LOG_ITEM, RegistryNames.PELT_SCRAPING_LOG);
        helper.register(BEAVER_PELT, RegistryNames.BEAVER_PELT);
        helper.register(MARTEN_PELT, RegistryNames.MARTEN_PELT);
        helper.register(MINK_PELT, RegistryNames.MINK_PELT);
        helper.register(FOX_PELT, RegistryNames.FOX_PELT);
        helper.register(SNOW_FOX_PELT, RegistryNames.SNOW_FOX_PELT);
        helper.register(POLARBEAR_PELT, RegistryNames.POLARBEAR_PELT);
        helper.register(WANDERING_TRAPPER_SPAWN_EGG, RegistryNames.WANDERING_TRAPPER_SPAWN_EGG);
    }

    public static void registerEntities(RegistryHelper<EntityType<?>> helper) {
        WANDERING_TRAPPER = EntityType.Builder.<WanderingTrapperEntity>of(WanderingTrapperEntity::new, MobCategory.CREATURE)
                .clientTrackingRange(80)
                .updateInterval(3)
                .canSpawnFarFromPlayer()
                .sized(0.6F, 1.95F)
                .build(Constants.MOD_ID + ":wandering_trapper");
        TRAPPER_DOG = EntityType.Builder.of(TrapperDogEntity::new, MobCategory.CREATURE).
                sized(0.6F, 0.85F)
                .canSpawnFarFromPlayer()
                .build(Constants.MOD_ID + "trapper_dog");

        helper.register(WANDERING_TRAPPER, RegistryNames.WANDERING_TRAPPER);
        helper.register(TRAPPER_DOG, RegistryNames.TRAPPER_DOG);
    }
}
