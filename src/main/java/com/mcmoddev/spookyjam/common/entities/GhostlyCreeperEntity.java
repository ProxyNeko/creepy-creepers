package com.mcmoddev.spookyjam.common.entities;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.LightningBoltEntity;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collection;

public class GhostlyCreeperEntity extends MonsterEntity {

    private static final DataParameter<Integer> STATE = EntityDataManager.createKey(CreeperEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(CreeperEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(CreeperEntity.class, DataSerializers.BOOLEAN);
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime = 30;
    private int explosionRadius = 3;
    private int droppedSkulls;

    public GhostlyCreeperEntity(EntityType<? extends MonsterEntity> type, World world) {
        super(type, world);
    }

    protected void registerGoals() {
        goalSelector.addGoal(1, new SwimGoal(this));
        //goalSelector.addGoal(2, new CreeperSwellGoal(this));
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

    public int getMaxFallHeight() {
        return getAttackTarget() == null ? 3 : 3 + (int) (getHealth() - 1.0F);
    }

    public void fall(float distance, float damageMultiplier) {
        super.fall(distance, damageMultiplier);
        timeSinceIgnited = (int) ((float) timeSinceIgnited + distance * 1.5F);
        if (timeSinceIgnited > fuseTime - 5) {
            timeSinceIgnited = fuseTime - 5;
        }
    }

    protected void registerData() {
        super.registerData();
        dataManager.register(STATE, -1);
        dataManager.register(POWERED, false);
        dataManager.register(IGNITED, false);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (dataManager.get(POWERED)) {
            compound.putBoolean("powered", true);
        }

        compound.putShort("Fuse", (short) fuseTime);
        compound.putByte("ExplosionRadius", (byte) explosionRadius);
        compound.putBoolean("ignited", hasIgnited());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        dataManager.set(POWERED, compound.getBoolean("powered"));
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
                playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
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
        Entity entity = source.getTrueSource();
        if (entity != this && entity instanceof CreeperEntity) {
            CreeperEntity creeperentity = (CreeperEntity) entity;
            if (creeperentity.ableToCauseSkullDrop()) {
                creeperentity.incrementDroppedSkulls();
                entityDropItem(Items.CREEPER_HEAD);
            }
        }

    }

    public boolean attackEntityAsMob(Entity entityIn) {
        return true;
    }

    /**
     * Returns true if the creeper is powered by a lightning bolt.
     */
    public boolean getPowered() {
        return dataManager.get(POWERED);
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

    /**
     * Called when a lightning bolt hits the entity.
     */
    public void onStruckByLightning(LightningBoltEntity lightningBolt) {
        super.onStruckByLightning(lightningBolt);
        dataManager.set(POWERED, true);
    }

    protected boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
            world.playSound(player, posX, posY, posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, getSoundCategory(), 1.0F, rand.nextFloat() * 0.4F + 0.8F);
            player.swingArm(hand);
            if (!world.isRemote) {
                ignite();
                itemstack.damageItem(1, player, (p_213625_1_) -> {
                    p_213625_1_.sendBreakAnimation(hand);
                });
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
            Explosion.Mode explosion$mode = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(world, this) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
            float f = getPowered() ? 2.0F : 1.0F;
            dead = true;
            world.createExplosion(this, posX, posY, posZ, (float) explosionRadius * f, explosion$mode);
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

    public void ignite() {
        dataManager.set(IGNITED, true);
    }

    /**
     * Returns true if an entity is able to drop its skull due to being blown up by this creeper.
     * <p>
     * Does not test if this creeper is charged; the caller must do that. However, does test the doMobLoot gamerule.
     */
    public boolean ableToCauseSkullDrop() {
        return getPowered() && droppedSkulls < 1;
    }

    public void incrementDroppedSkulls() {
        ++droppedSkulls;
    }
}
