package lilypuree.wandering_trapper.mixins;

import com.google.common.collect.ImmutableList;
import lilypuree.wandering_trapper.server.WanderingTrapperSpawner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level {
    @Final
    @Shadow
    @Mutable
    private List<CustomSpawner> customSpawners;

    protected ServerLevelMixin(WritableLevelData $$0, ResourceKey<Level> $$1, DimensionType $$2, Supplier<ProfilerFiller> $$3, boolean $$4, boolean $$5, long $$6) {
        super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(MinecraftServer server, Executor executor, LevelStorageSource.LevelStorageAccess storage, ServerLevelData levelData, ResourceKey<Level> levelKey, DimensionType dimensionType, ChunkProgressListener $$6, ChunkGenerator chunkGenerator,
                        boolean isDebug, long seed, List<CustomSpawner> customSpawners, boolean tickTime, CallbackInfo ci) {
        if (levelKey.equals(Level.OVERWORLD)) {
            boolean trapperSpawnerEmpty = customSpawners.stream().filter(spawner -> spawner instanceof WanderingTrapperSpawner).findAny().isEmpty();
            if (trapperSpawnerEmpty) {
                setCustomSpawners(ImmutableList.<CustomSpawner>builder().addAll(customSpawners).add(new WanderingTrapperSpawner()).build());
            }
        }
    }

    @Accessor(value = "customSpawners")
    public abstract void setCustomSpawners(List<CustomSpawner> customSpawners);
}
