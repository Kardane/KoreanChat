package org.karn.koreanchat.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import org.karn.koreanchat.util.KoreanChatManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.*;
import static org.karn.koreanchat.util.KoreanChatManager.setKoreanChat;


public class ChatToggle {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("krc")
                .then(CommandManager.literal("on")
                    .executes(ctx -> {
                        setKoreanChat(ctx.getSource().getPlayer(),true);
                        return 1;
                    })
                )
                .then(CommandManager.literal("off")
                        .executes(ctx -> {
                            setKoreanChat(ctx.getSource().getPlayer(),false);
                            return 1;
                        })
                )
        );
    }
}
