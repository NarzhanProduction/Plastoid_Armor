package net.narzhanp.plasticarmor.item.client;

import net.narzhanp.plasticarmor.PlasticArmor;
import net.narzhanp.plasticarmor.item.custom.PlastoidArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PlastoidArmorModel extends GeoModel<PlastoidArmorItem> {
    @Override
    public ResourceLocation getModelResource(PlastoidArmorItem geoAnimatable) {
        return new ResourceLocation(PlasticArmor.MOD_ID, "geo/plastoid_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PlastoidArmorItem geoAnimatable) {
        return new ResourceLocation(PlasticArmor.MOD_ID, "textures/armor/plastoid_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PlastoidArmorItem geoAnimatable) {
        return new ResourceLocation(PlasticArmor.MOD_ID, "animations/plastoid_armor.animation.json");
    }
}
