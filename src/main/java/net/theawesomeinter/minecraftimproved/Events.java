package net.theawesomeinter.minecraftimproved;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Sound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Events {
	private static final String
		GENERIC_ERR_EXECUTE = "An internal error occured during the event on Player \"%s\".",
		POTIONEFFECT_ERR_EXECUTE = "Unable to add status effect to Player \"%s\", Player.addPotionEffect(...) returned false.",
		GENERIC_ERR_DEAD = "Unable to perform the action on Player \"%s\". Player is currently dead.",
		POTIONEFFECT_ERR_DEAD = "Unable to add status effect to Player \"%s\", Player is currently dead.",
		ANVIL_ERR_NOSPAWN = "Unable to spawn anvil at (\"%s\", \"%s\", \"%s\")",

		COMMANDARGS_NONE = "This Event does not require arguments. Extra arguments are going unused.",

		POTIONEFFECT_LEVITATION = "Success, player \"%s\" levitated.",
		POTIONEFFECT_SPEED = "Success, player \"%s\" is moving faster.",
		GENERIC_SINKHOLE = "Success, player \"%s\" is sinking.",
		GENERIC_CREEPERSOUND = "Success, player \"%s\" is spooked.",
		GENERIC_ONEHUNDREDANVILS = "§2Info:§f Success, player \"%s\" is flattened.";


	public static void levitation(CommandSender sender, Player player, Object[] obj) {
		if (obj != null && obj.length > 0)
			Logger.warning(sender, COMMANDARGS_NONE);
		if (player.isDead()) {
			Logger.fatal(sender, String.format(POTIONEFFECT_ERR_DEAD, player.getName()));
			return;
		}
		if (levitation(player))
			Logger.info(sender, String.format(POTIONEFFECT_LEVITATION, player.getName()));
		else
			Logger.fatal(sender, String.format(POTIONEFFECT_ERR_EXECUTE, player.getName()));
	}
	public static boolean levitation(Player player) {
		return player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 127, false, false, false));
	}

	public static void speed(CommandSender sender, Player player, Object[] obj) {
		if (obj != null && obj.length > 0)
			Logger.warning(sender, COMMANDARGS_NONE);
		if (player.isDead()) {
			Logger.fatal(sender, String.format(POTIONEFFECT_ERR_DEAD, player.getName()));
			return;
		}
		if (speed(player))
			Logger.info(sender, String.format(POTIONEFFECT_SPEED, player.getName()));
		else
			Logger.fatal(sender, String.format(POTIONEFFECT_ERR_EXECUTE, player.getName()));
	}
	public static boolean speed(Player player) {
		return player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, 127, false, false, false));
	}


	public static void sinkhole(CommandSender sender, Player player, Object[] obj) {
		if (obj != null && obj.length > 0)
			Logger.warning(sender, COMMANDARGS_NONE);
		if (player.isDead()) {
			Logger.fatal(sender, String.format(GENERIC_ERR_DEAD, player.getName()));
			return;
		}
		if (sinkhole(player))
			Logger.info(sender, String.format(GENERIC_SINKHOLE, player.getName()));
		else
			Logger.fatal(sender, String.format(GENERIC_ERR_EXECUTE, player.getName()));
	}
	public static boolean sinkhole(Player player) {
		player.setVelocity(new Vector(0, -3, 0));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 6, false, false, false));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, 10, false, false, false));
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 10, false, false, false));
		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 35, 250, false, false, false));
		player.setFreezeTicks(300);
		Location playerLocation = player.getLocation();
		int z = (int)playerLocation.getY() - 10;
		Block[] blocks = new Block[9];
		Location center = playerLocation.add(new Vector(-1, 0, -1));
		byte i = 0, y = 0, x = 0;
		for (; y < 3; y++)
			for (x = 0; x < 3; x++)
				blocks[i++] = playerLocation.getWorld().getBlockAt(center.offset(x, 0, y).toLocation(player.getWorld()));
		y = 1;
		for (;;) {
			if (
				!player.getInventory().getItemInMainHand().isEmpty() 
				&&
				player.getInventory().getItemInMainHand().getType().isBlock()
			) {
				ItemStack item = player.getInventory().getItemInMainHand();
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
				player.getWorld().dropItemNaturally(player.getLocation(), item).setPickupDelay(80);
			}
			i = (byte)Math.round(Math.random() * 8);
			Block a = blocks[i];
			new BukkitRunnable() {
				public void run() {
					if (
						!player.getInventory().getItemInMainHand().isEmpty() 
						&&
						player.getInventory().getItemInMainHand().getType().isBlock()
					) {
						ItemStack item = player.getInventory().getItemInMainHand();
						player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
						player.getWorld().dropItemNaturally(player.getLocation(), item).setPickupDelay(80);
					}
					a.breakNaturally();
				}
			}.runTaskLater(MinecraftImproved.getPlugin(MinecraftImproved.class), Math.round(Math.random() + y));
			blocks[i] = blocks[i].getRelative(BlockFace.DOWN);
			if (z - 30 > blocks[i].getLocation().getBlockY() || z - 15 > player.getLocation().getBlockY())
				break;
			else if (z > blocks[i].getLocation().getBlockY())
				blocks[i].setType(Material.LAVA);
			y++;
		}
		player.damage(player.getHealth() / 2);
		return true;
	}

	public static void creeper_sound(CommandSender sender, Player player, Object[] obj) {
		if (obj != null && obj.length > 0)
			Logger.warning(sender, COMMANDARGS_NONE);
		if (player.isDead()) {
			Logger.fatal(sender, String.format(GENERIC_ERR_DEAD, player.getName()));
			return;
		}
		if (creeper_sound(player))
			Logger.info(sender, String.format(GENERIC_CREEPERSOUND, player.getName()));
		else
			Logger.fatal(sender, String.format(GENERIC_ERR_EXECUTE, player.getName()));
	}
	public static boolean creeper_sound(Player player) {
		player.playSound(player.getLocation().subtract(player.getFacing().getDirection()), Sound.ENTITY_CREEPER_PRIMED, 1, 1);

		new BukkitRunnable() {
			public void run() {
				// this is just completely derranged
				ItemStack item = player.getInventory().getItemInMainHand();
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
				player.getWorld().dropItemNaturally(player.getLocation(), item).setPickupDelay(80);
				player.getInventory().setHeldItemSlot((byte)Math.round(Math.random() * 8));
				Location a = player.getLocation().add(player.getFacing().getDirection());
				player.getWorld().getBlockAt(a.add(new Vector(-1, 0, 0))).breakNaturally();
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
			}
		}.runTaskLater(MinecraftImproved.getPlugin(MinecraftImproved.class), 60);

		return true;
	}

	public static void one_hundred_anvils(CommandSender sender, Player player, Object[] obj) {
		if (obj != null && obj.length > 0)
			Logger.warning(sender, COMMANDARGS_NONE);
		if (player.isDead()) {
			Logger.fatal(sender, String.format(GENERIC_ERR_DEAD, player.getName()));
			return;
		}
		if (one_hundred_anvils(player))
			Logger.info(sender, String.format(GENERIC_ONEHUNDREDANVILS, player.getName()));
		else {
			Logger.fatal(sender, String.format(GENERIC_ERR_EXECUTE, player.getName()));
			Logger.fatal(sender, String.format(ANVIL_ERR_NOSPAWN, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
		}
	}
	public static boolean one_hundred_anvils(Player player) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 2, false, false, false));
		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 400, 250, false, false, false));
		Location playerLocation = player.getLocation();
		BlockData anvilMat = Material.ANVIL.createBlockData();
		FallingBlock[] anvils = new FallingBlock[100];
		player.playSound(playerLocation.add(0, 10, 0), Sound.BLOCK_GLASS_BREAK, 1, 1);
		try {
			Location anvilCenter = playerLocation.add(new Vector(-5, 40, -5));
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
		} catch (IllegalArgumentException ex) {
			return false;
		}

		return true;
	}
}
