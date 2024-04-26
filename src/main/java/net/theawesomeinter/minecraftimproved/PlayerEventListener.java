package net.theawesomeinter.minecraftimproved;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerEventListener implements Listener {
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        e.allow();
        // Levitation event calculation
        // if ((int)(Math.random() % 10) == 0)
            // Events.levitation(e.getPlayer());

        
    }
}
