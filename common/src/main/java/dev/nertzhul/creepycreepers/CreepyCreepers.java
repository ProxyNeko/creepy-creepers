package dev.nertzhul.creepycreepers;

import dev.nertzhul.creepycreepers.setup.CcCreativeTabs;
import dev.nertzhul.creepycreepers.setup.CcEntities;
import dev.nertzhul.creepycreepers.setup.CcItems;
import dev.nertzhul.creepycreepers.setup.CcSoundEvents;
import dev.nertzhul.creepycreepers.setup.CcTags;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreepyCreepers {
    public static final String MOD_ID = "creepycreepers";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    
    public static void init() {
        CcTags.register();
        CcItems.register();
        CcEntities.register();
        CcSoundEvents.register();
        CcCreativeTabs.register();
    }
    
    public static ResourceLocation resource(String pPath) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, pPath);
    }
}
