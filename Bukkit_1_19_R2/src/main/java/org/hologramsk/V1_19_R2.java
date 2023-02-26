package org.hologramsk;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class V1_19_R2 implements NMS, HologramData {

    @Override
    public void moveHologram(Hologram hologram, Location location) {
        List<HologramLine> lines = hologram.getLines();
        int index = 0;
        for (HologramLine line : lines) {
            double y = hologram.getNextLineLocation(index).getY();
            LivingEntity entity = ((CraftLivingEntity) line.getArmorStand()).getHandle();
            entity.teleportTo(location.getX(), y, location.getZ());
            ClientboundTeleportEntityPacket teleportPacket = new ClientboundTeleportEntityPacket(entity);
            for (Player player : location.getWorld().getPlayers()) {
                ServerPlayer plr = ((CraftPlayer) player).getHandle();
                plr.connection.send(teleportPacket);
            }
            index++;
        }
    }

    @Override
    public void addHologramLine(Hologram hologram, HologramLine line) {
        Location location = hologram.getLocation();
        location.setY(location.getY() - (hologram.getLineCount() * hologram.getLineHeight()));
        hologram.getLines().add(line);
        updateHologramLine(hologram, line);
    }

    @Override
    public void addHologramLine(Hologram hologram, String name) {
        Location location = hologram.getLocation().clone().add(0, hologram.getLineCount() * -hologram.getLineHeight(), 0);
    }

    @Override
    public void addHologramLine(Hologram hologram, Material material) {

    }

    @Override
    public void removeHologramLine(Hologram hologram, int index) {

    }

    @Override
    public void displayHologram(Hologram hologram, Player player) {
        List<HologramLine> lines = hologram.getLines();
        ServerPlayer plr = ((CraftPlayer) player).getHandle();
        for (HologramLine line : lines) {
            net.minecraft.world.entity.LivingEntity entity = ((CraftLivingEntity) line.getArmorStand()).getHandle();
            ClientboundAddEntityPacket packet = new ClientboundAddEntityPacket(entity);
            plr.connection.send(packet);
        }
    }

    @Override
    public void hideHologram(Hologram hologram, Player player) {
        List<HologramLine> lines = hologram.getLines();
        ServerPlayer plr = ((CraftPlayer) player).getHandle();
        for (HologramLine line : lines) {
            net.minecraft.world.entity.LivingEntity entity = ((CraftLivingEntity) line.getArmorStand()).getHandle();
            ClientboundRemoveEntitiesPacket packet = new ClientboundRemoveEntitiesPacket(entity.getId());
            plr.connection.send(packet);
        }
    }

    @Override
    public void deleteHologram(Hologram hologram) {
        List<HologramLine> lines = hologram.getLines();
        for (HologramLine line : lines) {
            net.minecraft.world.entity.LivingEntity entity = ((CraftLivingEntity) line.getArmorStand()).getHandle();
            ClientboundRemoveEntitiesPacket packet = new ClientboundRemoveEntitiesPacket(entity.getId());
            for (Player plr : hologram.getLocation().getWorld().getPlayers()) {
                ServerPlayer player = ((CraftPlayer) plr).getHandle();
                player.connection.send(packet);
            }
        }
    }

    @Override
    public void updateHologram(Hologram hologram) {
        for (HologramLine line : hologram.getLines()) {
            updateHologramLine(hologram, line);
        }
    }

    @Override
    public void updateHologramLine(Hologram hologram, HologramLine line) {
        net.minecraft.world.entity.LivingEntity entity = ((CraftLivingEntity) line.getArmorStand()).getHandle();
        ClientboundAddEntityPacket spawnPacket = new ClientboundAddEntityPacket(entity);
        ClientboundSetEntityDataPacket dataPacket = new ClientboundSetEntityDataPacket(entity.getId(), entity.getEntityData().getNonDefaultValues());
        Location location = hologram.getLocation();
        for (Player plr : location.getWorld().getPlayers()) {
            ServerPlayer player = ((CraftPlayer) plr).getHandle();
            player.connection.send(spawnPacket);
            player.connection.send(dataPacket);
        }
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
        Hologram hologram = hologramLine.getHologram();
        if (hologram.getHiddenPlayers().contains(player.getUniqueId())) {
            net.minecraft.world.entity.LivingEntity entity = ((CraftLivingEntity) hologramLine.getArmorStand()).getHandle();
            ClientboundAddEntityPacket spawnPacket = new ClientboundAddEntityPacket(entity);
            ClientboundSetEntityDataPacket dataPacket = new ClientboundSetEntityDataPacket(entity.getId(), entity.getEntityData().getNonDefaultValues());
            ServerPlayer plr = ((CraftPlayer) player).getHandle();
            plr.connection.send(spawnPacket);
            plr.connection.send(dataPacket);
            hologram.getHiddenPlayers().remove(player.getUniqueId());
        }
    }

    @Override
    public void hideFrom(HologramLine hologramLine, Player player) {
        Hologram hologram = hologramLine.getHologram();
        if (!hologram.getHiddenPlayers().contains(player.getUniqueId())) {
            net.minecraft.world.entity.LivingEntity entity = ((CraftLivingEntity) hologramLine.getArmorStand()).getHandle();
            ServerPlayer plr = ((CraftPlayer) player).getHandle();
            ClientboundRemoveEntitiesPacket removePacket = new ClientboundRemoveEntitiesPacket(entity.getId());
            plr.connection.send(removePacket);
            hologram.getHiddenPlayers().add(player.getUniqueId());
        }
    }

    @Override
    public void updateTextFor(HologramLine hologramLine, String text, Player player) {
        Component component = Component.nullToEmpty(text);
        ArmorStand armorStand = (ArmorStand) ((CraftLivingEntity) hologramLine.getArmorStand()).getHandle();
        armorStand.setCustomName(component);
        ClientboundSetEntityDataPacket packet = new ClientboundSetEntityDataPacket(armorStand.getId(), armorStand.getEntityData().getNonDefaultValues());
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }

    @Override
    public void updateLocationFor(HologramLine hologramLine, Location location, Player player) {
        LivingEntity entity = ((CraftLivingEntity) hologramLine.getArmorStand()).getHandle();
        entity.teleportTo(location.getX(), location.getY(), location.getZ());
        ClientboundTeleportEntityPacket packet = new ClientboundTeleportEntityPacket(entity);
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }

    @Override
    public org.bukkit.entity.LivingEntity spawnArmorStand(Location location, String name) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        Component component = Component.nullToEmpty(name);
        ArmorStand armorStand = new ArmorStand(level, location.getX(), location.getY(), location.getZ());

        armorStand.setInvisible(true);
        armorStand.setInvulnerable(true);
        armorStand.setMarker(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(component);
        armorStand.setNoGravity(true);
        return (org.bukkit.entity.LivingEntity) armorStand.getBukkitEntity();
    }

    @Override
    public org.bukkit.entity.LivingEntity spawnArmorStand(Location location) {
        return this.spawnArmorStand(location, "Placeholder");
    }

    @Override
    public void loadAllHolograms(World world, Player player) {

    }
}
