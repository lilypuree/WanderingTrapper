package lilypuree.wandering_trapper;

import com.google.common.collect.ImmutableSet;
import lilypuree.wandering_trapper.core.RegistryHelper;
import lilypuree.wandering_trapper.core.RegistryNames;
import lilypuree.wandering_trapper.core.RegistryObjects;
import lilypuree.wandering_trapper.entity.TrapperDogEntity;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Set;

import static lilypuree.wandering_trapper.Constants.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registration {


    @SubscribeEvent
    public static void registerPOIs(RegistryEvent.Register<PoiType> event) {
        event.getRegistry().register(RegistryObjects.FURRIER_POI = new PoiType("furrier", getAllStates(RegistryObjects.PELT_SCRAPING_LOG), 1, 1).setRegistryName(RegistryNames.FURRIER));
    }

    @SubscribeEvent
    public static void registerVillagers(RegistryEvent.Register<VillagerProfession> event) {
        event.getRegistry().register(RegistryObjects.FURRIER = new VillagerProfession("furrier", RegistryObjects.FURRIER_POI, ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_LEATHERWORKER).setRegistryName(RegistryNames.FURRIER));
    }

    private static Set<BlockState> getAllStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }
}
