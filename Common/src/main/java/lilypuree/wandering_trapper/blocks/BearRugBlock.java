package lilypuree.wandering_trapper.blocks;

import lilypuree.wandering_trapper.core.RegistryObjects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.BiConsumer;

public class BearRugBlock extends HorizontalDirectionalBlock {
    public static final EnumProperty<RugPart> PART = EnumProperty.create("part", RugPart.class);
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    public BearRugBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(PART, RugPart.TAIL_LEFT));
    }


    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), UPDATE_NEIGHBORS | UPDATE_CLIENTS | UPDATE_SUPPRESS_DROPS);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighbourState, LevelAccessor level, BlockPos pos, BlockPos neighbourPos) {
        if (!level.isClientSide() && rugFull(level, state, pos) && !state.canSurvive(level, pos)) {
            level.scheduleTick(pos, state.getBlock(), 1);
            forOtherParts(state, pos, (otherPart, otherPos) -> {
                level.scheduleTick(otherPos, state.getBlock(), 1);
            });
//            dropResources(state, (ServerLevel) level, pos);
            popResource((ServerLevel) level, pos, new ItemStack(RegistryObjects.POLARBEAR_RUG));
        }
        return state;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction facing = ctx.getHorizontalDirection();
        RugPart rugPart = fittingRugPart(facing, ctx);
        if (rugPart != null) {
            return defaultBlockState().setValue(FACING, facing).setValue(PART, rugPart);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack itemStack) {
        if (!level.isClientSide) {
            forOtherParts(state, pos, (otherPart, otherPos) -> {
                        level.setBlock(otherPos, state.setValue(PART, otherPart), 3);
                        level.blockUpdated(pos, Blocks.AIR);
                        state.updateNeighbourShapes(level, pos, 3);
                    }
            );
        }
    }


    public RugPart fittingRugPart(Direction facing, BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos origin = context.getClickedPos();
        if (!canSupportRigidBlock(level, origin.below()) || !level.getBlockState(origin).isAir()) return null;
        BlockPos.MutableBlockPos mutable = origin.mutable();
        label1:
        for (RugPart part : RugPart.values()) {
            for (RugPart other : RugPart.values()) {
                if (part == other) continue;
                mutable.setWithOffset(origin, part.getOffsetTo(other, facing));
                if (!level.getBlockState(mutable).canBeReplaced(context) || !canSupportRigidBlock(level, mutable.move(0, -1, 0)))
                    continue label1;
            }
            return part;
        }
        return null;
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            preventCreativeDropFromCenterPart(level, pos, state, player);

            if (!player.isCreative()) {
                popResource(level, pos, new ItemStack(RegistryObjects.POLARBEAR_RUG));
//                dropResources(state, level, pos, null, player, player.getMainHandItem());
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void playerDestroy(Level $$0, Player $$1, BlockPos $$2, BlockState $$3, @Nullable BlockEntity $$4, ItemStack $$5) {
        super.playerDestroy($$0, $$1, $$2, Blocks.AIR.defaultBlockState(), $$4, $$5);
    }

    protected static void preventCreativeDropFromCenterPart(Level level, BlockPos pos, BlockState state, Player player) {
        forOtherParts(state, pos, (otherPart, otherPos) -> {
            BlockState otherState = level.getBlockState(otherPos);
            if (otherState.is(state.getBlock())) {
                level.setBlock(otherPos, Blocks.AIR.defaultBlockState(), UPDATE_SUPPRESS_DROPS | UPDATE_NONE | UPDATE_KNOWN_SHAPE | UPDATE_CLIENTS);
                level.levelEvent(player, 2001, otherPos, Block.getId(otherState));
            }
        });
    }

    protected static void forOtherParts(BlockState state, BlockPos pos, BiConsumer<RugPart, BlockPos> consumer) {
        RugPart part = state.getValue(PART);
        Direction facing = state.getValue(FACING);
        for (RugPart otherPart : RugPart.values()) {
            if (part == otherPart) continue;
            BlockPos otherPos = pos.offset(part.getOffsetTo(otherPart, facing));
            consumer.accept(otherPart, otherPos);
        }
    }

    protected static boolean rugFull(LevelAccessor level, BlockState state, BlockPos pos) {
        RugPart part = state.getValue(PART);
        Direction facing = state.getValue(FACING);
        for (RugPart otherPart : RugPart.values()) {
            if (part == otherPart) continue;
            BlockState otherState = level.getBlockState(pos.offset(part.getOffsetTo(otherPart, facing)));
            if (!otherState.is(state.getBlock())) return false;
        }
        return true;
    }

    ////

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos pos) {
        boolean isSupported = canSupportRigidBlock(levelReader, pos.below());
        boolean[] isConnected = new boolean[]{true};
        Direction facing = state.getValue(FACING);
        forOtherParts(state, pos, (otherPart, otherPos) -> {
            BlockState otherState = levelReader.getBlockState(otherPos);
            if (otherState.is(this) && otherState.getValue(FACING) == facing && otherState.getValue(PART) == otherPart) {

            } else {
                isConnected[0] = false;
            }
        });
        return isSupported && isConnected[0];
    }

    @Override
    public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
        return SHAPE;
    }

    @Override
    public void fallOn(Level $$0, BlockState $$1, BlockPos $$2, Entity $$3, float $$4) {
        super.fallOn($$0, $$1, $$2, $$3, $$4 * 0.5f);
    }


    @Override
    public PushReaction getPistonPushReaction(BlockState $$0) {
        return PushReaction.DESTROY;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }
}
