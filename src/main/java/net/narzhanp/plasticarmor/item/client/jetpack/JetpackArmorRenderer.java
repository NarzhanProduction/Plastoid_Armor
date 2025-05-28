package net.narzhanp.plasticarmor.item.client.jetpack;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.narzhanp.plasticarmor.item.custom.JetpackItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class JetpackArmorRenderer extends GeoArmorRenderer<JetpackItem> {
    public JetpackArmorRenderer() {
        super(new JetpackArmorModel());
    }
}