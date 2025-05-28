package net.narzhanp.plasticarmor.item.client.jetpack;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.narzhanp.plasticarmor.PlastoidArmor;
import net.narzhanp.plasticarmor.item.custom.JetpackItem;
import software.bernie.geckolib.model.GeoModel;

public class JetpackArmorModel extends GeoModel<JetpackItem> {
    @Override
    public ResourceLocation getModelResource(JetpackItem object) {
        return new ResourceLocation(PlastoidArmor.MOD_ID, "geo/jetpack.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(JetpackItem object) {
        return new ResourceLocation(PlastoidArmor.MOD_ID, "textures/armor/jetpack.png");
    }

    @Override
    public ResourceLocation getAnimationResource(JetpackItem object) {
        return null;
    }
}
