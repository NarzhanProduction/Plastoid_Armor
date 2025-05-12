package net.narzhanp.plasticarmor.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.narzhanp.plasticarmor.PlastoidArmor;
import net.narzhanp.plasticarmor.item.ModItems;

import java.util.Random;
import java.util.UUID;

import static net.narzhanp.plasticarmor.PlastoidArmor.LOGGER;

@Mod.EventBusSubscriber(modid = PlastoidArmor.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TankArmorHandler {
    private static final UUID TANK_SPEED_UUID = UUID.fromString("11381b6c-b609-4ef5-bcef-192bffa93915");
    private static final double TANK_SPEED_BOOST = -0.2; // -20%

    // Log when the class is loaded
    static {
        LOGGER.info(PlastoidArmor.MOD_ID, ": TankArmorHandler initialized");
    }

    // Check for full Stormtrooper Set
    private static boolean isFullTankSet(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        return helmet.getItem() == ModItems.TANK_HELMET.get() &&
                chestplate.getItem() == ModItems.TANK_CHESTPLATE.get() &&
                leggings.getItem() == ModItems.TANK_LEGGINGS.get() &&
                boots.getItem() == ModItems.TANK_BOOTS.get();
    }



    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttribute == null) return;

            if (isFullTankSet(player)) {
                if (speedAttribute.getModifier(TANK_SPEED_UUID) == null) {
                    AttributeModifier modifier = new AttributeModifier(
                            TANK_SPEED_UUID,
                            "TankArmorSpeedSlower",
                            TANK_SPEED_BOOST,
                            AttributeModifier.Operation.MULTIPLY_TOTAL
                    );
                    speedAttribute.addTransientModifier(modifier);
                }
            } else {
                if (speedAttribute.getModifier(TANK_SPEED_UUID) != null) {
                    speedAttribute.removeModifier(TANK_SPEED_UUID);
                }
            }
        }
    }


    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player && isFullTankSet(player)) {
            if (player.isFullyFrozen() || player.getTicksFrozen() > 0) {
                player.setTicksFrozen(0);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player && isFullTankSet(player)) {
            DamageSource source = event.getSource();
            // Reducing warium bullets damage up to 40%
            if (source.getMsgId().equals("crusty_chunks:armor_bypass_damage")) {
                float originalDamage = event.getAmount();
                float reducedDamage = originalDamage * 0.6F; // Reducing 40%
                event.setAmount(reducedDamage);
            }
        }

        if (event.getSource().getEntity() instanceof Player player && isFullTankSet(player)) {
            LivingEntity target = event.getEntity();
            float dealtDamage = event.getAmount();
            float damage = dealtDamage * 1.22F; // adding 22%
            target.hurt(target.level().damageSources().generic(), damage);
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player && isFullTankSet(player)) {
            DamageSource source = event.getSource();

            if (source.is(DamageTypes.FALL)) {
                float reducedDamage = event.getAmount() * 1.2f; // Adding 20%
                event.setAmount(reducedDamage);
            }

            // Reduced Damage from fire
            if (source.is(DamageTypes.IN_FIRE) ||
                    source.is(DamageTypes.ON_FIRE)) {
                float reducedDamage = event.getAmount() * 0.6f; // Reducing 40%
                event.setAmount(reducedDamage);
            }

            // 50% chance to ignore arrows or shulkers
            if ((source.is(DamageTypes.ARROW)) || (source.is(DamageTypes.MOB_PROJECTILE))) {
                Random random = new Random();
                int random_chance = random.nextInt(100);
                if (random_chance < 50) {
                    event.setCanceled(true); // Cancelling arrows
                }
            }

        }
    }
}