package org.hologramsk;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class V1_17_R1 implements NMS {

    @Override
    public void createHologram(String name, Location location) {

    }

    @Override
    public void moveHologram(Hologram hologram, Location location) {

    }

    @Override
    public void addHologramLine(Hologram hologram, HologramLine line) {
        Location location = hologram.getLocation();
        location.setY(location.getY() - (hologram.getLineCount() * hologram.getLineHeight()));

        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        Component component = Component.nullToEmpty(line.getText());
        ArmorStand armorStand = new ArmorStand(level, location.getX(), location.getY(), location.getZ());

        armorStand.setInvisible(true);
        armorStand.setInvulnerable(true);
        armorStand.setMarker(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(component);
        armorStand.setNoGravity(true);
        updateHologramLine(hologram, line);
    }

    @Override
    public void addHologramLine(Hologram hologram, String name) {

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
        ClientboundAddEntityPacket spawnPacket = new ClientboundAddEntityPacket((Entity) line.getArmorStand());
        Location location = hologram.getLocation();
        location.getWorld().getPlayers().forEach(player -> {
            ((CraftPlayer) player).getHandle().connection.send(spawnPacket);
        });
    }

    @Override
    public void insertLine(Hologram hologram, HologramLine line, int index) {

    }

    @Override
    public void update(Hologram hologram) {

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
    public void teleportLine(HologramLine hologramLine, Location location) {

    }

    @Override
    public void setText(HologramLine hologramLine, String text) {

    }

    @Override
    public void updateTextFor(HologramLine hologramLine, String text, Player player) {

    }

    @Override
    public void updateLocationFor(HologramLine hologramLine, Location location, Player player) {

    }

    @Override
    public void updateLine(HologramLine hologramLine) {

    }

    @Override
    public void updateHologramsInWorld(World world) {

    }

    @Override
    public LivingEntity spawnArmorStand(Location location, String name) {
        return null;
    }

    @Override
    public LivingEntity spawnArmorStand(Location location) {
        return null;
    }
}
