package xyz.stencylxd.minecraftimproved;

import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftImproved extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getCommandMap().register("triggerevent", "mci", new CommandEventTrigger(this));
        getServer().getPluginManager().registerEvents(new xyz.stencylxd.minecraftimproved.PlayerEventListener(), this);
    }

    @Override
    public void onDisable() {
    }
}
