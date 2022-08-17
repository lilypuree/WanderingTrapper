package lilypuree.wandering_trapper;

import lilypuree.wandering_trapper.capability.HuntingExperienceProvider;
import lilypuree.wandering_trapper.capability.IHuntingExperience;
import lilypuree.wandering_trapper.core.RegistryObjects;
import lilypuree.wandering_trapper.entity.FurrierTrades;
import lilypuree.wandering_trapper.entity.TrapperDogEntity;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonSetup {
    private static final Capability<IHuntingExperience> HUNTING_EXP_CAP = CapabilityManager.get(new CapabilityToken<IHuntingExperience>() {
    });

    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(RegistryObjects.WANDERING_TRAPPER.get(), WanderingTrapperEntity.createAttributes().build());
        event.put(RegistryObjects.TRAPPER_DOG.get(), TrapperDogEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void onAttatchCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(HuntingExperienceProvider.IDENTIFIER, new HuntingExperienceProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;
        LazyOptional<IHuntingExperience> oldHuntingExperience = event.getOriginal().getCapability(HUNTING_EXP_CAP);
        oldHuntingExperience.ifPresent(oldXP -> {
            LazyOptional<IHuntingExperience> newhuntingExperience = event.getEntity().getCapability(HUNTING_EXP_CAP);
            newhuntingExperience.ifPresent(newXP -> newXP.copyFrom(oldXP));
        });
    }


    @SubscribeEvent
    public static void onSetVillagerTrades(VillagerTradesEvent event) {
        FurrierTrades.init();
        if (event.getType() == RegistryObjects.FURRIER.get()) {
            event.getTrades().put(1, FurrierTrades.noviceTrades);
            event.getTrades().put(2, FurrierTrades.apprenticeTrades);
            event.getTrades().put(3, FurrierTrades.journeymanTrades);
            event.getTrades().put(4, FurrierTrades.expertTrades);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH, receiveCanceled = true)
    public static void onLivingDrop(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        Entity killer = event.getSource().getEntity();

        if (!(killer instanceof Player)) return;

        if (entity instanceof PolarBear) {
            dropItem(killer, entity, 6, new ItemStack(RegistryObjects.POLARBEAR_PELT.get()));
        } else if (entity instanceof Fox fox) {
            if (fox.getFoxType() == Fox.Type.RED) {
                dropItem(killer, entity, 2, new ItemStack(RegistryObjects.FOX_PELT.get()));
            } else {
                dropItem(killer, entity, 4, new ItemStack(RegistryObjects.SNOW_FOX_PELT.get()));
            }
        }
//        else if (entity.getType() == AAEntities.PINE_MARTEN.get()){
//            dropItem(killer, entity, 2, new ItemStack(RegistryObjects.MARTEN_PELT));
//        }
    }

    private static void dropItem(Entity player, Entity killed, int huntingExperience, ItemStack stack) {
        final float baseDropChance = 0.001F;
        player.getCapability(HUNTING_EXP_CAP).ifPresent(experience -> {
            experience.add(huntingExperience);
            if (baseDropChance + Mth.clamp(experience.getExperience() * 0.001F, 0, 0.1) > player.level.random.nextFloat()) {
                killed.spawnAtLocation(stack);
            }
        });
    }
}
