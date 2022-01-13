package lilypuree.wandering_trapper.core;

import com.google.common.collect.ImmutableSet;
import lilypuree.wandering_trapper.Constants;
import lilypuree.wandering_trapper.blocks.PeltScrapingLogBlock;
import lilypuree.wandering_trapper.entity.TrapperDogEntity;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import lilypuree.wandering_trapper.item.PeltItem;
import lilypuree.wandering_trapper.item.WanderingTrapperSpawnEgg;
import lilypuree.wandering_trapper.mixins.PoiTypeInvoker;
import lilypuree.wandering_trapper.mixins.VillagerProfessionInvoker;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.Set;
import java.util.function.Supplier;

public class RegistrySuppliers {
    public static Supplier<Block> PELT_SCRAPING_LOG = () -> new PeltScrapingLogBlock(Block.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD));
    public static Supplier<Item> PELT_SCRAPING_LOG_ITEM = () -> new BlockItem(RegistryObjects.PELT_SCRAPING_LOG, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static Supplier<Item> PELT_ITEM = PeltItem::new;

    public static Supplier<Item> WANDERING_TRAPPER_SPAWN_EGG = WanderingTrapperSpawnEgg::new;

    public static Supplier<PoiType> FURRIER_POI = () -> PoiTypeInvoker.invokeInit("furrier", getAllStates(RegistryObjects.PELT_SCRAPING_LOG), 1, 1);
    public static Supplier<VillagerProfession> FURRIER = () -> VillagerProfessionInvoker.invokeRegister("furrier", RegistryObjects.FURRIER_POI, ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_LEATHERWORKER);
    public static Supplier<EntityType<WanderingTrapperEntity>> WANDERING_TRAPPER = () -> EntityType.Builder.<WanderingTrapperEntity>of(WanderingTrapperEntity::new, MobCategory.CREATURE)
            .clientTrackingRange(80)
            .updateInterval(3)
            .canSpawnFarFromPlayer()
            .sized(0.6F, 1.95F)
            .build(Constants.MOD_ID + ":wandering_trapper");
    public static Supplier<EntityType<TrapperDogEntity>> TRAPPER_DOG = () -> EntityType.Builder.of(TrapperDogEntity::new, MobCategory.CREATURE).
            sized(0.6F, 0.85F)
            .canSpawnFarFromPlayer()
            .build(Constants.MOD_ID + "trapper_dog");

    private static Set<BlockState> getAllStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }
}
