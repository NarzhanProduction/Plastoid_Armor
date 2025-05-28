package net.narzhanp.plasticarmor.item.custom;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import mekanism.api.NBTConstants;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.api.providers.IGasProvider;
import mekanism.api.text.EnumColor;
import mekanism.client.render.RenderPropertiesProvider;
import mekanism.common.Mekanism;
import mekanism.common.MekanismLang;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.config.MekanismConfig;
import mekanism.common.config.value.CachedLongValue;
import mekanism.common.item.gear.BaseSpecialArmorMaterial;
import mekanism.common.item.gear.ItemGasArmor;
import mekanism.common.item.interfaces.IItemHUDProvider;
import mekanism.common.item.interfaces.IJetpackItem;
import mekanism.common.item.interfaces.IModeItem;
import mekanism.common.registries.MekanismGases;
import mekanism.common.util.ItemDataUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStack.TooltipPart;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.narzhanp.plasticarmor.PlastoidArmor;
import net.narzhanp.plasticarmor.item.client.jetpack.JetpackArmorRenderer;
import net.narzhanp.plasticarmor.item.client.marksman.MarksmanArmorRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class JetpackItem extends ItemGasArmor implements IItemHUDProvider, IModeItem, IJetpackItem, GeoItem, ICurioItem {

    private static final JetpackMaterial JETPACK_MATERIAL = new JetpackMaterial();

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public JetpackItem(Properties properties) {
        this(JETPACK_MATERIAL, properties);
    }

    public JetpackItem(ArmorMaterial material, Properties properties) {
        super(material, ArmorItem.Type.CHESTPLATE, properties.setNoRepair());
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new JetpackArmorRenderer();

                // This prepares our GeoArmorRenderer for the current render frame.
                // These parameters may be null however, so we don't do anything further with them
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }

    @Override
    protected CachedLongValue getMaxGas() {
        return MekanismConfig.gear.jetpackMaxGas;
    }

    @Override
    protected LongSupplier getFillRate() {
        return MekanismConfig.gear.jetpackFillRate;
    }

    @Override
    protected IGasProvider getGasType() {
        return MekanismGases.HYDROGEN;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(MekanismLang.MODE.translateColored(EnumColor.GRAY, getJetpackMode(stack).getTextComponent()));
    }

    @Override
    public boolean canUseJetpack(ItemStack stack) {
        return hasGas(stack);
    }

    @Override
    public JetpackMode getJetpackMode(ItemStack stack) {
        return JetpackMode.byIndexStatic(ItemDataUtils.getInt(stack, NBTConstants.MODE));
    }

    @Override
    public void useJetpackFuel(ItemStack stack) {
        useGas(stack, 1);
    }

    public void setMode(ItemStack stack, JetpackMode mode) {
        ItemDataUtils.setInt(stack, NBTConstants.MODE, mode.ordinal());
    }

    @Override
    public void addHUDStrings(List<Component> list, Player player, ItemStack stack, EquipmentSlot slotType) {
        if (slotType == getEquipmentSlot()) {
            JetpackItem jetpack = (JetpackItem) stack.getItem();
            list.add(MekanismLang.JETPACK_MODE.translateColored(EnumColor.DARK_GRAY, jetpack.getJetpackMode(stack)));
            GasStack stored = GasStack.EMPTY;
            Optional<IGasHandler> capability = stack.getCapability(Capabilities.GAS_HANDLER).resolve();
            if (capability.isPresent()) {
                IGasHandler gasHandlerItem = capability.get();
                if (gasHandlerItem.getTanks() > 0) {
                    stored = gasHandlerItem.getChemicalInTank(0);
                }
            }
            list.add(MekanismLang.JETPACK_STORED.translateColored(EnumColor.DARK_GRAY, EnumColor.ORANGE, stored.getAmount()));
        }
    }

    @Override
    public void changeMode(@NotNull Player player, @NotNull ItemStack stack, int shift, DisplayChange displayChange) {
        JetpackMode mode = getJetpackMode(stack);
        JetpackMode newMode = mode.adjust(shift);
        if (mode != newMode) {
            setMode(stack, newMode);
            displayChange.sendMessage(player, () -> MekanismLang.JETPACK_MODE_CHANGE.translate(newMode));
        }
    }

    @Override
    public boolean supportsSlotType(ItemStack stack, @NotNull EquipmentSlot slotType) {
        return slotType == getEquipmentSlot();
    }

    @Override
    public int getDefaultTooltipHideFlags(@NotNull ItemStack stack) {
        return super.getDefaultTooltipHideFlags(stack) | TooltipPart.MODIFIERS.getMask();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @NothingNullByDefault
    protected static class JetpackMaterial extends BaseSpecialArmorMaterial {

        @Override
        public String getName() {
            return PlastoidArmor.MOD_ID + ":jetpack";
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
    }
}