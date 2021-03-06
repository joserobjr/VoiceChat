package net.gliby.voicechat.common.networking.packets;

import io.netty.buffer.ByteBuf;
import net.gliby.voicechat.VoiceChat;
import net.gliby.voicechat.common.networking.MinecraftPacket;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MinecraftServerVoicePacket extends MinecraftPacket implements IMessageHandler<MinecraftServerVoicePacket, IMessage> {

    private byte[] data;
    private byte divider;


    public MinecraftServerVoicePacket() {
    }

    public MinecraftServerVoicePacket(byte divider, byte[] data) {
        this.divider = divider;
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.divider = buf.readByte();
        this.data = new byte[buf.readableBytes()];
        buf.readBytes(this.data);
    }

    @Override
    public IMessage onMessage(MinecraftServerVoicePacket packet, MessageContext ctx) {
        VoiceChat.getServerInstance().getVoiceServer().handleVoiceData(ctx.getServerHandler().playerEntity, packet.data, packet.divider, ctx.getServerHandler().playerEntity.getEntityId(), false);
        return null;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.divider);
        buf.writeBytes(this.data);
    }
}
