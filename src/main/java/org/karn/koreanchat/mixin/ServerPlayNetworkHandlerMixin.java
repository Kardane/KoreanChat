package org.karn.koreanchat.mixin;

import org.karn.koreanchat.util.ChatConversion;
import org.karn.koreanchat.util.KoreanChatData;
import org.karn.koreanchat.util.KoreanChatManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import eu.pb4.playerdata.api.PlayerDataApi;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.SignedMessageBody;
import net.minecraft.network.chat.SignedMessageChain;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow
    private SignedMessageChain.Decoder signedMessageDecoder;

    @Inject(method = "getSignedMessage", at = @At(value = "HEAD"), cancellable = true)
    private void getSignedMessage(ServerboundChatPacket packet, LastSeenMessages lastSeenMessages, CallbackInfoReturnable<PlayerChatMessage> cir) throws SignedMessageChain.DecodeException {
        ServerGamePacketListenerImpl self = (ServerGamePacketListenerImpl) (Object) this;
        KoreanChatData data = PlayerDataApi.getCustomDataFor(self.player, KoreanChatManager.KRC_DATA);
        String chatMessage = packet.message();

        if (data != null && data.krc) {
            chatMessage = ChatConversion.convertChat(chatMessage);
        }
        SignedMessageBody messageBody = new SignedMessageBody(chatMessage, packet.timeStamp(), packet.salt(), lastSeenMessages);
        cir.setReturnValue(this.signedMessageDecoder.unpack(packet.signature(), messageBody));
    }
}
