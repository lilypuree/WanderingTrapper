package lilypuree.wandering_trapper.server;

import lilypuree.wandering_trapper.core.RegistryObjects;
import lilypuree.wandering_trapper.entity.TrapperDogEntity;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Optional;
import java.util.Random;

public class WanderingTrapperSpawner implements CustomSpawner {
    private static final int DEFAULT_TICK_DELAY = 1200; //1200
    public static final int DEFAULT_SPAWN_DELAY = 2400; //2400
    private static final int MIN_SPAWN_CHANCE = 25;
    private static final int MAX_SPAWN_CHANCE = 75; //75
    private static final int SPAWN_CHANCE_INCREASE = 25;
    private static final int SPAWN_ONE_IN_X_CHANCE = 10; //10
    private static final int NUMBER_OF_SPAWN_ATTEMPTS = 10;

    private final Random random = new Random();
    //    private final ServerWorld world;
//    private final ServerLevel world;
    private int tickDelay;
    private int spawnDelay;
    private int spawnChance;

    public WanderingTrapperSpawner() {
        this.tickDelay = DEFAULT_TICK_DELAY;
        this.spawnDelay = DEFAULT_SPAWN_DELAY;
        this.spawnChance = MIN_SPAWN_CHANCE;
    }

    @Override
    public int tick(ServerLevel level, boolean spawnEnemies, boolean spawnFriendlies) {
        if (!level.getGameRules().getBoolean(GameRules.RULE_DO_TRADER_SPAWNING)) {
            return 0;
        } else if (--this.tickDelay > 0) {
            return 0;
        } else {
            this.tickDelay = DEFAULT_TICK_DELAY;
            this.spawnDelay -= DEFAULT_TICK_DELAY;
            if (this.spawnDelay > 0) {
                return 0;
            } else {
                this.spawnDelay = DEFAULT_SPAWN_DELAY;
                if (!level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
                    return 0;
                } else {
                    int chance = this.spawnChance;
                    this.spawnChance = Mth.clamp(this.spawnChance + SPAWN_CHANCE_INCREASE, MIN_SPAWN_CHANCE, MAX_SPAWN_CHANCE);
                    if (this.random.nextInt(100) > chance) {
                        return 0;
                    } else if (this.spawnTrapper(level)) {
                        this.spawnChance = MIN_SPAWN_CHANCE;
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        }
    }

    private boolean spawnTrapper(ServerLevel world) {
        Player playerentity = world.getRandomPlayer();
        if (playerentity == null) {
            return true;
        } else if (this.random.nextInt(SPAWN_ONE_IN_X_CHANCE) != 0) {
            return false;
        } else {
            BlockPos playerPos = playerentity.blockPosition();
            int range = 48;
            PoiManager poiManager = world.getPoiManager();
            Optional<BlockPos> optional = poiManager.find(poi -> poi.is(PoiTypes.MEETING), (k) -> {
                return true;
            }, playerPos, range, PoiManager.Occupancy.ANY);
            BlockPos targetPos = optional.orElse(playerPos);
            BlockPos spawnPos = this.findSpawnPositionNear(world, targetPos, 48);
            if (spawnPos != null && hasEnoughSpace(world, spawnPos)) {
                if (world.getBiome(spawnPos).value().getBaseTemperature() > 0) {
                    return false;
                }

                WanderingTrapperEntity trapper = RegistryObjects.WANDERING_TRAPPER.get().spawn(world, (CompoundTag) null, (Component) null, (Player) null, spawnPos, MobSpawnType.EVENT, false, false);
                if (trapper != null) {
                    this.spawnDogs(trapper, 4);
                    trapper.setDespawnDelay(48000); //48000
                    trapper.setWanderTarget(targetPos);
                    trapper.restrictTo(targetPos, 16);
                    return true;
                }
            }

            return false;
        }
    }

    private void spawnDogs(WanderingTrapperEntity trapper, int radius) {
        BlockPos blockpos = this.findSpawnPositionNear(trapper.getLevel(), new BlockPos(trapper.blockPosition()), radius);
        if (blockpos != null) {
            TrapperDogEntity trapperDogEntity = RegistryObjects.TRAPPER_DOG.get().spawn((ServerLevel) trapper.getLevel(), (CompoundTag) null, (Component) null, (Player) null, blockpos, MobSpawnType.EVENT, false, false);
            if (trapperDogEntity != null) {
                trapperDogEntity.setLeashedTo(trapper, true);
                trapperDogEntity.setOwnerUUID(trapper.getUUID());
            }
        }
    }

    private BlockPos findSpawnPositionNear(LevelReader world, BlockPos pos, int radius) {
        BlockPos blockpos = null;

        for (int i = 0; i < NUMBER_OF_SPAWN_ATTEMPTS; ++i) {
            int j = pos.getX() + this.random.nextInt(radius * 2) - radius;
            int k = pos.getZ() + this.random.nextInt(radius * 2) - radius;
            int l = world.getHeight(Heightmap.Types.WORLD_SURFACE, j, k);
            BlockPos temp = new BlockPos(j, l, k);
            if (NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.NO_RESTRICTIONS, world, temp, RegistryObjects.WANDERING_TRAPPER.get())) {
                blockpos = temp;
                break;
            }
        }

        return blockpos;
    }

    private boolean hasEnoughSpace(BlockGetter world, BlockPos pos) {
        for (BlockPos temp : BlockPos.betweenClosed(pos, pos.offset(1, 2, 1))) {
            if (!world.getBlockState(temp).getCollisionShape(world, temp).isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
