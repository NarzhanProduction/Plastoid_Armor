package net.narzhanp.plasticarmor.item.client.plastoid;

import net.narzhanp.plasticarmor.item.custom.StormtrooperArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class StormtrooperArmorRenderer extends GeoArmorRenderer<StormtrooperArmorItem> {
    public StormtrooperArmorRenderer() {
        super(new StormtrooperArmorModel());
    }
}