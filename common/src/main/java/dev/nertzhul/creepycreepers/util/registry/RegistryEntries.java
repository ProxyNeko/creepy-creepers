package dev.nertzhul.creepycreepers.util.registry;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

public class RegistryEntries<T> {
    private final ObjectOpenHashSet<RegistryEntry<T>> entries = new ObjectOpenHashSet<>();
    
    public <K extends T> RegistryEntry<K> add(RegistryEntry<K> pEntry) {
        this.entries.add((RegistryEntry<T>) pEntry);
        return pEntry;
    }
    
    public ImmutableSet<RegistryEntry<T>> getEntries() {
        return ImmutableSet.copyOf(this.entries);
    }
}
