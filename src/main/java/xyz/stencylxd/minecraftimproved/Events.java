package xyz.stencylxd.minecraftimproved;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Events {
	public static void levitation(CommandSender sender, Player player, Object[] obj) {
		if (obj != null && obj.length > 0)
			sender.sendMessage(Component.text("§6Warn:§f This Event does not require arguments. Extra arguments are going unused."));
		if (player.isDead()) {
			sender.sendMessage(Component.text("§cFatal:§f Unable to add status effect to Player \"" + player.getName() + "\", Player is currently dead."));
			return;
		}
		if (levitation(player))
			sender.sendMessage(Component.text("§2Info:§f Success, player \"" + player.getName() + "\" levitated."));
		else
			sender.sendMessage(Component.text("§cFatal:§f Unable to add status effect to Player \"" + player.getName() + "\", Player.addPotionEffect(...) returned false."));
	}
	public static boolean levitation(Player player) {
		return player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 127, false, false, false));
	}

}
