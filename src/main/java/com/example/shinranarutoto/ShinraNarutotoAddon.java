package com.example.shinranarutoto;

import com.example.shinranarutoto.client.ClientKeybinds;
import com.example.shinranarutoto.network.ModNetwork;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ShinraNarutotoAddon.MOD_ID)
public class ShinraNarutotoAddon {
    public static final String MOD_ID = "shinranarutoto";

    public ShinraNarutotoAddon() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModNetwork.register();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientKeybinds::register);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
