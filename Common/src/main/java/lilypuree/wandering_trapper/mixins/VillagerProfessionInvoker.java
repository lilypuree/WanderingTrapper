package lilypuree.wandering_trapper.mixins;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;


@Mixin(VillagerProfession.class)
public interface VillagerProfessionInvoker {
    @Invoker("<init>")
    public static VillagerProfession invokeRegister(String $$0, PoiType $$1, ImmutableSet<Item> $$2, ImmutableSet<Block> $$3,  SoundEvent $$4) {
        throw new AssertionError();
    }
}
