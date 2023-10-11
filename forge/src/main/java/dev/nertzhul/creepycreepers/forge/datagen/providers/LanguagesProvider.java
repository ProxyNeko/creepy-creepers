package dev.nertzhul.creepycreepers.forge.datagen.providers;

import dev.nertzhul.creepycreepers.CommonConstants;
import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.util.Translation;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.lang.reflect.Field;

public class LanguagesProvider extends LanguageProvider {
    private final Translation.Locale locale;
    
    public LanguagesProvider(PackOutput output, Translation.Locale locale) {
        super(output, CreepyCreepers.MOD_ID, locale.get());
        this.locale = locale;
    }
    
    @Override
    protected void addTranslations() {
        for (Field field : CommonConstants.Translations.class.getDeclaredFields()) {
            if (field.getType() == Translation.class) {
                try {
                    Translation entry = (Translation) field.get(Translation.class);
                    String key = this.locale.get();
                    
                    if (entry.localeMap().get(key) != null) {
                        add(entry.key(), entry.localeMap().get(key));
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
