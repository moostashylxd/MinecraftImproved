package net.theawesomeinter.minecraftimproved;

import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftImproved extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getCommandMap().register("triggerevent", "mci", new CommandEventTrigger(this));
        getServer().getPluginManager().registerEvents(new net.theawesomeinter.minecraftimproved.PlayerEventListener(), this);
        getServer().getLogger().info("Commands and events registered. Have a nice day!");
    }

    @Override
    public void onDisable() {
    }
}
