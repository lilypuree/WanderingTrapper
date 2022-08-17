package lilypuree.wandering_trapper.entity;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lilypuree.wandering_trapper.core.RegistryObjects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;

import java.util.Random;


public class TrapperTrades {

    public static final Int2ObjectMap<ItemListing[]> trades = getAsIntMap(ImmutableMap.of(1, new ItemListing[]{
            new ItemsForEmeraldsTrade(RegistryObjects.BEAVER_PELT.get(), 18, 6, 5, 30),
            new ItemsForEmeraldsTrade(RegistryObjects.FOX_PELT.get(), 18, 3, 3, 30),
            new ItemsForEmeraldsTrade(RegistryObjects.SNOW_FOX_PELT.get(), 18, 2, 3, 30),
            new ItemsForEmeraldsTrade(RegistryObjects.MARTEN_PELT.get(), 24, 10, 5, 30),
            new ItemsForEmeraldsTrade(RegistryObjects.MINK_PELT.get(), 32, 6, 5, 30),
            new ItemsForEmeraldsTrade(RegistryObjects.POLARBEAR_PELT.get(), 60, 3, 3, 30)
    }, 2, new ItemListing[]{
            new ItemsForItemsTrade(Items.CLOCK, 1, RegistryObjects.BEAVER_PELT.get(), 4, 3, 30),
            new ItemsForItemsTrade(Items.CLOCK, 1, RegistryObjects.FOX_PELT.get(), 2, 3, 30),
            new ItemsForItemsTrade(Items.LANTERN, 1, RegistryObjects.BEAVER_PELT.get(), 4, 3, 30),
            new ItemsForItemsTrade(Items.LANTERN, 1, RegistryObjects.FOX_PELT.get(), 4, 3, 30),
            new ItemsForEmeraldsTrade(Items.RABBIT_FOOT, 1, 4, 5, 10),
    }));


    private static Int2ObjectMap<ItemListing[]> getAsIntMap(ImmutableMap<Integer, ItemListing[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }

    static class ItemsForEmeraldsTrade implements ItemListing {
        private final ItemStack item;
        private final int emeraldCount;
        private final int itemCount;
        private final int maxUses;
        private final int givenEXP;
        private final float priceMultiplier;


        public ItemsForEmeraldsTrade(Item item, int emeraldCount, int itemCountIn, int giveEXPIn) {
            this((ItemStack) (new ItemStack(item)), emeraldCount, itemCountIn, 12, giveEXPIn);
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

        @Override
        public MerchantOffer getOffer(Entity p_221182_1_, RandomSource p_221182_2_) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCount), new ItemStack(this.item.getItem(), this.itemCount), this.maxUses, this.givenEXP, this.priceMultiplier);
        }
    }

    static class ItemsForItemsTrade implements ItemListing {
        private final ItemStack buyingItem;
        private final int buyingItemCount;
        private final ItemStack sellingItem;
        private final int sellingItemCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;


        public ItemsForItemsTrade(ItemLike buyingItemIn, int buyingItemCount, Item sellingItemIn, int sellingItemCount, int maxUses, int xpValue) {
            this.buyingItem = new ItemStack(buyingItemIn);
            this.buyingItemCount = buyingItemCount;
            this.sellingItem = new ItemStack(sellingItemIn);
            this.sellingItemCount = sellingItemCount;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = 0.05F;
        }

        @Override
        public MerchantOffer getOffer(Entity p_221182_1_, RandomSource p_221182_2_) {
            return new MerchantOffer(new ItemStack(this.buyingItem.getItem(), this.buyingItemCount), ItemStack.EMPTY, new ItemStack(this.sellingItem.getItem(), this.sellingItemCount), this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    static class ItemsForEmeraldsAndItemsTrade implements ItemListing {
        private final ItemStack buyingItem;
        private final int buyingItemCount;
        private final int emeraldCount;
        private final ItemStack sellingItem;
        private final int sellingItemCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public ItemsForEmeraldsAndItemsTrade(ItemLike p_i50533_1_, int p_i50533_2_, Item p_i50533_3_, int p_i50533_4_, int p_i50533_5_, int p_i50533_6_) {
            this(p_i50533_1_, p_i50533_2_, 1, p_i50533_3_, p_i50533_4_, p_i50533_5_, p_i50533_6_);
        }

        public ItemsForEmeraldsAndItemsTrade(ItemLike p_i50534_1_, int p_i50534_2_, int p_i50534_3_, Item p_i50534_4_, int p_i50534_5_, int p_i50534_6_, int p_i50534_7_) {
            this.buyingItem = new ItemStack(p_i50534_1_);
            this.buyingItemCount = p_i50534_2_;
            this.emeraldCount = p_i50534_3_;
            this.sellingItem = new ItemStack(p_i50534_4_);
            this.sellingItemCount = p_i50534_5_;
            this.maxUses = p_i50534_6_;
            this.xpValue = p_i50534_7_;
            this.priceMultiplier = 0.05F;
        }

        @Override
        public MerchantOffer getOffer(Entity p_221182_1_, RandomSource p_221182_2_) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCount), new ItemStack(this.buyingItem.getItem(), this.buyingItemCount), new ItemStack(this.sellingItem.getItem(), this.sellingItemCount), this.maxUses, this.xpValue, this.priceMultiplier);
        }


    }


}
