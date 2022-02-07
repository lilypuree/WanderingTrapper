package lilypuree.wandering_trapper.item;

import lilypuree.wandering_trapper.blocks.BearRugBlock;
import lilypuree.wandering_trapper.blocks.RugPart;
import lilypuree.wandering_trapper.core.RegistryObjects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static net.minecraft.world.level.block.Block.canSupportRigidBlock;

public class PeltItem extends Item {
    public PeltItem() {
        super(new Properties().tab(CreativeModeTab.TAB_MATERIALS));
    }


}
