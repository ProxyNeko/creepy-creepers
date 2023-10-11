package dev.nertzhul.creepycreepers;

import dev.nertzhul.creepycreepers.setup.CcEntities;
import dev.nertzhul.creepycreepers.setup.CcItems;
import dev.nertzhul.creepycreepers.util.Translation;
import dev.nertzhul.creepycreepers.util.Translation.Locale;

public class CommonConstants {
    public static class Translations {
        public static final Translation CREATIVE_TAB = Translation.builder("creativetab", "title").addLocale(Locale.EN_US, "Creepy Creepers").build();
        
        //region Items
        public static final Translation GHOSTLY_CREEPER_SPAWN_EGG = Translation.builder(CcItems.GHOSTLY_CREEPER_SPAWN_EGG.get())
            .addLocale(Locale.EN_US, "Ghostly Creeper Spawn Egg")
            .addLocale(Locale.PT_BR, "Ovo Gerador de Creeper Fantasmagórico").build();
        public static final Translation SNOWY_CREEPER_SPAWN_EGG = Translation.builder(CcItems.SNOWY_CREEPER_SPAWN_EGG.get())
            .addLocale(Locale.EN_US, "Snowy Creeper Spawn Egg")
            .addLocale(Locale.PT_BR, "Ovo Gerador de Creeper de Neve").build();
        public static final Translation HALLOWEEN_CREEPER_SPAWN_EGG = Translation.builder(CcItems.HALLOWEEN_CREEPER_SPAWN_EGG.get())
            .addLocale(Locale.EN_US, "Halloween Creeper Spawn Egg")
            .addLocale(Locale.PT_BR, "Ovo Gerador de Creeper de Halloween").build();
        public static final Translation TUFF_CREEPER_SPAWN_EGG = Translation.builder(CcItems.TUFF_CREEPER_SPAWN_EGG.get())
            .addLocale(Locale.EN_US, "Tuff Creeper Spawn Egg")
            .addLocale(Locale.PT_BR, "Ovo Gerador de Creeper de Tufo").build();
        //endregion
        
        //region Entities
        public static final Translation GHOSTLY_CREEPER = Translation.builder(CcEntities.GHOSTLY_CREEPER.get())
            .addLocale(Locale.EN_US, "Ghostly Creeper")
            .addLocale(Locale.PT_BR, "Creeper Fantasmagórico").build();
        public static final Translation SNOWY_CREEPER = Translation.builder(CcEntities.SNOWY_CREEPER.get())
            .addLocale(Locale.EN_US, "Snowy Creeper")
            .addLocale(Locale.PT_BR, "Creeper de Neve").build();
        public static final Translation HALLOWEEN_CREEPER = Translation.builder(CcEntities.HALLOWEEN_CREEPER.get())
            .addLocale(Locale.EN_US, "Halloween Creeper")
            .addLocale(Locale.PT_BR, "Creeper de Halloween").build();
        public static final Translation TUFF_CREEPER = Translation.builder(CcEntities.TUFF_CREEPER.get())
            .addLocale(Locale.EN_US, "Tuff Creeper")
            .addLocale(Locale.PT_BR, "Creeper de Tufo").build();
        //endregion
        
        //region Subtitles
        public static final Translation GHOSTLY_CREEPER_SCREAM = Translation.builder("subtitle", "ghostly_creeper_scream")
            .addLocale(Locale.EN_US, "Ghostly Creeper Scream")
            .addLocale(Locale.PT_BR, "Grito de Creeper Fantasmagórico").build();
        //endregion
    }
}
