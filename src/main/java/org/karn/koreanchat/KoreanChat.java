package org.karn.koreanchat;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.message.v1.ServerMessageDecoratorEvent;
import net.minecraft.text.Text;
import org.karn.koreanchat.command.ChatToggle;
import org.karn.koreanchat.util.ExceptionStringUtil;

import java.util.concurrent.CompletableFuture;

public class KoreanChat implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, commandRegistryAccess, ignored1) -> {
            ChatToggle.register(dispatcher);
        });

        ServerMessageDecoratorEvent.EVENT.register(ServerMessageDecoratorEvent.CONTENT_PHASE, (sender, message) -> {
            Text replaced = message.copy().append(" :)");
            return CompletableFuture.completedFuture(replaced);
        });
    }

}