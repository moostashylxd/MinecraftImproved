package xyz.stencylxd.minecraftimproved;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandEventTrigger extends Command {

	public Plugin plugin;
	public CommandEventTrigger(Plugin plugin) {
		super("triggerevent", "§cMCI:§f Trigger an event.", "/et <EventName (string)> <TargetUsername (string)> [ARGS]", new ArrayList<>(List.of("et", "trigev")));
		this.plugin = plugin;
	}
	@Override
	public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
		if (strings.length < 2) {
			commandSender.sendMessage(Component.text("§2Info:§f /" + s + " <EventName (string)> <TargetUsername (string)> [ARGS]"));
			return true;
		}
		try {
			Method a = Events.class.getDeclaredMethod(strings[0].toLowerCase(), CommandSender.class, Player.class, Object[].class);
			Player b = plugin.getServer().getPlayerExact(strings[1]);
			if (b == null) {
				commandSender.sendMessage(Component.text("§cFatal:§f TargetUsername does not match the Username of an online Player."));
				return false;
			}
			a.invoke(CommandEventTrigger.class, commandSender, b, Arrays.copyOfRange(strings, 2, strings.length));
		} catch (NoSuchMethodException ex) {
			commandSender.sendMessage(Component.text("§cFatal:§f EventName does not match the name of an existing Event."));
			return false;
		} catch (InvocationTargetException | IllegalAccessException e) {
			// Shouldn't ever happen unless the user purposely tries to break the code (char overflow, invalid). Maybe TODO?
			return false;
		}

		return true;
	}
}
