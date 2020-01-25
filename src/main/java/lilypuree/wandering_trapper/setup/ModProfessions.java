package lilypuree.wandering_trapper.setup;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.SoundEvent;
import net.minecraft.village.PointOfInterestType;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class ModProfessions {

    private static Constructor poiConstructor;
    private static Constructor professionConstructor;
    private static Method blockStatesInjector;

    static{
        try{
//            poiConstructor = PointOfInterestType.class.getDeclaredConstructor(String.class, Set.class, int.class, int.class);
//            poiConstructor.setAccessible(true);
//            professionConstructor = VillagerProfession.class.getDeclaredConstructor(String.class, PointOfInterestType.class, ImmutableSet.class, ImmutableSet.class, SoundEvent.class);
//            professionConstructor.setAccessible(true);
            blockStatesInjector = PointOfInterestType.class.getDeclaredMethod("func_221052_a", PointOfInterestType.class);
            blockStatesInjector.setAccessible(true);
        }catch(NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public static PointOfInterestType pointOfInterestType(String resourceLocation, Set<BlockState> blockStates, int maxFreeTickets, int p_i225712_4_) {
        try {
//            PointOfInterestType poi = (PointOfInterestType) poiConstructor.newInstance(resourceLocation, blockStates, maxFreeTickets, p_i225712_4_);
            PointOfInterestType poi = new PointOfInterestType(resourceLocation, blockStates,maxFreeTickets, null, p_i225712_4_);
            blockStatesInjector.invoke(null, poi);
            return poi;
        }catch (IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
        return null;
    }

    public static VillagerProfession villagerProfession(String resourceLocation, PointOfInterestType type, @Nullable SoundEvent soundEvent){
//        try {
//            VillagerProfession poi = (VillagerProfession) professionConstructor.newInstance(resourceLocation, type, ImmutableSet.of(), ImmutableSet.of(), soundEvent);
            VillagerProfession profession = new VillagerProfession(resourceLocation, type, ImmutableSet.of(), ImmutableSet.of());
            return profession;
//        }catch (){
////            e.printStackTrace();
//        }
//        return null;
    }
}
