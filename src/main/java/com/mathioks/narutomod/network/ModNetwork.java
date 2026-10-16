package com.mathioks.narutomod.network;

import com.mathioks.narutomod.NarutoAnimeMod;
import net.minecraft.resources.Identifier;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

public final class ModNetwork {
    public static final int VERSION = 1;
    public static SimpleChannel CHANNEL;

    public static void init() {
        if (CHANNEL != null) return;
        CHANNEL = ChannelBuilder
                .named(Identifier.fromNamespaceAndPath(NarutoAnimeMod.MODID, "main"))
                .networkProtocolVersion(VERSION)
                .acceptedVersions((status, version) -> true)
                .simpleChannel();

        CHANNEL.messageBuilder(CastJutsuPacket.class)
                .encoder(CastJutsuPacket::encode)
                .decoder(CastJutsuPacket::new)
                .consumerMainThread(CastJutsuPacket::handle)
                .add();

        NarutoAnimeMod.LOGGER.info("[Naruto Anime Mod] canal de rede iniciado (v{})", VERSION);
    }

    public static void toServer(Object msg) {
        CHANNEL.send(msg, net.minecraftforge.network.PacketDistributor.SERVER.noArg());
    }

    public static void toPlayer(Object msg, net.minecraft.server.level.ServerPlayer player) {
        CHANNEL.send(msg, net.minecraftforge.network.PacketDistributor.PLAYER.with(player));
    }

    private ModNetwork() {}
}
