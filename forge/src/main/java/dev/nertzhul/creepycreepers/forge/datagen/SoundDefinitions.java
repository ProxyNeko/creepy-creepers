package dev.nertzhul.creepycreepers.forge.datagen;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.setup.CcSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class SoundDefinitions extends SoundDefinitionsProvider {
    public SoundDefinitions(PackOutput output, ExistingFileHelper helper) {
        super(output, CreepyCreepers.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        add(CcSoundEvents.GHOSTLY_CREEPER_SCREAM.get(), definition().with(
            sound(modLoc("entity/ghostly_creeper_scream"))
        ).subtitle("subtitle.creepycreepers.ghostly_creeper_scream"));
    }

    private ResourceLocation modLoc(String path) {
        return new ResourceLocation(CreepyCreepers.MOD_ID, path);
    }
}
