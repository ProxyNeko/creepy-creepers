package dev.nertzhul.creepycreepers;

import dev.nertzhul.creepycreepers.setup.CcCreativeTabs;
import dev.nertzhul.creepycreepers.setup.CcEntities;
import dev.nertzhul.creepycreepers.setup.CcItems;
import dev.nertzhul.creepycreepers.setup.CcSoundEvents;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreepyCreepers {
    public static final String MOD_ID = "creepycreepers";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    
    public static void init() {
        CcItems.ITEMS.register();
        CcEntities.ENTITIES.register();
        CcSoundEvents.SOUNDS.register();
        CcCreativeTabs.CREATIVE_TABS.register();
    }
    
    public static ResourceLocation resource(String pPath) {
        return new ResourceLocation(MOD_ID, pPath);
    }
}
