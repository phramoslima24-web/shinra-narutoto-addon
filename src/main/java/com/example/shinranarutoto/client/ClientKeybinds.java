package com.example.shinranarutoto.client;

import com.example.shinranarutoto.ShinraNarutotoAddon;
import com.example.shinranarutoto.network.ModNetwork;
import com.example.shinranarutoto.network.ShinraTenseiPacket;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = ShinraNarutotoAddon.MOD_ID, value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public final class ClientKeybinds {
    private static final KeyMapping SHINRA_TENSEI = new KeyMapping(
            "key.shinranarutoto.shinra_tensei", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R,
            "key.categories.narutoto");

    private ClientKeybinds() {}

    public static void register() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientKeybinds::registerKeys);
    }

    private static void registerKeys(RegisterKeyMappingsEvent event) { event.register(SHINRA_TENSEI); }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        while (SHINRA_TENSEI.consumeClick()) {
            ModNetwork.CHANNEL.sendToServer(new ShinraTenseiPacket());
        }
    }
}
