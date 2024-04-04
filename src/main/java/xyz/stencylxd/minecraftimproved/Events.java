package xyz.stencylxd.minecraftimproved;

import net.kyori.adventure.text.Component;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Sound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Events {
	private static final String
		MFatal_GENERIC_ERR_EXECUTE = "§cFatal:§f An internal error occured during the event on Player \"%s\".",
		MFatal_POTIONEFFECT_ERR_EXECUTE = "§cFatal:§f Unable to add status effect to Player \"%s\", Player.addPotionEffect(...) returned false.",
		MFatal_GENERIC_ERR_DEAD = "§cFatal:§f Unable to perform the action on Player \"%s\". Player is currently dead.",
		MFatal_POTIONEFFECT_ERR_DEAD = "§cFatal:§f Unable to add status effect to Player \"%s\", Player is currently dead.",
		MFatal_ANVIL_ERR_NOSPAWN = "§cFatal:§f Unable to spawn anvil at (\"%s\", \"%s\", \"%s\")",

		MWarn_COMMANDARGS_NONE = "§6Warn:§f This Event does not require arguments. Extra arguments are going unused.",

		MInfo_POTIONEFFECT_LEVITATION = "§2Info:§f Success, player \"%s\" levitated.",
		MInfo_POTIONEFFECT_SPEED = "§2Info:§f Success, player \"%s\" is moving faster.",
		MInfo_GENERIC_SINKHOLE = "§2Info:§f Success, player \"%s\" is sinking.",
		MInfo_GENERIC_CREEPERSOUND = "§2Info:§f Success, player \"%s\" is spooked.",
		MInfo_GENERIC_ONEHUNDREDANVILS = "§2Info:§f Success, player \"%s\" is flattened.";


	public static void levitation(CommandSender sender, Player player, Object[] obj) {
		if (obj != null && obj.length > 0)
			sender.sendMessage(Component.text(MWarn_COMMANDARGS_NONE));
		if (player.isDead()) {
			sender.sendMessage(Component.text(String.format(MFatal_POTIONEFFECT_ERR_DEAD, player.getName())));
			return;
		}
		if (levitation(player))
			sender.sendMessage(Component.text(String.format(MInfo_POTIONEFFECT_LEVITATION, player.getName())));
		else
			sender.sendMessage(Component.text(String.format(MFatal_POTIONEFFECT_ERR_EXECUTE, player.getName())));
	}
	public static boolean levitation(Player player) {
		return player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 127, false, false, false));
	}


	public static void speed(CommandSender sender, Player player, Object[] obj) {
		if (obj != null && obj.length > 0)
			sender.sendMessage(Component.text(MWarn_COMMANDARGS_NONE));
		if (player.isDead()) {
			sender.sendMessage(Component.text(String.format(MFatal_POTIONEFFECT_ERR_DEAD, player.getName())));
			return;
		}
		if (speed(player))
			sender.sendMessage(Component.text(String.format(MInfo_POTIONEFFECT_SPEED, player.getName())));
		else
			sender.sendMessage(Component.text(String.format(MFatal_POTIONEFFECT_ERR_EXECUTE, player.getName())));
	}
	public static boolean speed(Player player) {
		return player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 127, false, false, false));
	}


	public static void sinkhole(CommandSender sender, Player player, Object[] obj) {
		if (obj != null && obj.length > 0)
			sender.sendMessage(Component.text(MWarn_COMMANDARGS_NONE));
		if (player.isDead()) {
			sender.sendMessage(Component.text(String.format(MFatal_GENERIC_ERR_DEAD, player.getName())));
			return;
		}
		if (sinkhole(player))
			sender.sendMessage(Component.text(String.format(MInfo_GENERIC_SINKHOLE, player.getName())));
		else
			sender.sendMessage(Component.text(String.format(MFatal_GENERIC_ERR_EXECUTE, player.getName())));
	}
	// TODO: Finish this
	public static boolean sinkhole(Player player) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000, 3, false, false, false));
		Block center = player.getLocation().getBlock();
		BlockFace playerFace = player.getFacing();
		Block[] hole = new Block[16];
		Block topLeft = center.getRelative(playerFace, 2).getRelative(-1, 0, 0);
		
		
		
		return true;
	}


	public static void creeper_sound(CommandSender sender, Player player, Object[] obj) {
		if (obj != null && obj.length > 0)
			sender.sendMessage(Component.text(MWarn_COMMANDARGS_NONE));
		if (player.isDead()) {
			sender.sendMessage(Component.text(String.format(MFatal_GENERIC_ERR_DEAD, player.getName())));
			return;
		}
		if (creeper_sound(player))
			sender.sendMessage(Component.text(String.format(MInfo_GENERIC_CREEPERSOUND, player.getName())));
		else
			sender.sendMessage(Component.text(String.format(MFatal_GENERIC_ERR_EXECUTE, player.getName())));
	}
	public static boolean creeper_sound(Player player) {
		player.playSound(player.getLocation().subtract(player.getFacing().getDirection()), Sound.ENTITY_CREEPER_PRIMED, 1, 1);

		new BukkitRunnable() {
			public void run() {
				// this is just completely derranged
				byte slot = 40;
				if (player.getInventory().getItemInMainHand().getType() == Material.SHIELD)
					slot = (byte)player.getInventory().getHeldItemSlot();
				ItemStack item = player.getInventory().getItem(slot);
				player.getInventory().setItem(slot, new ItemStack(Material.AIR));
				player.getWorld().dropItemNaturally(player.getLocation(), item).setPickupDelay(80);
				Location a = player.getLocation().add(player.getFacing().getDirection());
				player.getWorld().getBlockAt(a).breakNaturally();
				player.getWorld().getBlockAt(a.add(new Vector(0, 1, 0))).breakNaturally();
				player.getWorld().getBlockAt(a.add(new Vector(0, -2, 0))).setType(Material.STONE);
				Creeper c = player.getWorld().spawn(a.add(new Vector(0, 1, 0)), Creeper.class);
				c.setInvulnerable(true);
				c.setSilent(true);
				c.setPowered(true);
				c.setRotation(-player.getBodyYaw(), 0);
				player.playSound(a, Sound.BLOCK_GLASS_BREAK, 1, 1);
				c.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 32000, 3, false, false, false));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 400, 3, false, false, false));
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 400, 127, false, false, false));
			}
		}.runTaskLater(MinecraftImproved.getPlugin(MinecraftImproved.class), 20);
		return true;
	}


	public static void one_hundred_anvils(CommandSender sender, Player player, Object[] obj) {
		if (obj != null && obj.length > 0)
			sender.sendMessage(Component.text(MWarn_COMMANDARGS_NONE));
		if (player.isDead()) {
			sender.sendMessage(Component.text(String.format(MFatal_GENERIC_ERR_DEAD, player.getName())));
			return;
		}
		if (one_hundred_anvils(player))
			sender.sendMessage(Component.text(String.format(MInfo_GENERIC_ONEHUNDREDANVILS, player.getName())));
		else {
			sender.sendMessage(Component.text(String.format(MFatal_GENERIC_ERR_EXECUTE, player.getName())));
			sender.sendMessage(Component.text(String.format(MFatal_ANVIL_ERR_NOSPAWN, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ())));
		}
	}
	public static boolean one_hundred_anvils(Player player) {
		Location playerLocation = player.getLocation();
		BlockData anvilMat = Material.ANVIL.createBlockData();
		FallingBlock[] anvils = new FallingBlock[100];
		// short x = playerLocation.getX(), z = playerLocation.get;
		
		try {
			Location anvilCenter = playerLocation.add(new Vector(-5, 50, -5));
			byte i = 0;
			for (byte y = 1; y < 11; y++)
				for (byte x = 1; x < 11; x++) {
					anvils[i] = playerLocation.getWorld().spawn(
						anvilCenter.offset(x, 0, y).toLocation(player.getWorld()),
						FallingBlock.class
					);
					anvils[i].setBlockData(anvilMat);
					anvils[i].setVelocity(new Vector(0, -.8, 0));
					anvils[i].setDamagePerBlock(100);
					anvils[i].setCancelDrop(true);
					anvils[i].setSilent(true);
					anvils[i++].setDropItem(false);
				}
			anvils[0].setSilent(false);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 2, false, false, false));
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 400, 127, false, false, false));
		} catch (IllegalArgumentException ex) {
			return false;
		}

		return true;
	}
}
