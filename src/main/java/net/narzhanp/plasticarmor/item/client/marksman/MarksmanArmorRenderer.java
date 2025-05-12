package net.narzhanp.plasticarmor.item.client.marksman;

import net.narzhanp.plasticarmor.item.custom.MarksmanArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class MarksmanArmorRenderer extends GeoArmorRenderer<MarksmanArmorItem> {
    public MarksmanArmorRenderer() {
        super(new MarksmanArmorModel());
    }
}