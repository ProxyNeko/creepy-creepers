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
package dev.tophatcat.creepycreepers.common.init;

import dev.tophatcat.creepycreepers.CreepyCreepers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CreepySoundRegistry {

    public static final DeferredRegister<SoundEvent> CREEPY_SOUNDS = DeferredRegister.create(
        ForgeRegistries.SOUND_EVENTS, CreepyCreepers.MOD_ID);

    public static final RegistryObject<SoundEvent> GHOSTLY_CREEPER = registerSound("ghostly_creeper_scream",
        "ghostly_creeper_scream");
    //TODO Make an Australian Creeper noise!
    public static final RegistryObject<SoundEvent> AUSTRALIAN_CREEPER = registerSound("australian_creeper",
        "australian_creeper");

    private static RegistryObject<SoundEvent> registerSound(String registryName, String soundPath) {
        return CREEPY_SOUNDS.register(registryName, () -> new SoundEvent(new ResourceLocation(CreepyCreepers.MOD_ID,
            soundPath)));
    }
}
