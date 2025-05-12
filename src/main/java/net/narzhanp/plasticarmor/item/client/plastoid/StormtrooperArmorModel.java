package net.narzhanp.plasticarmor.item.client.plastoid;

import net.minecraft.resources.ResourceLocation;
import net.narzhanp.plasticarmor.PlastoidArmor;
import net.narzhanp.plasticarmor.item.custom.StormtrooperArmorItem;
import software.bernie.geckolib.model.GeoModel;

public class StormtrooperArmorModel extends GeoModel<StormtrooperArmorItem> {
    @Override
    public ResourceLocation getModelResource(StormtrooperArmorItem object) {
        return new ResourceLocation(PlastoidArmor.MOD_ID, "geo/stormtrooper_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StormtrooperArmorItem object) {
        return new ResourceLocation(PlastoidArmor.MOD_ID, "textures/armor/stormtrooper_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(StormtrooperArmorItem object) {
        return null;
    }
}
