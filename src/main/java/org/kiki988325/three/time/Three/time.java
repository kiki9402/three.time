package org.kiki988325.three.time.Three;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public final class time extends JavaPlugin implements Listener {
    private final Set<Player> invinciblePlayers = new HashSet<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("three times plugins가 활성화 됐습니다!");
    }

    @Override
    public void onDisable() {
        getLogger().info("three times plugins가 비활성화 됐습니다!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        invinciblePlayers.add(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                invinciblePlayers.remove(player);
            }
        }.runTaskLater(this, 60); // 3초 (1초 = 20틱)
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (invinciblePlayers.contains(player)) {
                event.setCancelled(true);
            }
        }
    }
}
