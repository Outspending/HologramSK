package org.hologramsk;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public interface NMS {

    /*
    * For NMSHolograms
    * V0.0.1
     */
    void createHologram(String name, Location location);

    void moveHologram(Hologram hologram, Location location);

    void addHologramLine(Hologram hologram, HologramLine line);

    void addHologramLine(Hologram hologram, String name);

    void removeHologramLine(Hologram hologram, int index);

    void displayHologram(Hologram hologram, Player player);

    void hideHologram(Hologram hologram, Player player);

    void deleteHologram(Hologram hologram);

    void updateHologram(Hologram hologram);

    void updateHologramLine(Hologram hologram, HologramLine line);

    void insertLine(Hologram hologram, HologramLine line, int index);

    void update(Hologram hologram);

    void setLines(String... lines);

    List<HologramLine> getLines();

    /*
    * For NMSHologramLine
    * V0.0.1
     */

    void showTo(HologramLine hologramLine, Player player);

    void hideFrom(HologramLine hologramLine, Player player);

    void teleportLine(HologramLine hologramLine, Location location);

    void setText(HologramLine hologramLine, String text);

    void updateTextFor(HologramLine hologramLine, String text, Player player);

    void updateLocationFor(HologramLine hologramLine, Location location, Player player);

    void updateLine(HologramLine hologramLine);

    void updateHologramsInWorld(World world);

    LivingEntity spawnArmorStand(Location location, String name);
    LivingEntity spawnArmorStand(Location location);
}
