package net.narzhanp.plasticarmor.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.narzhanp.plasticarmor.PlastoidArmor;
import net.narzhanp.plasticarmor.item.ModItems;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static net.narzhanp.plasticarmor.PlastoidArmor.LOGGER;

@Mod.EventBusSubscriber(modid = PlastoidArmor.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MarksmanArmorHandler {
    private static final UUID MARKSMAN_SPEED_UUID = UUID.fromString("a1b2c3d4-e5f6-7890-1234-56789abcdef0");
    private static final double MARKSMAN_SPEED_BOOST = 0.2; // +20%
//    private static final UUID MARKSMAN_FOLLOW_RANGE_UUID = UUID.fromString("c3d4e5f6-7890-1234-5678-9abcdef01234");
//    private static final double MARKSMAN_FOLLOW_RANGE = -0.5;

    // Log when the class is loaded
    static {
        LOGGER.info(PlastoidArmor.MOD_ID, ": MarksmanArmorHandler initialized");
    }

    // Check for full Marksman Set
    private static boolean isFullMarksmanSet(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        return helmet.getItem() == ModItems.MARKSMAN_HELMET.get() &&
                chestplate.getItem() == ModItems.MARKSMAN_CHESTPLATE.get() &&
                leggings.getItem() == ModItems.MARKSMAN_LEGGINGS.get() &&
                boots.getItem() == ModItems.MARKSMAN_BOOTS.get();
    }


    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttribute == null) return;

            if (isFullMarksmanSet(player)) {
                if (speedAttribute.getModifier(MARKSMAN_SPEED_UUID) == null) {
                    AttributeModifier modifier = new AttributeModifier(
                            MARKSMAN_SPEED_UUID,
                            "MarksmanArmorSpeedBoost",
                            MARKSMAN_SPEED_BOOST,
                            AttributeModifier.Operation.MULTIPLY_TOTAL
                    );
                    speedAttribute.addTransientModifier(modifier);
                }
            } else {
                if (speedAttribute.getModifier(MARKSMAN_SPEED_UUID) != null) {
                    speedAttribute.removeModifier(MARKSMAN_SPEED_UUID);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (isFullMarksmanSet(player)) {
                if (player.isFullyFrozen() || player.getTicksFrozen() > 0) {
                    player.setTicksFrozen(0);
                }
                // Searching for mobs in radius
                player.level().getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(50.0), e -> true)
                        .forEach(monster -> {
                            Objects.requireNonNull(monster.getAttribute(Attributes.FOLLOW_RANGE)).setBaseValue(10.0);
                        });
            } else {
                // Restoring standard radius
                player.level().getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(50.0), e -> true)
                        .forEach(monster -> {
                            Objects.requireNonNull(monster.getAttribute(Attributes.FOLLOW_RANGE)).setBaseValue(32.0);
                        });
            }
        }
        if (event.getEntity() instanceof Player player && !player.level().isClientSide && player.tickCount % 2 == 0) {
            boolean isSneaking = player.isShiftKeyDown();

            if (isFullMarksmanSet(player) && isSneaking) {
                Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.15);
            } else {
                Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.1);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player && isFullMarksmanSet(player)) {
            LivingEntity target = event.getEntity();
            float dealtDamage = event.getAmount();
            float damage = dealtDamage * 0.9F; // reducing 10%
            target.hurt(target.level().damageSources().generic(), damage);
        } else if (event.getEntity() instanceof Player target && isFullMarksmanSet(target)) {
            DamageSource source = event.getSource();
            if (source.getEntity() instanceof Player player) {
                boolean isFalling = player.fallDistance > 0.0f &&
                        !player.onGround() &&
                        player.getDeltaMovement().y() < 0 &&
                        !player.onClimbable() &&
                        !player.isInWater() &&
                        !player.isOnRails() &&
                        !player.getAbilities().flying;
                if (isFalling) {
                    float dealtDamage = event.getAmount();
                    float damage = dealtDamage * 1.20F; // adding 20%
                    target.hurt(target.level().damageSources().generic(), damage);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player && isFullMarksmanSet(player)) {
            DamageSource source = event.getSource();

            // Reduced Damage from fire
            if (source.is(DamageTypes.IN_FIRE) ||
                    source.is(DamageTypes.ON_FIRE)) {
                float reducedDamage = event.getAmount() * 0.85f; // Reducing 15%
                event.setAmount(reducedDamage);
            }
        }
    }
}