package net.narzhanp.plasticarmor.item.client.marksman;

import net.minecraft.resources.ResourceLocation;
import net.narzhanp.plasticarmor.PlastoidArmor;
import net.narzhanp.plasticarmor.item.custom.MarksmanArmorItem;
import software.bernie.geckolib.model.GeoModel;

public class MarksmanArmorModel extends GeoModel<MarksmanArmorItem> {
    @Override
    public ResourceLocation getModelResource(MarksmanArmorItem object) {
        return new ResourceLocation(PlastoidArmor.MOD_ID, "geo/marksman_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MarksmanArmorItem object) {
        return new ResourceLocation(PlastoidArmor.MOD_ID, "textures/armor/marksman_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MarksmanArmorItem object) {
        return null;
    }
}
