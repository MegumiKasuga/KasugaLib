package kasuga.lib.registrations.common;

import kasuga.lib.KasugaLib;
import kasuga.lib.core.client.gui.commands.BaseArgument;
import kasuga.lib.core.client.gui.commands.BaseArgumentInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TemporaryRegs {
    public static void register(IEventBus bus){
        DeferredRegister<ArgumentTypeInfo<?, ?>> args =
                DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, KasugaLib.MOD_ID);
        args.register("simple_string",
                () -> ArgumentTypeInfos.registerByClass(BaseArgument.class, new BaseArgumentInfo()));
        args.register(bus);
    }
}
