package lilypuree.wandering_trapper.setup;

import lilypuree.wandering_trapper.WanderingTrapper;
import lilypuree.wandering_trapper.capability.HuntingExperience;
import lilypuree.wandering_trapper.capability.HuntingExperienceProvider;
import lilypuree.wandering_trapper.capability.HuntingExperienceStorage;
import lilypuree.wandering_trapper.capability.IHuntingExperience;
import lilypuree.wandering_trapper.server.WanderingTrapperSpawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

@Mod.EventBusSubscriber(modid = WanderingTrapper.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {

    @CapabilityInject(IHuntingExperience.class)
    public static final Capability<IHuntingExperience> HUNTING_EXP_CAP = null;

    @Nullable
    private static WanderingTrapperSpawner wanderingTrapperSpawner = null;

    public void init(FMLCommonSetupEvent e) {
        CapabilityManager.INSTANCE.register(IHuntingExperience.class, new HuntingExperienceStorage(), HuntingExperience::new);
    }

    @SubscribeEvent
    public static void onServerSetUp(FMLServerStartingEvent event) {
        wanderingTrapperSpawner = new WanderingTrapperSpawner(event.getServer().getWorld(DimensionType.OVERWORLD));
    }

    @SubscribeEvent
    public static void onAttatchCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(WanderingTrapper.MODID, "hunting_experience"), new HuntingExperienceProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;
        LazyOptional<IHuntingExperience> oldHuntingExperience = event.getOriginal().getCapability(HUNTING_EXP_CAP);
        oldHuntingExperience.ifPresent(oldXP -> {
            LazyOptional<IHuntingExperience> newhuntingExperience = event.getPlayer().getCapability(HUNTING_EXP_CAP);
            newhuntingExperience.ifPresent(newXP -> newXP.copyFrom(oldXP));
        });
    }

    @SubscribeEvent
    public static void onSetVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.LEATHERWORKER) {
            ArrayList<VillagerTrades.ITrade> trades = new ArrayList<>();
            trades.add(new BasicTrade(new ItemStack(Items.EMERALD, 3), new ItemStack(Registration.POLARBEAR_PELT.get(), 1), new ItemStack(Items.LEATHER, 5), 12, 10, 0.05F));
            trades.add(new BasicTrade(new ItemStack(Items.EMERALD, 3), new ItemStack(Registration.BEAVER_PELT.get(), 3), new ItemStack(Items.LEATHER, 1), 16, 10, 0.05F));
            event.getTrades().put(1, trades);
        } else if (event.getType() == Registration.FURRIER.get()) {
            ArrayList<VillagerTrades.ITrade> trades = new ArrayList<>();
            trades.add(new BasicTrade(new ItemStack(Items.EMERALD, 3), new ItemStack(Registration.POLARBEAR_PELT.get(), 1), new ItemStack(Items.LEATHER, 5), 12, 10, 0.05F));
            trades.add(new BasicTrade(new ItemStack(Items.EMERALD, 3), new ItemStack(Registration.BEAVER_PELT.get(), 3), new ItemStack(Items.LEATHER, 1), 16, 10, 0.05F));
            event.getTrades().put(1, trades);
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (wanderingTrapperSpawner != null) {
            wanderingTrapperSpawner.tick();
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH, receiveCanceled = true)
    public static void onLivingDrop(LivingDropsEvent event) {

        LivingEntity entity = event.getEntityLiving();
        final Random random = new Random();
        final float baseDropChance = 0.001F;

        if (entity instanceof PolarBearEntity) {
            event.getSource().getTrueSource().getCapability(HUNTING_EXP_CAP).ifPresent(experience -> {
                experience.add(5);
                if (baseDropChance + MathHelper.clamp(experience.getExperience() * 0.001F, 0, 0.1) > random.nextFloat()) {
                    entity.entityDropItem(new ItemStack(Registration.POLARBEAR_PELT.get()));
                }
            });
        } else if (entity instanceof FoxEntity) {
            if (((FoxEntity) entity).getVariantType() == FoxEntity.Type.RED) {
                event.getSource().getTrueSource().getCapability(HUNTING_EXP_CAP).ifPresent(experience -> {
                    experience.add(2);
//                    System.out.println(experience.getExperience() + "fox");
                    if (baseDropChance + MathHelper.clamp(experience.getExperience() * 0.001F, 0, 0.1) > random.nextFloat()) {
                        entity.entityDropItem(new ItemStack(Registration.FOX_PELT.get()));
                    }
                });
            } else {
                event.getSource().getTrueSource().getCapability(HUNTING_EXP_CAP).ifPresent(experience -> {
                    experience.add(4);
                    if (baseDropChance + MathHelper.clamp(experience.getExperience() * 0.001F, 0, 0.1) > random.nextFloat()) {
                        entity.entityDropItem(new ItemStack(Registration.SNOW_FOX_PELT.get()));
                    }
                });
            }
        }
    }
}
