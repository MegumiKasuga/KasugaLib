package kasuga.lib.core.events.both;

import kasuga.lib.KasugaLib;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class BothSetupEvent {
    public static void onFMLCommonSetup(FMLCommonSetupEvent event){
        KasugaLib.STACKS.JAVASCRIPT.setup();
    }
}
