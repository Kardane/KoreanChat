package org.karn.koreanchat.mixin;

import eu.pb4.playerdata.api.PlayerDataApi;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.karn.koreanchat.util.KoreanChatData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.karn.koreanchat.util.KoreanChatManager.KRC_DATA;

@Mixin(PlayerManager.class)
public class PlayerConnectMixin {
    @Shadow
    @Final
    private MinecraftServer server;

    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    private void ChatEnableOnJoin(ClientConnection connection, ServerPlayerEntity player, ConnectedClientData clientData, CallbackInfo ci) {
        KoreanChatData data = PlayerDataApi.getCustomDataFor(player, KRC_DATA);
        if(data == null) {
            data = new KoreanChatData();
            data.krc = true;
            PlayerDataApi.setCustomDataFor(player, KRC_DATA, data);
            player.sendMessageToClient(Text.literal("[알림] 한글채팅이 활성화 되었습니다. /krc로 한글채팅을 끄고 켤 수 있습니다.").formatted(Formatting.GREEN),false);
        }
    }
}
