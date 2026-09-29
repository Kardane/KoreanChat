package org.karn.koreanchat.neoforge;

import net.minecraft.world.entity.player.Player;

public final class ChatSettings {
    private static final String KEY = "KoreanChatEnabled";

    private ChatSettings() {}

    public static boolean initialize(Player player) {
        if (player.getPersistentData().contains(KEY)) return false;
        player.getPersistentData().putBoolean(KEY, true);
        return true;
    }

    public static boolean isEnabled(Player player) {
        return !player.getPersistentData().contains(KEY) || player.getPersistentData().getBooleanOr(KEY, true);
    }

    public static void setEnabled(Player player, boolean enabled) {
        player.getPersistentData().putBoolean(KEY, enabled);
    }

    public static void copy(Player original, Player replacement) {
        if (original.getPersistentData().contains(KEY)) {
            setEnabled(replacement, isEnabled(original));
        }
    }
}
