package lilypuree.wandering_trapper.blocks;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.StringRepresentable;

public enum RugPart implements StringRepresentable {
    TAIL_RIGHT("tail_right", 0, false),
    TAIL_LEFT("tail_left", 0, true),
    HEAD_RIGHT("head_right", 2, false),
    HEAD_LEFT("head_left", 2, true),
    MID_RIGHT("mid_right", 1, false),
    MID_LEFT("mid_left", 1, true);

    private final String name;
    private final boolean left;
    private final int tail;

    private RugPart(String $$0, int tail, boolean left) {
        this.name = $$0;
        this.tail = tail;
        this.left = left;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public Vec3i getOffsetTo(RugPart relative, Direction facing) {
        Vec3i v1 = facing.getNormal().multiply(relative.tail - this.tail);
        Vec3i v2 = left == relative.left ? Vec3i.ZERO : ((left) ? facing.getClockWise().getNormal() : facing.getCounterClockWise().getNormal());
        return v1.offset(v2);
    }
}
