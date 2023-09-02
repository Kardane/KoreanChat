package org.karn.koreanchat;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.karn.koreanchat.command.ChatToggle;

public class KoreanChat implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, commandRegistryAccess, ignored1) -> {
            ChatToggle.register(dispatcher);
        });
    }

}