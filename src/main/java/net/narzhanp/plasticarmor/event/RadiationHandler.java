package net.narzhanp.plasticarmor.event;

import mekanism.common.lib.radiation.RadiationManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.narzhanp.plasticarmor.PlastoidArmor;
import net.narzhanp.plasticarmor.item.custom.MarksmanArmorItem;


@Mod.EventBusSubscriber(modid = PlastoidArmor.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RadiationHandler {
    private static final String NBT_PROTECTION_ACTIVE = PlastoidArmor.MOD_ID + ":MarksmanRadiationProtectionActive";
    private static final String NBT_LAST_ACTIVATED = PlastoidArmor.MOD_ID + ":MarksmanLastActivatedTick";
    private static final long PROTECTION_DURATION = 5 * 60 * 20;
    private static final long COOLDOWN_DURATION = 5 * 60 * 20;
    private static final int TICK_CHECK_INTERVAL = 20;

    private static boolean isFullMarksmanArmorSetEquipped(Player player) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                ItemStack stack = player.getItemBySlot(slot);
                if (!(stack.getItem() instanceof MarksmanArmorItem)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void activateProtection(ServerPlayer player, long currentTick) {
        CompoundTag data = player.getPersistentData();
        data.putBoolean(NBT_PROTECTION_ACTIVE, true);
        data.putLong(NBT_LAST_ACTIVATED, currentTick);

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                ItemStack stack = player.getItemBySlot(slot);
                if (stack.getItem() instanceof MarksmanArmorItem) {
                    stack.getOrCreateTag().putBoolean("RadiationProtectionActive", true);
                }
            }
        }
    }

    private static void deactivateProtection(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        data.putBoolean(NBT_PROTECTION_ACTIVE, false);

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                ItemStack stack = player.getItemBySlot(slot);
                if (stack.getItem() instanceof MarksmanArmorItem) {
                    stack.getOrCreateTag().remove("RadiationProtectionActive");
                }
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (event.getServer().getTickCount() % TICK_CHECK_INTERVAL != 0) return;

        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            CompoundTag data = player.getPersistentData();
            long currentTick = player.level().getGameTime();

            if (data.getBoolean(NBT_PROTECTION_ACTIVE)) {
                long lastActivated = data.getLong(NBT_LAST_ACTIVATED);
                if (currentTick - lastActivated >= PROTECTION_DURATION) {
                    deactivateProtection(player);
                }
            }

            if (!data.getBoolean(NBT_PROTECTION_ACTIVE) && isFullMarksmanArmorSetEquipped(player)) {
                boolean isUnderRadiation = false;
                try {
                    double radiationLevel = RadiationManager.get().getRadiationLevel(player);
                    isUnderRadiation = radiationLevel > 0;
                } catch (Exception e) {
                    System.err.println("Error Checking Error for Mekanism Radiation " + player.getName().getString() + ": " + e.getMessage());
                }

                if (isUnderRadiation) {
                    if (!data.contains(NBT_LAST_ACTIVATED) ||
                            (currentTick - data.getLong(NBT_LAST_ACTIVATED) >= COOLDOWN_DURATION + PROTECTION_DURATION)) {
                        activateProtection(player, currentTick);
                    }
                }
            }
        }
    }
}