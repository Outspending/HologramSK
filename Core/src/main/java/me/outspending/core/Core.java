package me.outspending.core;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import me.outspending.core.misc.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.hologramsk.*;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public final class Core extends JavaPlugin implements Listener {

    public static JavaPlugin plugin;
    public static NMS NMSVersion;
    public static SkriptAddon addon;

    @Override
    public void onEnable() {
        plugin = this;
        if (!checkPluginRequirement()) return;
        if (!setNMSVersion()) return;

        addon = Skript.registerAddon(this);
        try {
            addon.loadClasses("me.outspending.core", "elements");
        } catch (Exception e) {
            e.printStackTrace();
        }

        UpdateChecker checker = new UpdateChecker();
        checker.getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                Bukkit.getLogger().log(Level.INFO, "[HologramSK] You are using the latest version!");
            } else {
                checker.setIsUpdateAvailable(true);
                checker.setLatestVersion(version);
                Bukkit.getLogger().log(Level.INFO, "[HologramSK] There is a new update available: " + version);
            }
        });
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getLogger().log(Level.INFO, "[HologramSK] Plugin loaded successfully!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private static boolean setNMSVersion() {
        String version = Bukkit.getServer().getMinecraftVersion();
        switch (version) {
            case "1.17" -> {
                NMSVersion = new V1_17_R1();
                sendNMSVersion("1_17_R1");
            }
            case "1.18", "1.18.1" -> {
                NMSVersion = new V1_18_R1();
                sendNMSVersion("1_18_R1");
            }
            case "1.18.2" -> {
                NMSVersion = new V1_18_R2();
                sendNMSVersion("1_18_R2");
            }
            case "1.19", "1.19.1", "1.19.2" -> {
                NMSVersion = new V1_19_R1();
                sendNMSVersion("1_19_R1");
            }
            case "1.19.3" -> {
                NMSVersion = new V1_19_R2();
                sendNMSVersion("1_19_R2");
            }
            default -> {
                Bukkit.getLogger().log(Level.SEVERE, "[HologramSK] Unsupported Minecraft version: " + version);
                Bukkit.getPluginManager().disablePlugin(plugin);
                return false;
            }
        }
        return true;
    }

    private static void sendNMSVersion(String version) {
        plugin.getLogger().log(Level.INFO, "-------[ HologramSK ]-------");
        plugin.getLogger().log(Level.INFO, "HologramSK is using NMS version " + version);
        plugin.getLogger().log(Level.INFO, "HologramSK Version: V" + plugin.getDescription().getVersion());
        plugin.getLogger().log(Level.INFO, "HologramSK PlaceholderAPI: " + (HologramAPI.hasPlaceholderAPI() ? "Enabled" : "Disabled"));
        plugin.getLogger().log(Level.INFO, "----------------------------");
    }

    private static boolean checkPluginRequirement() {
        if (Bukkit.getPluginManager().getPlugin("Skript") == null) {
            Bukkit.getLogger().log(Level.SEVERE, "[HologramSK] Skript is required to run HologramSK!");
            Bukkit.getPluginManager().disablePlugin(plugin);
            return false;
        } else if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            Bukkit.getLogger().log(Level.WARNING, "[HologramSK] PlaceholderAPI is not installed, placeholders will not work!");
            return true;
        }
        HologramAPI.setPlaceholderAPI(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    NMSVersion.replaceAllPlaceholdersInWorld(world);
                }
            }
        }.runTaskTimerAsynchronously(plugin, 20, 20);
        return true;
    }

    @EventHandler
    public void onEntityClick(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.isOp()) {
            UpdateChecker checker = new UpdateChecker();
            if (checker.getIsUpdateAvailable()) {
                player.sendMessage("§a[HologramSK] There is a new update available: " + checker.getLatestVersion());
            }
        }
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static SkriptAddon getAddon() {
        return addon;
    }

    public static NMS getNMSVersion() {
        return NMSVersion;
    }
}
