package org.karn.koreanchat.neoforge;

import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.karn.koreanchat.util.ChatConversion;

@Mod("koreanchat")
public class KoreanChatNeoForge {
    public KoreanChatNeoForge() {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("krc")
            .then(Commands.literal("on").executes(context -> setEnabled(context.getSource().getPlayerOrException(), true)))
            .then(Commands.literal("off").executes(context -> setEnabled(context.getSource().getPlayerOrException(), false))));
    }

    private static int setEnabled(ServerPlayer player, boolean enabled) {
        if (ChatSettings.isEnabled(player) == enabled) {
            player.sendSystemMessage(Component.literal("[알림] 한글채팅이 이미 켜져 있거나 꺼져 있습니다!").withStyle(ChatFormatting.RED));
            return 0;
        }
        ChatSettings.setEnabled(player, enabled);
        String message = enabled
            ? "[알림] 한글채팅이 활성화 되었습니다. /krc off로 한글채팅을 끌 수 있습니다."
            : "[알림] 한글채팅이 비활성화 되었습니다. /krc on으로 한글채팅을 켤 수 있습니다.";
        player.sendSystemMessage(Component.literal(message).withStyle(ChatFormatting.GREEN));
        return 1;
    }

    @SubscribeEvent
    public void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && ChatSettings.initialize(player)) {
            player.sendSystemMessage(Component.literal("[알림] 한글채팅이 활성화 되었습니다. /krc로 한글채팅을 끄고 켤 수 있습니다.").withStyle(ChatFormatting.GREEN));
        }
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        ChatSettings.copy(event.getOriginal(), event.getEntity());
    }

    @SubscribeEvent
    public void onChat(ServerChatEvent event) {
        if (ChatSettings.isEnabled(event.getPlayer())) {
            event.setMessage(Component.literal(ChatConversion.convertChat(event.getRawText())));
        }
    }
}
