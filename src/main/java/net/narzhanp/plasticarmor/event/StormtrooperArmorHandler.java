package net.narzhanp.plasticarmor.event;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.Random;
import java.util.UUID;

import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.narzhanp.plasticarmor.PlastoidArmor;
import net.narzhanp.plasticarmor.item.ModItems;

import static net.narzhanp.plasticarmor.PlastoidArmor.LOGGER;

@Mod.EventBusSubscriber(modid = PlastoidArmor.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StormtrooperArmorHandler {
    private static final UUID STORMTROOPER_SPEED_UUID = UUID.fromString("f7c3b6e1-4b9e-4f7b-9c2a-7e9d2b4c1e2a");
    private static final double STORMTROOPER_SPEED_BOOST = 0.2; // +20%

    // Log when the class is loaded
    static {
        LOGGER.info(PlastoidArmor.MOD_ID, ": StormtrooperArmorHandler initialized");
    }

    // Check for full Stormtrooper Set
    private static boolean isFullStormtrooperSet(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        return helmet.getItem() == ModItems.STORMTROOPER_HELMET.get() &&
                chestplate.getItem() == ModItems.STORMTROOPER_CHESTPLATE.get() &&
                leggings.getItem() == ModItems.STORMTROOPER_LEGGINGS.get() &&
                boots.getItem() == ModItems.STORMTROOPER_BOOTS.get();
    }



    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttribute == null) return;

            if (isFullStormtrooperSet(player)) {
                if (speedAttribute.getModifier(STORMTROOPER_SPEED_UUID) == null) {
                    AttributeModifier modifier = new AttributeModifier(
                            STORMTROOPER_SPEED_UUID,
                            "StormtrooperArmorSpeedBoost",
                            STORMTROOPER_SPEED_BOOST,
                            AttributeModifier.Operation.MULTIPLY_TOTAL
                    );
                    speedAttribute.addTransientModifier(modifier);
                }
            } else {
                if (speedAttribute.getModifier(STORMTROOPER_SPEED_UUID) != null) {
                    speedAttribute.removeModifier(STORMTROOPER_SPEED_UUID);
                }
            }
        }
    }


    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player && isFullStormtrooperSet(player)) {
            if (player.isFullyFrozen() || player.getTicksFrozen() > 0) {
                player.setTicksFrozen(0);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player && isFullStormtrooperSet(player)) {
            DamageSource source = event.getSource();
            // Reducing warium bullets damage up to 35%
            if (source.getMsgId().equals("crusty_chunks:armor_bypass_damage")) {
                float originalDamage = event.getAmount();
                float reducedDamage = originalDamage * 0.65F; // Reducing 35%
                event.setAmount(reducedDamage);
            }
        }

        if (event.getEntity() instanceof Player target && isFullStormtrooperSet(target)) {
            float dealtDamage = event.getAmount();
            float damage = dealtDamage * 1.15F; // adding 15%
            target.hurt(target.level().damageSources().generic(), damage);
        } else if (event.getSource().getEntity() instanceof Player player && isFullStormtrooperSet(player)) {
            LivingEntity target = event.getEntity();
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

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player && isFullStormtrooperSet(player)) {
            DamageSource source = event.getSource();

            if (source.is(DamageTypes.FALL)) {
                float reducedDamage = event.getAmount() * 0.85f; // Reducing 15%
                event.setAmount(reducedDamage);
            }

            // Reduced Damage from fire
            if (source.is(DamageTypes.IN_FIRE) ||
                    source.is(DamageTypes.ON_FIRE)) {
                float reducedDamage = event.getAmount() * 0.7f; // Reducing 30%
                event.setAmount(reducedDamage);
            }

            // 25% chance to ignore arrows or shulkers
            if ((source.is(DamageTypes.ARROW)) || (source.is(DamageTypes.MOB_PROJECTILE))) {
                Random random = new Random();
                int random_chance = random.nextInt(100);
                if (random_chance < 25) {
                    event.setCanceled(true); // Cancelling arrows
                }
            }

        }
    }
}