package dev.nertzhul.creepycreepers.setup;

import dev.nertzhul.creepycreepers.CommonConstants;
import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.util.RegistryUtil;
import dev.nertzhul.creepycreepers.util.registry.RegistryEntry;
import dev.nertzhul.creepycreepers.util.registry.RegistryProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;

import java.util.Comparator;

public class CcCreativeTabs {
    public static final RegistryProvider<CreativeModeTab> CREATIVE_TABS = RegistryProvider.create(Registries.CREATIVE_MODE_TAB, CreepyCreepers.MOD_ID);
    
    public static final RegistryEntry<CreativeModeTab> CC_MAIN_TAB = CREATIVE_TABS.register("main_tab",
        () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .icon(() -> CcItems.GHOSTLY_CREEPER_SPAWN_EGG.get().getDefaultInstance())
            .title(CommonConstants.Translations.CREATIVE_TAB.asComponent())
            .displayItems((display, output) -> {
                CcItems.ITEMS.stream().map(RegistryEntry::get)
                    .sorted(Comparator.comparing(RegistryUtil::getKey))
                    .forEach(output::accept);
            })
            .build()
    );
}
