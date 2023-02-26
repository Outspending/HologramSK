package org.hologramsk;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class V1_19_R1 implements NMS, HologramData {

    @Override
    public void reloadHologram(Hologram hologram) {

    }

    @Override
    public void moveHologram(Hologram hologram, Location location) {

    }

    @Override
    public void addHologramLine(Hologram hologram, HologramLine line) {

    }

    @Override
    public void addHologramLine(Hologram hologram, String name) {

    }

    @Override
    public void addHologramLine(Hologram hologram, Material material) {

    }

    @Override
    public void removeHologramLine(Hologram hologram, int index) {

    }

    @Override
    public void displayHologram(Hologram hologram, Player player) {

    }

    @Override
    public void hideHologram(Hologram hologram, Player player) {

    }

    @Override
    public void deleteHologram(Hologram hologram) {

    }

    @Override
    public void updateHologram(Hologram hologram) {

    }

    @Override
    public void updateHologramLine(Hologram hologram, HologramLine line) {

    }

    @Override
    public void insertLine(Hologram hologram, HologramLine line, int index) {

    }

    @Override
    public void setLines(String... lines) {

    }

    @Override
    public List<HologramLine> getLines() {
        return null;
    }

    @Override
    public void showTo(HologramLine hologramLine, Player player) {

    }

    @Override
    public void hideFrom(HologramLine hologramLine, Player player) {

    }

    @Override
    public void updateTextFor(HologramLine hologramLine, String text, Player player) {

    }

    @Override
    public void updateLocationFor(HologramLine hologramLine, Location location, Player player) {

    }

    @Override
    public LivingEntity spawnArmorStand(Location location, String name) {
        return null;
    }

    @Override
    public LivingEntity spawnArmorStand(Location location) {
        return null;
    }

    @Override
    public void loadAllHolograms(World world, Player player) {

    }

    @Override
    public boolean checkPlaceholder(Hologram hologram, int index) {
        return false;
    }

    @Override
    public boolean checkPlaceholder(Hologram hologram, HologramLine line) {
        return false;
    }

    @Override
    public void replacePlaceholder(Hologram hologram, int index) {

    }

    @Override
    public void replacePlaceholder(Hologram hologram, int index, String text) {

    }

    @Override
    public void replacePlaceholder(Hologram hologram, HologramLine line) {

    }

    @Override
    public void replaceAllPlaceholders(Hologram hologram) {

    }

    @Override
    public void replaceAllPlaceholdersInWorld(World world) {

    }
}
