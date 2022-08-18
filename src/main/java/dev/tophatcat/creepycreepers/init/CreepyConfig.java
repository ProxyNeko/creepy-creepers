/*
 * Creepy Creepers - https://github.com/tophatcats-mods/creepy-creepers
 * Copyright (C) 2016-2022 <KiriCattus>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation;
 * Specifically version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 * USA
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 */
package dev.tophatcat.creepycreepers.init;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CreepyConfig {

    public static final ServerConfig SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    public static boolean creepyCreepersNaturallySpawn = true;
    public static Set<Biome> allowlist = Sets.newHashSet();
    public static Set<Biome> disallowlist = Sets.newHashSet();
    public static int weight = 45;

    public static void setup() {
        creepyCreepersNaturallySpawn = SERVER.creepyCreepersSpawn.get();
        allowlist = SERVER.biomeAllowlist.get().stream().map(n
            -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(n))).collect(Collectors.toSet());
        disallowlist = SERVER.biomeDisallowlist.get().stream().map(n
            -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(n))).collect(Collectors.toSet());

    }

    public static class ServerConfig {
        public ForgeConfigSpec.BooleanValue creepyCreepersSpawn;
        public ForgeConfigSpec.IntValue creeperSpawnWeight;
        public ForgeConfigSpec.ConfigValue<List<? extends String>> biomeAllowlist;
        public ForgeConfigSpec.ConfigValue<List<? extends String>> biomeDisallowlist;

        ServerConfig(ForgeConfigSpec.Builder builder) {
            builder.push("Creepy Creepers Behaviour");
            creepyCreepersSpawn = builder
                .comment("If this creeper should spawn naturally in the world.")
                .define("enableNaturalSpawning", true);

            creeperSpawnWeight = builder.comment("If -1, the default spawn weight will be used.")
                .defineInRange("spawnWeight", 45, -1, Integer.MAX_VALUE);

            biomeAllowlist = builder.comment("If biomes are specified here, creepers will spawn in ONLY these biomes. "
                    + "(The blacklist is ignored while this is set!)")
                .defineList("whitelist", Lists.newArrayList(), o -> o instanceof String);

            biomeDisallowlist = builder
                .comment("If the whitelist is not used, use this list to specify the biomes "
                    + "that creepers should not spawn in.")
                .defineList("blacklist", Lists.newArrayList("minecraft:void"), o -> o instanceof String);
            builder.pop();
        }
    }
}

