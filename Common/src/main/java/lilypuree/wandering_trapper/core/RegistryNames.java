package lilypuree.wandering_trapper.core;

import net.minecraft.resources.ResourceLocation;

import static lilypuree.wandering_trapper.Constants.MOD_ID;

public class RegistryNames {
    public static ResourceLocation PELT_SCRAPING_LOG = create("pelt_scraping_log");
    public static ResourceLocation BEAVER_PELT = create("beaver_pelt");
    public static ResourceLocation MARTEN_PELT = create("marten_pelt");
    public static ResourceLocation MINK_PELT = create("mink_pelt");
    public static ResourceLocation FOX_PELT = create("fox_pelt");
    public static ResourceLocation SNOW_FOX_PELT = create("snow_fox_pelt");
    public static ResourceLocation POLARBEAR_PELT = create("polarbear_pelt");
    public static ResourceLocation POLARBEAR_RUG = create("polarbear_rug");
    public static ResourceLocation WANDERING_TRAPPER_SPAWN_EGG = create("wandering_trapper_spawn_egg");

    public static ResourceLocation WANDERING_TRAPPER = create("wandering_trapper");
    public static ResourceLocation TRAPPER_DOG = create("trapper_dog");
    public static ResourceLocation FURRIER = create("furrier");



    private static ResourceLocation create(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
