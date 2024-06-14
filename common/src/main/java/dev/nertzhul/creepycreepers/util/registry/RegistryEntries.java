package dev.nertzhul.creepycreepers.util.registry;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

public class RegistryEntries<T> {
    private final ObjectOpenHashSet<RegistryObject<T>> entries = new ObjectOpenHashSet<>();
    
    public <K extends T> RegistryObject<K> add(RegistryObject<K> pEntry) {
        this.entries.add((RegistryObject<T>) pEntry);
        return pEntry;
    }
    
    public ImmutableSet<RegistryObject<T>> getEntries() {
        return ImmutableSet.copyOf(this.entries);
    }
}
