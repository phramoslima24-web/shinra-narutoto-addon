package com.example.shinranarutoto.network;

import com.example.shinranarutoto.ShinraNarutotoAddon;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class ModNetwork {
    private static final String VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ShinraNarutotoAddon.MOD_ID, "main"),
            () -> VERSION, VERSION::equals, VERSION::equals);

    private ModNetwork() {}

    public static void register() {
        CHANNEL.messageBuilder(ShinraTenseiPacket.class, 0)
                .encoder(ShinraTenseiPacket::encode)
                .decoder(ShinraTenseiPacket::decode)
                .consumerMainThread(ShinraTenseiPacket::handle)
                .add();
    }
}
