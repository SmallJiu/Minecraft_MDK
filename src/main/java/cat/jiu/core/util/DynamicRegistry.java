package cat.jiu.core.util;

import cat.jiu.core.api.handler.IJsonSerializable;
import cat.jiu.core.api.handler.INBTSerializable;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class DynamicRegistry<T extends IJsonSerializable & INBTSerializable> {
    protected final ConcurrentHashMap<ResourceLocation, Getter<T>> registry = new ConcurrentHashMap<>();
    protected final FailBack<ResourceLocation, JsonObject, T> jsonFailBack;
    protected final FailBack<ResourceLocation, CompoundTag, T> nbtFailBack;

    public DynamicRegistry(
            FailBack<ResourceLocation, JsonObject, T> jsonFailBack,
            FailBack<ResourceLocation, CompoundTag, T> nbtFailBack
    ) {
        this.jsonFailBack = jsonFailBack;
        this.nbtFailBack = nbtFailBack;
    }

    public boolean register(ResourceLocation id, Class<T> clazz) {
        return this.register(id, nbt->{
            try {
                T instance = clazz.getDeclaredConstructor().newInstance();
                instance.read(nbt);
                return instance;
            }catch (Exception e){
                e.printStackTrace();
                return this.nbtFailBack.apply(id, nbt);
            }
        }, json->{
            try {
                T instance = clazz.getDeclaredConstructor().newInstance();
                instance.read(json);
                return instance;
            }catch (Exception e){
                e.printStackTrace();
                return this.jsonFailBack.apply(id, json);
            }
        });
    }

    public boolean register(ResourceLocation id, Function<CompoundTag, T> nbtGetter, Function<JsonObject, T> jsonGetter) {
        if (!this.registry.containsKey(id)) {
            this.registry.put(id, new Getter<>(nbtGetter, jsonGetter));
            return true;
        }
        return false;
    }

    public boolean registered(ResourceLocation id) {
        return this.registry.containsKey(id);
    }

    public T get(ResourceLocation id, CompoundTag tag) {
        if (this.registry.containsKey(id)) {
            return this.registry.get(id).nbtGetter.apply(tag);
        }
        return this.nbtFailBack.apply(id, tag);
    }

    public T get(ResourceLocation id, JsonObject json) {
        if (this.registry.containsKey(id)) {
            return this.registry.get(id).jsonGetter.apply(json);
        }
        return this.jsonFailBack.apply(id, json);
    }

    public T get(ResourceLocation id) {
        return this.get(id, new JsonObject());
    }

    public Set<ResourceLocation> getIDs() {
        return Collections.unmodifiableSet(this.registry.keySet());
    }

    protected static class Getter<T extends IJsonSerializable & INBTSerializable> {
        protected final Function<CompoundTag, T> nbtGetter;
        protected final Function<JsonObject, T> jsonGetter;

        public Getter(Function<CompoundTag, T> nbtGetter, Function<JsonObject, T> jsonGetter) {
            this.nbtGetter = nbtGetter;
            this.jsonGetter = jsonGetter;
        }
    }

    public static interface FailBack<T1, T2, R> {
        R apply(T1 a, T2 b );
    }
}
