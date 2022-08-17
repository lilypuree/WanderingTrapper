package lilypuree.wandering_trapper.entity;

import lilypuree.wandering_trapper.core.RegistryObjects;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class FurrierTrades {

    public static List<VillagerTrades.ItemListing> noviceTrades;
    public static List<VillagerTrades.ItemListing> apprenticeTrades;
    public static List<VillagerTrades.ItemListing> journeymanTrades;
    public static List<VillagerTrades.ItemListing> expertTrades;

    public static void init(){
        noviceTrades = new ArrayList<>();
        apprenticeTrades = new ArrayList<>();
        journeymanTrades = new ArrayList<>();
        expertTrades = new ArrayList<>();

        noviceTrades.add(new TradeImpl(3, RegistryObjects.POLARBEAR_PELT.get(), 1, Items.LEATHER, 5, 12, 10, 0.05F));
        noviceTrades.add(new TradeImpl(3, RegistryObjects.BEAVER_PELT.get(), 3, Items.LEATHER, 1, 16, 10, 0.05F));
        apprenticeTrades.add(new TradeImpl(RegistryObjects.BEAVER_PELT.get(), 10, 50, 5, 10, 0.05F));
        apprenticeTrades.add(new TradeImpl(RegistryObjects.MARTEN_PELT.get(), 15, 56, 5, 10, 0.05F));
        journeymanTrades.add(new TradeImpl(RegistryObjects.FOX_PELT.get(), 6, 60, 5, 10, 0.05F));
        journeymanTrades.add(new TradeImpl(RegistryObjects.SNOW_FOX_PELT.get(), 3, 64, 5, 10, 0.05F));
        journeymanTrades.add(new TradeImpl(RegistryObjects.POLARBEAR_PELT.get(), 1, 32, 5, 10, 0.05F));
        expertTrades.add(new TradeImpl(RegistryObjects.MINK_PELT.get(), 6, 64, 5, 10, 0.05F));

    }

}
