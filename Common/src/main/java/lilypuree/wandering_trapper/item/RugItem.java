package lilypuree.wandering_trapper.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class RugItem extends BlockItem {

    public RugItem(Block $$0, Properties $$1) {
        super($$0, $$1);
    }

    @Override
    protected boolean mustSurvive() {
        return false;
    }

    @Override
    public String getDescriptionId() {
        return getOrCreateDescriptionId();
    }
}
