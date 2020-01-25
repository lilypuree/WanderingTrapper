package lilypuree.wandering_trapper;


import lilypuree.wandering_trapper.setup.*;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.OptionalMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WanderingTrapper.MODID)
public class WanderingTrapper {
    public static final String MODID = "wandering_trapper";

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public static ModSetup setup = new ModSetup();
    public static WanderingTrapper instance;

    private static final Logger LOGGER = LogManager.getLogger();


    public WanderingTrapper() {
        instance = this;

        Registration.register();

        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLCommonSetupEvent e) -> setup.init(e));
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
    }


    private static String prefix(String path) {
        return WanderingTrapper.MODID + "." + path;
    }
}
