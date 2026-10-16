package com.mathioks.narutomod.network;

import com.mathioks.narutomod.jutsu.JutsuCaster;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;

/** Cliente -> Servidor: "quero conjurar o combo X". */
public record CastJutsuPacket(String combo, int ticksCarregados) {

    public CastJutsuPacket(FriendlyByteBuf buf) {
        this(buf.readUtf(16), buf.readVarInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(combo, 16);
        buf.writeVarInt(ticksCarregados);
    }

    public static void handle(CastJutsuPacket msg, CustomPayloadEvent.Context ctx) {
        ServerPlayer sender = ctx.getSender();
        if (sender == null) return;
        JutsuCaster.cast(sender, msg.combo(), msg.ticksCarregados());
    }
}
