package me.tofumc.tofumcaddon.events;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;

import me.tofumc.tofumcaddon.TofuMCAddon;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;

public class PhoenixDown implements Listener {
    private final TofuMCAddon plugin;
    public static SlimefunItemStack myItem;

    public PhoenixDown(TofuMCAddon main, SlimefunItemStack item) {
        this.plugin = main;
        myItem = item;
        checkInv();
    }

    private void checkInv()
    {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, () -> {
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(myItem.getItemMeta().equals(p.getInventory().getItemInMainHand().getItemMeta())) {
                    p.setFireTicks(25);
                }
            }
        }, 0L, 20L);
    }



}
