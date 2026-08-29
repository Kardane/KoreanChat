package org.karn.koreanchat.util;

import eu.pb4.playerdata.api.PlayerDataApi;
import eu.pb4.playerdata.api.storage.JsonDataStorage;
import eu.pb4.playerdata.api.storage.PlayerDataStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;


public class KoreanChatManager {
    public static final PlayerDataStorage<KoreanChatData> KRC_DATA = new JsonDataStorage<>("toggle", KoreanChatData.class);

    public static boolean isKoreanChatEnabled(MinecraftServer server, UUID uuid) {
        KoreanChatData data = PlayerDataApi.getCustomDataFor(server, uuid, KRC_DATA);
        return data != null && data.krc;
    }
    public static boolean setKoreanChat(ServerPlayer player, boolean toggle) {
        KoreanChatData data = PlayerDataApi.getCustomDataFor(player, KRC_DATA);
        if (isKoreanChatEnabled(player.level().getServer(), player.getUUID()) == toggle) {
            player.sendSystemMessage(Component.literal("[알림] 한글채팅이 이미 켜져 있거나 꺼져 있습니다!").withStyle(ChatFormatting.RED),false);
            return false;
        }

        if(data == null) {
            data = new KoreanChatData();
            data.krc = toggle;
            PlayerDataApi.setCustomDataFor(player, KRC_DATA, data);
        }

        if (toggle) ChatEnable(player);

        if (!toggle) ChatDisable(player);
        return true;
    }

    public static void ChatEnable(ServerPlayer player) {
        KoreanChatData data = PlayerDataApi.getCustomDataFor(player, KRC_DATA);
        data.krc = true;
        PlayerDataApi.setCustomDataFor(player, KRC_DATA, data);
        player.sendSystemMessage(Component.literal("[알림] 한글채팅이 활성화 되었습니다. /krc로 off로 한글채팅을 끌 수 있습니다.").withStyle(ChatFormatting.GREEN),false);
    }

    public static void ChatDisable(ServerPlayer player) {
        KoreanChatData data = PlayerDataApi.getCustomDataFor(player, KRC_DATA);
        data.krc = false;
        PlayerDataApi.setCustomDataFor(player, KRC_DATA, data);
        player.sendSystemMessage(Component.literal("[알림] 한글채팅이 비활성화 되었습니다. /krc on으로 한글채팅을 켤 수 있습니다.").withStyle(ChatFormatting.GREEN),false);
    }
}
