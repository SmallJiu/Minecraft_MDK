package cat.jiu.core.util;

import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class StaticRegistry<T extends Supplier<ResourceLocation>> {
    protected final ConcurrentHashMap<ResourceLocation, T> registry = new ConcurrentHashMap<>();
    protected final Function<ResourceLocation, T> failBack;

    public StaticRegistry(Function<ResourceLocation, T> failBack) {
        this.failBack = failBack;
    }

    public boolean register(T instance) {
        return this.register(instance.get(), instance);
    }
    public boolean register(ResourceLocation id, T instance) {
        if (!this.registry.containsKey(id)) {
            this.registry.put(id, instance);
            return true;
        }
        return false;
    }

    public boolean registered(ResourceLocation id) {
        return this.registry.containsKey(id);
    }

    public T get(ResourceLocation id) {
        if (this.registry.containsKey(id)) {
            return this.registry.get(id);
        }
        return this.failBack.apply(id);
    }

    public Set<ResourceLocation> getIDs() {
        return Collections.unmodifiableSet(this.registry.keySet());
    }
}
