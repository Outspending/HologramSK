package me.outspending.core;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.hologramsk.*;

import java.util.logging.Level;

public final class Core extends JavaPlugin {

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
                NMSVersion = new V1_18_R2();
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
}
