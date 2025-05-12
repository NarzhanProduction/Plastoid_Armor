package net.narzhanp.plasticarmor.item.client.tank;

import net.minecraft.resources.ResourceLocation;
import net.narzhanp.plasticarmor.PlastoidArmor;
import net.narzhanp.plasticarmor.item.custom.TankArmorItem;
import software.bernie.geckolib.model.GeoModel;

public class TankArmorModel extends GeoModel<TankArmorItem> {
    @Override
    public ResourceLocation getModelResource(TankArmorItem object) {
        return new ResourceLocation(PlastoidArmor.MOD_ID, "geo/tank_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TankArmorItem object) {
        return new ResourceLocation(PlastoidArmor.MOD_ID, "textures/armor/tank_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TankArmorItem object) {
        return null;
    }
}
