package org.karn.koreanchat.mixin;

import org.karn.koreanchat.util.ExceptionStringUtil;
import org.karn.koreanchat.util.KoreanChatData;
import org.karn.koreanchat.util.KoreanChatManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import eu.pb4.playerdata.api.PlayerDataApi;
import net.minecraft.network.message.LastSeenMessageList;
import net.minecraft.network.message.MessageBody;
import net.minecraft.network.message.MessageChain;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow
    private MessageChain.Unpacker messageUnpacker;

    @Inject(method = "getSignedMessage", at = @At(value = "HEAD"), cancellable = true)
    private void getSignedMessage(ChatMessageC2SPacket packet, LastSeenMessageList lastSeenMessages, CallbackInfoReturnable<SignedMessage> cir) throws MessageChain.MessageChainException {
        ServerPlayNetworkHandler self = (ServerPlayNetworkHandler) (Object) this;
        KoreanChatData data = PlayerDataApi.getCustomDataFor(self.player, KoreanChatManager.KRC_DATA);
        String chatMessage = packet.chatMessage();

        if (data.krc) {
            chatMessage = ExceptionStringUtil.getString(chatMessage);
        }
        MessageBody messageBody = new MessageBody(chatMessage, packet.timestamp(), packet.salt(), lastSeenMessages);
        cir.setReturnValue(this.messageUnpacker.unpack(packet.signature(), messageBody));
    }
}
