package lilypuree.wandering_trapper.setup;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistryModifiable;

@Mod.EventBusSubscriber
public class RecipeHandler {


    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<? extends IRecipe> event) {
        ResourceLocation recipe = new ResourceLocation("musketmod:barrel");
        ResourceLocation recipe2 = new ResourceLocation("musketmod:stock");
        ResourceLocation recipe3 = new ResourceLocation("musketmod:musket");
        IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) event.getRegistry();
        modRegistry.remove(recipe);
        modRegistry.remove(recipe2);
        modRegistry.remove(recipe3);
    }
}