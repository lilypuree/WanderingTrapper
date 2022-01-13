package lilypuree.wandering_trapper.mixins;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(PoiType.class)
public interface PoiTypeInvoker {
    @Invoker("<init>")
    public static PoiType invokeInit(String name, Set<BlockState> matchingStates, int maxTickets, int validRange) {
        throw new AssertionError();
    }
}
