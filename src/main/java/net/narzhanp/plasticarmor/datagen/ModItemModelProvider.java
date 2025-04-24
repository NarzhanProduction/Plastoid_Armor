package net.narzhanp.plasticarmor.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.narzhanp.plasticarmor.PlasticArmor;
import net.narzhanp.plasticarmor.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PlasticArmor.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.PLASTOID);

        simpleItem(ModItems.PLASTOID_HELMET);
        simpleItem(ModItems.PLASTOID_CHESTPLATE);
        simpleItem(ModItems.PLASTOID_LEGGINGS);
        simpleItem(ModItems.PLASTOID_BOOTS);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PlasticArmor.MOD_ID, "item/" + item.getId().getPath()));
    }
}
