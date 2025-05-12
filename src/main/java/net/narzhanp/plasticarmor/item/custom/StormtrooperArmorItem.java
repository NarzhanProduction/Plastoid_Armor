package net.narzhanp.plasticarmor.item.custom;

import mekanism.common.capabilities.ItemCapabilityWrapper;
import mekanism.common.capabilities.radiation.item.RadiationShieldingHandler;
import mekanism.common.integration.gender.GenderCapabilityHelper;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.narzhanp.plasticarmor.item.client.plastoid.StormtrooperArmorRenderer;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

import static mekanism.common.item.gear.ItemHazmatSuitArmor.getShieldingByArmor;

public class StormtrooperArmorItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public StormtrooperArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new StormtrooperArmorRenderer();

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // Анимации, если нужны
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        ItemCapabilityWrapper wrapper = new ItemCapabilityWrapper(stack, RadiationShieldingHandler.create(item -> getShieldingByArmor(getType())));
        GenderCapabilityHelper.addGenderCapability(this, wrapper::add);
        return wrapper;
    }

    public ArmorItem.@NotNull Type getType() {
        return super.getType();
    }
}