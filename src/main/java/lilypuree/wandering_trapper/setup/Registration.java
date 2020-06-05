package lilypuree.wandering_trapper.setup;

import com.google.common.collect.ImmutableSet;
import lilypuree.wandering_trapper.block.PeltScrapingLogBlock;
import lilypuree.wandering_trapper.compat.BowWeapon;
import lilypuree.wandering_trapper.entity.TrapperDogEntity;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import lilypuree.wandering_trapper.item.PeltItem;
import lilypuree.wandering_trapper.item.WanderingTrapperSpawnEgg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

import static lilypuree.wandering_trapper.WanderingTrapper.MODID;

public class Registration {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS,MODID );
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, MODID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS =  new DeferredRegister<>(ForgeRegistries.PROFESSIONS, MODID);
    public static final DeferredRegister<PointOfInterestType> POIS =  new DeferredRegister<>(ForgeRegistries.POI_TYPES, MODID);



    public static void register(){
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        PROFESSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        POIS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }
    public static final RegistryObject<Block> PELT_SCRAPING_LOG = BLOCKS.register("pelt_scraping_log", ()->new PeltScrapingLogBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)) {
    });
    public static final RegistryObject<Item> PELT_SCRAPING_LOG_ITEM = ITEMS.register("pelt_scraping_log", ()->new BlockItem(PELT_SCRAPING_LOG.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));


    public static final RegistryObject<PeltItem> BEAVER_PELT = ITEMS.register("beaver_pelt", PeltItem::new);
    public static final RegistryObject<PeltItem> MARTEN_PELT = ITEMS.register("marten_pelt", PeltItem::new);
    public static final RegistryObject<PeltItem> MINK_PELT = ITEMS.register("mink_pelt", PeltItem::new);
    public static final RegistryObject<PeltItem> FOX_PELT = ITEMS.register("fox_pelt", PeltItem::new);
    public static final RegistryObject<PeltItem> SNOW_FOX_PELT = ITEMS.register("snow_fox_pelt", PeltItem::new);
    public static final RegistryObject<PeltItem> POLARBEAR_PELT = ITEMS.register("polarbear_pelt", PeltItem::new);
    public static final RegistryObject<WanderingTrapperSpawnEgg> WANDERING_TRADER_SPAWN_EGG = ITEMS.register("wandering_trapper_spawn_egg", WanderingTrapperSpawnEgg::new);

    public static final RegistryObject<PointOfInterestType> FURRIER_POI = POIS.register("furrier", ()->ModProfessions.pointOfInterestType("furrier", getAllStates(PELT_SCRAPING_LOG.get()), 1, 1));

    public static final RegistryObject<VillagerProfession> FURRIER = PROFESSIONS.register("furrier", ()->new ModProfessions().villagerProfession("furrier", FURRIER_POI.get(), SoundEvents.ENTITY_VILLAGER_WORK_LEATHERWORKER));

    public static final RegistryObject<EntityType<WanderingTrapperEntity>> WANDERING_TRAPPER = ENTITIES.register("wandering_trapper", () -> EntityType.Builder.<WanderingTrapperEntity>create((type, world) -> {
        if (ModList.get().isLoaded("musketmod")) {
//            WanderingTrapperEntity.weaponSelector = new MusketWeapon();
            return new WanderingTrapperEntity(type, world);
        } else {
            WanderingTrapperEntity.weaponSelector = new BowWeapon();
            return new WanderingTrapperEntity(type, world);
        }
    }, EntityClassification.CREATURE)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.6F, 1.95F)
            .build(MODID + ":wandering_trapper"));

    public static final RegistryObject<EntityType<TrapperDogEntity>> TRAPPER_DOG = ENTITIES.register("trapper_dog", () -> EntityType.Builder.create(TrapperDogEntity::new, EntityClassification.CREATURE).
            size(0.6F, 0.85F)
            .build(MODID + "trapper_dog"));

    private static Set<BlockState> getAllStates(Block block) {
        return ImmutableSet.copyOf(block.getStateContainer().getValidStates());
    }

}
