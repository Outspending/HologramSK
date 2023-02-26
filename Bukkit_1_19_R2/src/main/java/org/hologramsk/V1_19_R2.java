package org.hologramsk;

import me.clip.placeholderapi.PlaceholderAPI;
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
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static org.bukkit.craftbukkit.v1_19_R2.inventory.CraftItemStack.asNMSCopy;

public class V1_19_R2 implements NMS, HologramData {

    @Override
    public void reloadHologram(Hologram hologram) {
        HologramLine[] lines = hologram.getLines().toArray(new HologramLine[0]);
        for (int i = 0; i < lines.length; i++) {
            LivingEntity entity = ((CraftLivingEntity) lines[i].getArmorStand()).getHandle();
            Location location = hologram.getNextLineLocation(i);
            entity.teleportTo(location.getX(), location.getY(), location.getZ());

            ClientboundRemoveEntitiesPacket removePacket = new ClientboundRemoveEntitiesPacket(entity.getId());
            ClientboundAddEntityPacket addPacket = new ClientboundAddEntityPacket(entity);
            ClientboundTeleportEntityPacket teleportPacket = new ClientboundTeleportEntityPacket(entity);
            for (Player player : hologram.getLocation().getWorld().getPlayers()) {
                ServerPlayer plr = ((CraftPlayer) player).getHandle();
                plr.connection.send(removePacket);
                plr.connection.send(addPacket);
                plr.connection.send(teleportPacket);
            }
        }
    }

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
        location.setY(location.getY() - ((hologram.getLineCount() + 1) * -hologram.getLineHeight()));
        hologram.getLines().add(line);
        updateHologramLine(hologram, line);
    }

    @Override
    public void addHologramLine(Hologram hologram, String name) {

    }

    @Override
    public void addHologramLine(Hologram hologram, Material material) {
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(new ItemStack(material));
        ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, ((CraftWorld) hologram.getLocation().getWorld()).getHandle());
        itemEntity.setItem(item);
        Location spawnLocation = hologram.getLocation().clone().add(0, hologram.getLineCount() * -hologram.getLineHeight(), 0);
        itemEntity.teleportTo(spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ());
        ClientboundAddEntityPacket packet = new ClientboundAddEntityPacket(itemEntity);
        ClientboundTeleportEntityPacket teleportPacket = new ClientboundTeleportEntityPacket(itemEntity);
        for (Player player : hologram.getLocation().getWorld().getPlayers()) {
            ServerPlayer plr = ((CraftPlayer) player).getHandle();
            plr.connection.send(packet);
            plr.connection.send(teleportPacket);
            if (HologramAPI.hasPlaceholderAPI()) {
                if (checkPlaceholder(hologram, hologram.getLineCount())) {
                    ClientboundSetEntityDataPacket dataPacket = new ClientboundSetEntityDataPacket(itemEntity.getId(), itemEntity.getEntityData().getNonDefaultValues());
                    plr.connection.send(dataPacket);
                }
            }
        }
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
            if (HologramAPI.hasPlaceholderAPI()) {
                entity.setCustomName(Component.nullToEmpty(PlaceholderAPI.setPlaceholders(plr, line.getText())));
            }
            player.connection.send(spawnPacket);
            player.connection.send(dataPacket);
        }
    }

    @Override
    public void insertLine(Hologram hologram, HologramLine line, int index) {
        List<HologramLine> lines = hologram.getLines();
        lines.add(index, line);

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

    /*
    * Disclaimer: This will only work if PlaceholderAPI is installed.
     */
    @Override
    public boolean checkPlaceholder(Hologram hologram, int index) {
        HologramLine line = hologram.getLines().get(index);
        return PlaceholderAPI.containsPlaceholders(line.getText());
    }

    @Override
    public boolean checkPlaceholder(Hologram hologram, HologramLine line) {
        return PlaceholderAPI.containsPlaceholders(line.getText());
    }

    @Override
    public void replacePlaceholder(Hologram hologram, int index) {
        HologramLine line = hologram.getLines().get(index);
        String text = line.getText();
        for (Player player : hologram.getLocation().getWorld().getPlayers()) {
            String newText = PlaceholderAPI.setPlaceholders(player, text);
            line.setText(newText);
            updateTextFor(line, newText, player);
        }
    }

    @Override
    public void replacePlaceholder(Hologram hologram, int index, String text) {
        HologramLine line = hologram.getLines().get(index);
        line.setText(text);
        for (Player player : hologram.getLocation().getWorld().getPlayers()) {
            updateTextFor(line, text, player);
        }
    }

    @Override
    public void replacePlaceholder(Hologram hologram, HologramLine line) {
        String text = line.getText();
        for (Player player : hologram.getLocation().getWorld().getPlayers()) {
            String newText = PlaceholderAPI.setPlaceholders(player, text);
            line.setText(newText);
            updateTextFor(line, newText, player);
        }
    }

    @Override
    public void replaceAllPlaceholders(Hologram hologram) {
        List<HologramLine> lines = hologram.getLines();
        for (HologramLine line : lines) {
            if (checkPlaceholder(hologram, line)) {
                String text = line.getText();
                for (Player player : hologram.getLocation().getWorld().getPlayers()) {
                    String newText = PlaceholderAPI.setPlaceholders(player, text);
                    line.setText(newText);
                    updateTextFor(line, newText, player);
                }
            }
        }
    }

    @Override
    public void replaceAllPlaceholdersInWorld(World world) {
        List<Hologram> holos = holograms.get(world);
        List<Player> players = world.getPlayers();
        if (holos == null) return;
        for (Hologram hologram : holos) {
            List<HologramLine> lines = hologram.getLines();
            for (HologramLine line : lines) {
                if (checkPlaceholder(hologram, line)) {
                    String text = line.getText();
                    for (Player player : players) {
                        String newText = PlaceholderAPI.setPlaceholders(player, text);
                        line.setText(newText);
                        updateTextFor(line, newText, player);
                    }
                }
            }
        }
    }
}
