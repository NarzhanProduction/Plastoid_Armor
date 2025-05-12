package net.narzhanp.plasticarmor.item.client.tank;

import net.narzhanp.plasticarmor.item.custom.TankArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class TankArmorRenderer extends GeoArmorRenderer<TankArmorItem> {
    public TankArmorRenderer() {
        super(new TankArmorModel());
    }
}