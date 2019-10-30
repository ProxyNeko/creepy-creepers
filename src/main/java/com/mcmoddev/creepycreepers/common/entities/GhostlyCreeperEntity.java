package com.mcmoddev.creepycreepers.common.entities;

import com.mcmoddev.creepycreepers.common.init.RegistrySound;
import com.mcmoddev.creepycreepers.common.misc.CreeperSwellGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Collection;

public class GhostlyCreeperEntity extends MonsterEntity {

    private static final DataParameter<Integer> STATE = EntityDataManager.createKey(CreeperEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(CreeperEntity.class, DataSerializers.BOOLEAN);
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime = 30;
    private int explosionRadius = 3;

    public GhostlyCreeperEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
    }

    protected void registerGoals() {
        goalSelector.addGoal(1, new SwimGoal(this));
        goalSelector.addGoal(2, new CreeperSwellGoal(this));
        goalSelector.addGoal(3, new AvoidEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0D, 1.2D));
        goalSelector.addGoal(3, new AvoidEntityGoal<>(this, CatEntity.class, 6.0F, 1.0D, 1.2D));
        goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
        goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
        goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        goalSelector.addGoal(6, new LookRandomlyGoal(this));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    protected void registerData() {
        super.registerData();
        dataManager.register(STATE, -1);
        dataManager.register(IGNITED, false);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putShort("Fuse", (short) fuseTime);
        compound.putByte("ExplosionRadius", (byte) explosionRadius);
        compound.putBoolean("ignited", hasIgnited());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("Fuse", 99)) {
            fuseTime = compound.getShort("Fuse");
        }

        if (compound.contains("ExplosionRadius", 99)) {
            explosionRadius = compound.getByte("ExplosionRadius");
        }

        if (compound.getBoolean("ignited")) {
            ignite();
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        if (isAlive()) {
            lastActiveTime = timeSinceIgnited;
            if (hasIgnited()) {
                setCreeperState(1);
            }

            int i = getCreeperState();
            if (i > 0 && timeSinceIgnited == 0) {
                playSound(RegistrySound.CREEPERSCREAM_SOUND, 1.0F, 1.0F);
            }

            timeSinceIgnited += i;
            if (timeSinceIgnited < 0) {
                timeSinceIgnited = 0;
            }

            if (timeSinceIgnited >= fuseTime) {
                timeSinceIgnited = fuseTime;
                explode();
            }
        }
        super.tick();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CREEPER_DEATH;
    }

    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        return true;
    }

    /**
     * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
     */
    @OnlyIn(Dist.CLIENT)
    public float getCreeperFlashIntensity(float partialTicks) {
        return MathHelper.lerp(partialTicks, (float) lastActiveTime, (float) timeSinceIgnited) / (float) (fuseTime - 2);
    }

    /**
     * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
     */
    public int getCreeperState() {
        return dataManager.get(STATE);
    }

    /**
     * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
     */
    public void setCreeperState(int state) {
        dataManager.set(STATE, state);
    }


    protected boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
            world.playSound(player, posX, posY, posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, getSoundCategory(), 1.0F, rand.nextFloat() * 0.4F + 0.8F);
            player.swingArm(hand);
            if (!world.isRemote) {
                ignite();
                itemstack.damageItem(1, player, (p_213625_1_) -> p_213625_1_.sendBreakAnimation(hand));
                return true;
            }
        }

        return super.processInteract(player, hand);
    }

    /**
     * Creates an explosion as determined by this creeper's power and explosion radius.
     */
    private void explode() {
        if (!world.isRemote) {
            Explosion.Mode explosion$mode = ForgeEventFactory.getMobGriefingEvent(world, this) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
            dead = true;
            world.createExplosion(this, posX, posY, posZ, (float) explosionRadius, explosion$mode);
            remove();
            spawnLingeringCloud();
        }

    }

    private void spawnLingeringCloud() {
        Collection<EffectInstance> collection = getActivePotionEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(world, posX, posY, posZ);
            areaeffectcloudentity.setRadius(2.5F);
            areaeffectcloudentity.setRadiusOnUse(-0.5F);
            areaeffectcloudentity.setWaitTime(10);
            areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
            areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float) areaeffectcloudentity.getDuration());

            for (EffectInstance effectinstance : collection) {
                areaeffectcloudentity.addEffect(new EffectInstance(effectinstance));
            }

            world.addEntity(areaeffectcloudentity);
        }

    }

    public boolean hasIgnited() {
        return dataManager.get(IGNITED);
    }

    private void ignite() {
        dataManager.set(IGNITED, true);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
    }
}
