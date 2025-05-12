package net.narzhanp.plasticarmor;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.narzhanp.plasticarmor.item.ModCreativeModTabs;
import net.narzhanp.plasticarmor.item.ModItems;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(PlastoidArmor.MOD_ID)
public class PlastoidArmor {
    public static final String MOD_ID = "plastoid_armor";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PlastoidArmor(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
