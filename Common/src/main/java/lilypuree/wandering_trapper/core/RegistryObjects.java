package lilypuree.wandering_trapper.core;

import com.google.common.collect.ImmutableSet;
import lilypuree.wandering_trapper.Constants;
import lilypuree.wandering_trapper.entity.TrapperDogEntity;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import lilypuree.wandering_trapper.item.WanderingTrapperSpawnEgg;
import lilypuree.wandering_trapper.mixins.POITypesInvoker;
import lilypuree.wandering_trapper.platform.Services;
import lilypuree.wandering_trapper.registration.BlockRegistryObject;
import lilypuree.wandering_trapper.registration.RegistrationProvider;
import lilypuree.wandering_trapper.registration.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class RegistryObjects {

    public static final RegistrationProvider<Block> BLOCK_REGISTRY = RegistrationProvider.get(Registry.BLOCK, Constants.MOD_ID);
    public static final RegistrationProvider<Item> ITEM_REGISTRY = RegistrationProvider.get(Registry.ITEM, Constants.MOD_ID);
    public static final RegistrationProvider<EntityType<?>> ENTITYTYPE_REGISTRY = RegistrationProvider.get(Registry.ENTITY_TYPE, Constants.MOD_ID);
    public static final RegistrationProvider<VillagerProfession> VILLAGER_REGISTRY = RegistrationProvider.get(Registry.VILLAGER_PROFESSION, Constants.MOD_ID);
    public static final BlockRegistryObject<Block> PELT_SCRAPING_LOG = registerBlock("pelt_scraping_log", RegistrySuppliers.PELT_SCRAPING_LOG);
    public static final BlockRegistryObject<Block> POLARBEAR_RUG = registerBlock("polarbear_rug", RegistrySuppliers.POLARBEAR_RUG);
    public static final RegistryObject<Item> PELT_SCRAPING_LOG_ITEM = registerItem("pelt_scraping_log", RegistrySuppliers.PELT_SCRAPING_LOG_ITEM);
    public static final RegistryObject<Item> BEAVER_PELT = registerItem("beaver_pelt", RegistrySuppliers.PELT_ITEM);
    public static final RegistryObject<Item> MARTEN_PELT = registerItem("marten_pelt", RegistrySuppliers.PELT_ITEM);
    public static final RegistryObject<Item> MINK_PELT = registerItem("mink_pelt", RegistrySuppliers.PELT_ITEM);
    public static final RegistryObject<Item> FOX_PELT = registerItem("fox_pelt", RegistrySuppliers.PELT_ITEM);
    public static final RegistryObject<Item> SNOW_FOX_PELT = registerItem("snow_fox_pelt", RegistrySuppliers.PELT_ITEM);
    public static final RegistryObject<Item> POLARBEAR_PELT = registerItem("polarbear_pelt", RegistrySuppliers.POLARBEAR_PELT);
    public static final RegistryObject<Item> WANDERING_TRAPPER_SPAWN_EGG = registerItem("wandering_trapper_spawn_egg", WanderingTrapperSpawnEgg::new);

    public static final RegistryObject<EntityType<WanderingTrapperEntity>> WANDERING_TRAPPER = ENTITYTYPE_REGISTRY.register("wandering_trapper", RegistrySuppliers.WANDERING_TRAPPER);
    public static final RegistryObject<EntityType<TrapperDogEntity>> TRAPPER_DOG = ENTITYTYPE_REGISTRY.register("trapper_dog", RegistrySuppliers.TRAPPER_DOG);

    public static final Supplier<PoiType> FURRIER_POI = Services.PLATFORM.registerPoiType("furrier", () -> new PoiType(POITypesInvoker.invokeGetBlockStates(PELT_SCRAPING_LOG.get()), 1, 1));
    public static final RegistryObject<VillagerProfession> FURRIER = VILLAGER_REGISTRY.register("furrier",
            () -> new VillagerProfession("furrier", holder -> holder.value().equals(FURRIER_POI.get()), holder -> holder.value().equals(FURRIER_POI.get()), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_LEATHERWORKER));

    private static BlockRegistryObject<Block> registerBlock(String name, Supplier<Block> blockSupplier) {
        return BlockRegistryObject.wrap(BLOCK_REGISTRY.register(name, blockSupplier));
    }

    private static RegistryObject<Item> registerItem(String name, Supplier<Item> itemSupplier) {
        return ITEM_REGISTRY.register(name, itemSupplier);
    }

    public static void init() {

    }
}
