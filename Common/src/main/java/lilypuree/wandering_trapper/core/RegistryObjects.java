package lilypuree.wandering_trapper.core;

import lilypuree.wandering_trapper.entity.TrapperDogEntity;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class RegistryObjects {
    public static Block PELT_SCRAPING_LOG;
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


    public static void initBlocks() {
        PELT_SCRAPING_LOG = RegistrySuppliers.PELT_SCRAPING_LOG.get();
    }

    public static void initItems() {
        PELT_SCRAPING_LOG_ITEM = RegistrySuppliers.PELT_SCRAPING_LOG_ITEM.get();
        BEAVER_PELT = RegistrySuppliers.PELT_ITEM.get();
        MARTEN_PELT = RegistrySuppliers.PELT_ITEM.get();
        MINK_PELT = RegistrySuppliers.PELT_ITEM.get();
        FOX_PELT = RegistrySuppliers.PELT_ITEM.get();
        SNOW_FOX_PELT = RegistrySuppliers.PELT_ITEM.get();
        POLARBEAR_PELT = RegistrySuppliers.PELT_ITEM.get();
        WANDERING_TRAPPER_SPAWN_EGG = RegistrySuppliers.WANDERING_TRAPPER_SPAWN_EGG.get();
    }

    public static void initEntities() {
        WANDERING_TRAPPER = RegistrySuppliers.WANDERING_TRAPPER.get();
        TRAPPER_DOG = RegistrySuppliers.TRAPPER_DOG.get();
    }
}
