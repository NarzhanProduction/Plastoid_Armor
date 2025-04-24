package net.narzhanp.plasticarmor.item.client;

import net.narzhanp.plasticarmor.item.custom.PlastoidArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class PlastoidArmorRenderer extends GeoArmorRenderer<PlastoidArmorItem> {
    public PlastoidArmorRenderer() {
        super(new PlastoidArmorModel());
    }
}
