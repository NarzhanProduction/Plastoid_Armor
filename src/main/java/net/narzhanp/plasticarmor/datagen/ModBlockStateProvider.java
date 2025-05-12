package net.narzhanp.plasticarmor.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.narzhanp.plasticarmor.PlastoidArmor;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PlastoidArmor.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}
