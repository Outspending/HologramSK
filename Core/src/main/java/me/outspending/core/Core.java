package me.outspending.core;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.hologramsk.*;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public final class Core extends JavaPlugin implements CommandExecutor {

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

        getCommand("testing").setExecutor(this);
        Bukkit.getLogger().log(Level.INFO, "[HologramSK] Plugin loaded successfully!");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;
        NMSHologram hologram = new NMSHologram("test", "This is a test!", player.getLocation());
        hologram.addLine("This is a test line!");
        return true;
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
                Bukkit.getLogger().log(Level.INFO, "[HologramSK] Using NMS version 1.17");
            }
            case "1.18", "1.18.1" -> {
                NMSVersion = new V1_18_R1();
                Bukkit.getLogger().log(Level.INFO, "[HologramSK] Using NMS version 1.18");
            }
            case "1.18.2" -> {
                NMSVersion = new V1_18_R1();
                Bukkit.getLogger().log(Level.INFO, "[HologramSK] Using NMS version 1.18.2");
            }
            case "1.19", "1.19.1", "1.19.2" -> {
                NMSVersion = new V1_19_R1();
                Bukkit.getLogger().log(Level.INFO, "[HologramSK] Using NMS version 1.19");
            }
            case "1.19.3" -> {
                NMSVersion = new V1_19_R2();
                Bukkit.getLogger().log(Level.INFO, "[HologramSK] Using NMS version 1.19.3");
            }
            default -> {
                Bukkit.getLogger().log(Level.SEVERE, "[HologramSK] Unsupported Minecraft version: " + version);
                Bukkit.getPluginManager().disablePlugin(plugin);
                return false;
            }
        }
        return true;
    }

    private static boolean checkPluginRequirement() {
        if (Bukkit.getPluginManager().getPlugin("Skript") == null) {
            Bukkit.getLogger().log(Level.SEVERE, "[HologramSK] Skript is required to run HologramSK!");
            Bukkit.getPluginManager().disablePlugin(plugin);
            return false;
        }
        return true;
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
