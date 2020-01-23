package lilypuree.wandering_trapper;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lilypuree.wandering_trapper.setup.Registration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;

import javax.annotation.Nullable;
import java.util.Random;


public class TrapperTrades {

    public static final Int2ObjectMap<ITrade[]> trades = gatAsIntMap(ImmutableMap.of(1,new ITrade[]{
         new ItemsForEmeraldsTrade(Registration.BEAVER_PELT.get(), 5, 8, 6,10),
            new ItemsForEmeraldsTrade(Registration.FOX_PELT.get(), 7, 3, 3, 10),
            new ItemsForEmeraldsTrade(Registration.SNOW_FOX_PELT.get(), 9, 3, 2, 10),
            new ItemsForEmeraldsTrade(Registration.MARTEN_PELT.get(), 3, 5, 5, 10),
            new ItemsForEmeraldsTrade(Registration.MINK_PELT.get(), 13, 5, 2, 10),
            new ItemsForEmeraldsTrade(Registration.POLARBEAR_PELT.get(), 30, 1, 3, 10)
    }, 2, new ITrade[]{
            new ItemsForItemsTrade(Items.CLOCK, 1, Registration.BEAVER_PELT.get(), 4, 3, 10),
            new ItemsForItemsTrade(Items.CLOCK, 1, Registration.FOX_PELT.get(), 2, 3, 10),
            new ItemsForItemsTrade(Items.LANTERN, 1, Registration.BEAVER_PELT.get(), 4, 3, 10),
            new ItemsForItemsTrade(Items.LANTERN, 1, Registration.FOX_PELT.get(), 4, 3, 10),
            new ItemsForEmeraldsTrade(Items.RABBIT_FOOT, 1, 4, 5, 10),
    }));


    private static Int2ObjectMap<ITrade[]> gatAsIntMap(ImmutableMap<Integer, ITrade[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }

    static class ItemsForEmeraldsTrade implements VillagerTrades.ITrade {
        private final ItemStack item;
        private final int emeraldCount;
        private final int itemCount;
        private final int maxUses;
        private final int givenEXP;
        private final float priceMultiplier;


        public ItemsForEmeraldsTrade(Item item, int emeraldCount, int itemCountIn, int giveEXPIn) {
            this((ItemStack)(new ItemStack(item)), emeraldCount, itemCountIn, 12, giveEXPIn);
        }

        public ItemsForEmeraldsTrade(Item item, int emeraldCount, int itemCountIn, int maxUsesIn, int givenEXPIn) {
            this(new ItemStack(item), emeraldCount, itemCountIn, maxUsesIn, givenEXPIn);
        }

        public ItemsForEmeraldsTrade(ItemStack item, int emeraldCount, int itemCountIn, int maxUsesIn, int givenEXPIn) {
            this(item, emeraldCount, itemCountIn, maxUsesIn, givenEXPIn, 0.05F);
        }

        public ItemsForEmeraldsTrade(ItemStack itemIn, int emeraldCountIn, int itemCountIn, int maxUsesIn, int givenEXPIn, float priceMultiplierIn) {
            this.item = itemIn;
            this.emeraldCount = emeraldCountIn;
            this.itemCount = itemCountIn;
            this.maxUses = maxUsesIn;
            this.givenEXP = givenEXPIn;
            this.priceMultiplier = priceMultiplierIn;
        }

        public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCount), new ItemStack(this.item.getItem(), this.itemCount), this.maxUses, this.givenEXP, this.priceMultiplier);
        }
    }

    static class ItemsForItemsTrade implements VillagerTrades.ITrade{
        private final ItemStack buyingItem;
        private final int buyingItemCount;
        private final ItemStack sellingItem;
        private final int sellingItemCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;


        public ItemsForItemsTrade(IItemProvider buyingItemIn, int buyingItemCount, Item sellingItemIn, int sellingItemCount, int maxUses, int xpValue) {
            this.buyingItem = new ItemStack(buyingItemIn);
            this.buyingItemCount = buyingItemCount;
            this.sellingItem = new ItemStack(sellingItemIn);
            this.sellingItemCount = sellingItemCount;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = 0.05F;
        }

        @Nullable
        public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
            return new MerchantOffer( new ItemStack(this.buyingItem.getItem(), this.buyingItemCount),ItemStack.EMPTY, new ItemStack(this.sellingItem.getItem(), this.sellingItemCount), this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    static class ItemsForEmeraldsAndItemsTrade implements VillagerTrades.ITrade {
        private final ItemStack buyingItem;
        private final int buyingItemCount;
        private final int emeraldCount;
        private final ItemStack sellingItem;
        private final int sellingItemCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public ItemsForEmeraldsAndItemsTrade(IItemProvider p_i50533_1_, int p_i50533_2_, Item p_i50533_3_, int p_i50533_4_, int p_i50533_5_, int p_i50533_6_) {
            this(p_i50533_1_, p_i50533_2_, 1, p_i50533_3_, p_i50533_4_, p_i50533_5_, p_i50533_6_);
        }

        public ItemsForEmeraldsAndItemsTrade(IItemProvider p_i50534_1_, int p_i50534_2_, int p_i50534_3_, Item p_i50534_4_, int p_i50534_5_, int p_i50534_6_, int p_i50534_7_) {
            this.buyingItem = new ItemStack(p_i50534_1_);
            this.buyingItemCount = p_i50534_2_;
            this.emeraldCount = p_i50534_3_;
            this.sellingItem = new ItemStack(p_i50534_4_);
            this.sellingItemCount = p_i50534_5_;
            this.maxUses = p_i50534_6_;
            this.xpValue = p_i50534_7_;
            this.priceMultiplier = 0.05F;
        }

        @Nullable
        public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCount), new ItemStack(this.buyingItem.getItem(), this.buyingItemCount), new ItemStack(this.sellingItem.getItem(), this.sellingItemCount), this.maxUses, this.xpValue, this.priceMultiplier);
        }


    }


}
