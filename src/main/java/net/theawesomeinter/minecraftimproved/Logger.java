package net.theawesomeinter.minecraftimproved;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;

public final class Logger {
    public static final String
        MHelp = "§cMCI:§f ",
        MInfo = "§2Info:§f ",
        MWarning = "§6Warn:§f ",
        MFatal = "§cFatal:§f ";

    public static void help(String msg) {
        info(msg);
    }
    public static void help(Player player, String msg) {
        player.sendMessage(MHelp + Component.text(msg));
    }
    public static void help(CommandSender sender, String msg) {
        sender.sendMessage(MHelp + Component.text(msg));
    }

    public static void info(String msg) {
        MinecraftImproved.getPlugin(MinecraftImproved.class).getLogger().info(msg);
    }
    public static void info(Player player, String msg) {
        player.sendMessage(MInfo + Component.text(msg));
    }
    public static void info(CommandSender sender, String msg) {
        sender.sendMessage(MInfo + Component.text(msg));
    }

    public static void warning(String msg) {
        MinecraftImproved.getPlugin(MinecraftImproved.class).getLogger().warning(msg);
    }
    public static void warning(Player player, String msg) {
        player.sendMessage(MWarning + Component.text(msg));
    }
    public static void warning(CommandSender sender, String msg) {
        sender.sendMessage(MWarning + Component.text(msg));
    }

    public static void fatal(String msg) {
        MinecraftImproved.getPlugin(MinecraftImproved.class).getLogger().severe(msg);
    }
    public static void fatal(Player player, String msg) {
        player.sendMessage(MFatal + Component.text(msg));
    }
    public static void fatal(CommandSender sender, String msg) {
        sender.sendMessage(MFatal + Component.text(msg));
    }
}
