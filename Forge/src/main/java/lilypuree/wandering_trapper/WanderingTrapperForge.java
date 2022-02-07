package lilypuree.wandering_trapper;

import lilypuree.wandering_trapper.compat.BowWeapon;
import lilypuree.wandering_trapper.compat.MusketWeapon;
import lilypuree.wandering_trapper.core.RegistryHelper;
import lilypuree.wandering_trapper.core.RegistryObjects;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod(Constants.MOD_ID)
public class WanderingTrapperForge {

    public WanderingTrapperForge() {

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(CommonSetup::entityAttributes);
        modBus.addListener(WanderingTrapperForge::setTrapperWeapon);

        modBus.addGenericListener(Block.class, (RegistryEvent.Register<Block> e) -> RegistryObjects.registerBlocks(new RegistryHelperForge<>(e.getRegistry())));
        modBus.addGenericListener(Item.class, (RegistryEvent.Register<Item> e) -> RegistryObjects.registerItems(new RegistryHelperForge<>(e.getRegistry())));
        modBus.addGenericListener(EntityType.class, (RegistryEvent.Register<EntityType<?>> e) -> RegistryObjects.registerEntities(new RegistryHelperForge<>(e.getRegistry())));

    }

    public static void setTrapperWeapon(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            if (ModList.get().isLoaded("musketmod")) {
                WanderingTrapperEntity.weaponSelector = new MusketWeapon();
            } else {
                WanderingTrapperEntity.weaponSelector = new BowWeapon();
            }
        });
    }

    public static class RegistryHelperForge<T extends IForgeRegistryEntry<T>> implements RegistryHelper<T> {
        IForgeRegistry<T> registry;

        public RegistryHelperForge(IForgeRegistry<T> registry) {
            this.registry = registry;
        }

        @Override
        public void register(T entry, ResourceLocation name) {
            registry.register(entry.setRegistryName(name));
        }
    }

}
