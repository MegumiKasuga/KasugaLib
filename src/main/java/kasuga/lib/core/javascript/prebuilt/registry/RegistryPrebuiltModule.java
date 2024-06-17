package kasuga.lib.core.javascript.prebuilt.registry;

import kasuga.lib.core.javascript.JavascriptContext;
import kasuga.lib.core.javascript.ffi.ResourceLocationFFIHelper;
import kasuga.lib.core.javascript.registration.JavascriptPriorityRegistry;
import kasuga.lib.core.javascript.registration.RegistrationRegistry;
import net.minecraft.resources.ResourceLocation;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistryPrebuiltModule implements Closeable {
    private final RegistrationRegistry registry;
    private final JavascriptContext context;

    private final Map<ResourceLocation, RegistryProxy<?>> map = new HashMap<>();

    public RegistryPrebuiltModule(RegistrationRegistry registry, JavascriptContext context) {
        this.registry = registry;
        this.context = context;
    }

    public RegistryProxy<?> getRegistry(ResourceLocation location){
        JavascriptPriorityRegistry<?> registryEntry = registry.getRegistry(location);
        if(registryEntry == null)
            throw new IllegalStateException("Illegal State: registry "+location.toString()+" not loaded");
        return map.computeIfAbsent(location,(l)->new RegistryProxy<>(context,registryEntry));
    }

    @HostAccess.Export
    public RegistryProxy<?> getRegistry(Value value){
        return getRegistry(ResourceLocationFFIHelper.fromValue(value));
    }


    @Override
    public void close() throws IOException {
        for (RegistryProxy<?> proxy : map.values()) {
            proxy.close();
        }
        map.clear();
    }


}