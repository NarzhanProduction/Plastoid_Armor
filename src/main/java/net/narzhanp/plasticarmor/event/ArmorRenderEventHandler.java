package net.narzhanp.plasticarmor.event;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.narzhanp.plasticarmor.PlastoidArmor;
import net.narzhanp.plasticarmor.item.ModItems;

@Mod.EventBusSubscriber(modid = PlastoidArmor.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArmorRenderEventHandler {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onRenderPlayerPre(RenderPlayerEvent event) {
        if (event.getEntity() instanceof AbstractClientPlayer player) {

            PlayerModel<?> model = event.getRenderer().getModel();

            ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
            ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
            ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);

            boolean hideHead = isItemFromMod(helmet);
            boolean hideBody = isItemFromMod(chestplate);
            boolean hideArms = isItemFromMod(chestplate);
            boolean hideLegs = isItemFromMod(leggings);

            model.head.visible = !hideHead;
            model.hat.visible = !hideHead;
            model.body.visible = !hideBody;
            model.leftArm.visible = !hideArms;
            model.rightArm.visible = !hideArms;
            model.leftLeg.visible = !hideLegs;
            model.rightLeg.visible = !hideLegs;
        }
    }

    private static boolean isItemFromMod(ItemStack stack) {
        if (stack.isEmpty()) return false;

        return stack.getItem() == ModItems.STORMTROOPER_HELMET.get() ||
                stack.getItem() == ModItems.STORMTROOPER_CHESTPLATE.get() ||
                stack.getItem() == ModItems.STORMTROOPER_LEGGINGS.get() ||
                stack.getItem() == ModItems.STORMTROOPER_BOOTS.get() ||
                stack.getItem() == ModItems.MARKSMAN_HELMET.get() ||
                stack.getItem() == ModItems.MARKSMAN_CHESTPLATE.get() ||
                stack.getItem() == ModItems.MARKSMAN_LEGGINGS.get() ||
                stack.getItem() == ModItems.MARKSMAN_BOOTS.get() ||
                stack.getItem() == ModItems.TANK_HELMET.get() ||
                stack.getItem() == ModItems.TANK_CHESTPLATE.get() ||
                stack.getItem() == ModItems.TANK_LEGGINGS.get() ||
                stack.getItem() == ModItems.TANK_BOOTS.get();
    }
}