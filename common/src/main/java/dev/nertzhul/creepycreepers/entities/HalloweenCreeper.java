package dev.nertzhul.creepycreepers.entities;

import dev.nertzhul.creepycreepers.util.CalendarUtil;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import org.jetbrains.annotations.Nullable;
import java.util.List;

public class HalloweenCreeper extends Creeper {
    private final List<Block> RARE_HEADS = List.of(Blocks.JACK_O_LANTERN, Blocks.WITHER_SKELETON_SKULL);
    private final List<Block> UNCOMMON_HEADS = List.of(Blocks.SKELETON_SKULL);
    
    public HalloweenCreeper(EntityType<? extends Creeper> entityType, Level level) {
        super(entityType, level);
    }
    
    @Override @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData);
        
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(
            level.getRandom().nextFloat() < 0.1F
                ? Util.getRandom(RARE_HEADS, level.getRandom())
                : level.getRandom().nextFloat() < 0.15F
                    ? Util.getRandom(UNCOMMON_HEADS, level.getRandom())
                    : Blocks.CARVED_PUMPKIN
        ));
        this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
        
        return spawnData;
    }
    
    @Override
    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType reason) {
        return CalendarUtil.isHalloween() && super.checkSpawnRules(level, reason);
    }
}
