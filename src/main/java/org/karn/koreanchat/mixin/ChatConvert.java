package org.karn.koreanchat.mixin;

import eu.pb4.playerdata.api.PlayerDataApi;

import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.karn.koreanchat.util.ExceptionStringUtil;
import org.karn.koreanchat.util.KoreanChatData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.karn.koreanchat.util.KoreanChatManager.KRC_DATA;
@Mixin(ServerPlayNetworkHandler.class)
public class ChatConvert {

    @Shadow
    @Final
    private MinecraftServer server;
    @Shadow public ServerPlayerEntity player;

    @Inject(method = "onChatMessage",at = @At(value ="HEAD"),cancellable = true)
    public void ChatConvert(ChatMessageC2SPacket packet, CallbackInfo ci){
        //ci.cancel();
        String Message = packet.chatMessage();
        KoreanChatData data = PlayerDataApi.getCustomDataFor(player, KRC_DATA);
        if(data.krc) {
            Message = new ExceptionStringUtil().getString(Message);
        }
        Text NameMessage = Text.literal("<").append(this.player.getDisplayName()).append("> ").append(Text.literal(Message));
        this.server.getPlayerManager().broadcast(NameMessage,false);
    }
}
