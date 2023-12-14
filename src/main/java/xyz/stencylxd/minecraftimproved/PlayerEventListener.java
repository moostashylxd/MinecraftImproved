package xyz.stencylxd.minecraftimproved;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class PlayerEventListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        e.allow();
        if ((int)(Math.random() % 10) == 0)
            Events.levitation(e.getPlayer());
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent e) {
        ItemStack a = e.getPlayer().getInventory().getItem(e.getNewSlot());
        if (a == null)
            return;
        e.getPlayer().sendMessage(a.displayName());
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
    }
}
