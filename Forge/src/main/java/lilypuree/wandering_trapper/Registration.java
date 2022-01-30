package lilypuree.wandering_trapper;

import com.google.common.collect.ImmutableSet;
import lilypuree.wandering_trapper.core.RegistryNames;
import lilypuree.wandering_trapper.core.RegistryObjects;
import lilypuree.wandering_trapper.core.RegistrySuppliers;
import lilypuree.wandering_trapper.entity.TrapperDogEntity;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

import static lilypuree.wandering_trapper.Constants.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registration {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        RegistryObjects.initBlocks();
        event.getRegistry().registerAll(RegistryObjects.PELT_SCRAPING_LOG.setRegistryName(RegistryNames.PELT_SCRAPING_LOG));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        RegistryObjects.initItems();
        event.getRegistry().registerAll(
                RegistryObjects.PELT_SCRAPING_LOG_ITEM.setRegistryName(RegistryNames.PELT_SCRAPING_LOG),
                RegistryObjects.BEAVER_PELT.setRegistryName(RegistryNames.BEAVER_PELT),
                RegistryObjects.MARTEN_PELT.setRegistryName(RegistryNames.MARTEN_PELT),
                RegistryObjects .MINK_PELT.setRegistryName(RegistryNames.MINK_PELT),
                RegistryObjects.FOX_PELT.setRegistryName(RegistryNames.FOX_PELT),
                RegistryObjects.SNOW_FOX_PELT.setRegistryName(RegistryNames.SNOW_FOX_PELT),
                RegistryObjects.POLARBEAR_PELT.setRegistryName(RegistryNames.POLARBEAR_PELT),
                RegistryObjects.WANDERING_TRAPPER_SPAWN_EGG.setRegistryName(RegistryNames.WANDERING_TRAPPER_SPAWN_EGG)
        );
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        RegistryObjects.initEntities();
        event.getRegistry().register(RegistryObjects.WANDERING_TRAPPER = (EntityType<WanderingTrapperEntity>) RegistrySuppliers.WANDERING_TRAPPER.get().setRegistryName(RegistryNames.WANDERING_TRAPPER));
        event.getRegistry().register(RegistryObjects.TRAPPER_DOG = (EntityType<TrapperDogEntity>) RegistrySuppliers.TRAPPER_DOG.get().setRegistryName(RegistryNames.TRAPPER_DOG));
    }

    @SubscribeEvent
    public static void registerPOIs(RegistryEvent.Register<PoiType> event) {
        event.getRegistry().register(RegistryObjects.FURRIER_POI = new PoiType("furrier", getAllStates(RegistryObjects.PELT_SCRAPING_LOG), 1, 1).setRegistryName(RegistryNames.FURRIER));
    }

    @SubscribeEvent
    public static void registerVillagers(RegistryEvent.Register<VillagerProfession> event) {
        event.getRegistry().register(RegistryObjects.FURRIER =new VillagerProfession("furrier", RegistryObjects.FURRIER_POI, ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_LEATHERWORKER).setRegistryName( RegistryNames.FURRIER));
    }

    private static Set<BlockState> getAllStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }
}
