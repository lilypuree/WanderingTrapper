package lilypuree.wandering_trapper.server;

import lilypuree.wandering_trapper.entity.TrapperDogEntity;
import lilypuree.wandering_trapper.entity.WanderingTrapperEntity;
import lilypuree.wandering_trapper.setup.Registration;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

public class WanderingTrapperSpawner {

    private final Random random= new Random();
    private final ServerWorld world;
    private int field_221248_c;


    public WanderingTrapperSpawner(ServerWorld world) {
        this.world = world;
        this.field_221248_c = 12000; //12000
    }

    public void tick() {
        if (world.isDaytime() && world.getDimension() instanceof OverworldDimension) {
            if(--this.field_221248_c <=0){
                this.field_221248_c = 12000; //12000

                if (this.func_221245_b()) {
                    this.field_221248_c = 24000; //24000
                }
            }
        }
    }

    private boolean func_221245_b(){
        PlayerEntity playerentity = this.world.getRandomPlayer();
        if (playerentity == null) {
            return true;
        } else if (this.random.nextInt(10) != 0) {
            return false;
        } else {
            BlockPos blockpos = playerentity.getPosition();
            int i = 48;
            PointOfInterestManager pointofinterestmanager = this.world.getPointOfInterestManager();
            Optional<BlockPos> optional = pointofinterestmanager.func_219127_a(PointOfInterestType.MEETING.func_221045_c(), (p_221241_0_) -> {
                return true;
            }, blockpos, 48, PointOfInterestManager.Status.ANY);
            BlockPos blockpos1 = optional.orElse(blockpos);
            BlockPos blockpos2 = this.func_221244_a(blockpos1, 48);
            if(blockpos2 != null){
                if(this.world.getBiome(blockpos2).getTempCategory() != Biome.TempCategory.COLD){
                    return false;
                }

                WanderingTrapperEntity wanderingTrapperEntity = Registration.WANDERING_TRAPPER.get().spawn(this.world, (CompoundNBT) null, (ITextComponent)null, (PlayerEntity)null, blockpos2, SpawnReason.EVENT, false,false);
                if(wanderingTrapperEntity != null){
                    this.spawnDogs(wanderingTrapperEntity, 4);
                    this.world.getWorldInfo().setWanderingTraderId(wanderingTrapperEntity.getUniqueID());
                    wanderingTrapperEntity.setDespawnDelay(48000); // 48000
                    wanderingTrapperEntity.setWanderTarget(blockpos1);
                    wanderingTrapperEntity.setHomePosAndDistance(blockpos1, 16);
                    return true;
                }
            }

            return false;
        }
    }

    private void spawnDogs(WanderingTrapperEntity trapper, int p_221243_2_) {
        BlockPos blockpos = this.func_221244_a(new BlockPos(trapper), p_221243_2_);
        if (blockpos != null) {
            TrapperDogEntity trapperDogEntity = Registration.TRAPPER_DOG.get().spawn(this.world, (CompoundNBT)null, (ITextComponent)null, (PlayerEntity)null, blockpos, SpawnReason.EVENT, false, false);
            if (trapperDogEntity != null) {
                trapperDogEntity.setLeashHolder(trapper, true);
                trapperDogEntity.setOwnerId(trapper.getUniqueID());
            }
        }
    }


    @Nullable
    private BlockPos func_221244_a(BlockPos p_221244_1_, int p_221244_2_) {
        BlockPos blockpos = null;

        for(int i = 0; i < 10; ++i) {
            int j = p_221244_1_.getX() + this.random.nextInt(p_221244_2_ * 2) - p_221244_2_;
            int k = p_221244_1_.getZ() + this.random.nextInt(p_221244_2_ * 2) - p_221244_2_;
            int l = this.world.getHeight(Heightmap.Type.WORLD_SURFACE, j, k);
            BlockPos blockpos1 = new BlockPos(j, l, k);
            if (WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, this.world, blockpos1, Registration.WANDERING_TRAPPER.get())) {
                blockpos = blockpos1;
                break;
            }
        }

        return blockpos;
    }
}
