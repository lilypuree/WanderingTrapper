package lilypuree.wandering_trapper.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;

import java.util.Random;

public class TradeImpl implements VillagerTrades.ItemListing {

    protected final ItemStack price;
    protected final ItemStack price2;
    protected final ItemStack forSale;
    protected final int maxTrades;
    protected final int xp;
    protected final float priceMult;

    public TradeImpl(ItemStack price, ItemStack price2, ItemStack forSale, int maxTrades, int xp, float priceMult) {
        this.price = price;
        this.price2 = price2;
        this.forSale = forSale;
        this.maxTrades = maxTrades;
        this.xp = xp;
        this.priceMult = priceMult;
    }

    public TradeImpl(ItemLike item, int count, int emeralds, int maxTrades, int xp, float priceMult) {
        this(new ItemStack(item, count), ItemStack.EMPTY, new ItemStack(Items.EMERALD, emeralds), maxTrades, xp, priceMult);
    }

    public TradeImpl(int emeralds, ItemLike price2, int price2Count, ItemLike forSale, int saleCount, int maxTrades, int xp, float priceMult) {
        this(new ItemStack(Items.EMERALD, emeralds), new ItemStack(price2, price2Count), new ItemStack(forSale, saleCount), maxTrades, xp, priceMult);
    }

    @Override
    public MerchantOffer getOffer(Entity var1, Random var2) {
        return new MerchantOffer(price, price2, forSale, maxTrades, xp, priceMult);
    }
}
