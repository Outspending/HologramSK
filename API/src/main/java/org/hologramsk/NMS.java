package org.hologramsk;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface NMS {

    void createHologram(String name, Location location);

    void addHologramLine(Hologram hologram, HologramLine line);

    void addHologramLine(Hologram hologram, String name);

    void removeHologramLine(Hologram hologram, int index);

    void displayHologram(Hologram hologram, Player player);

    void hideHologram(Hologram hologram, Player player);

    void deleteHologram(Hologram hologram);

    void updateHologram(Hologram hologram);

    void updateHologramLine(Hologram hologram, HologramLine line);

    void insertLine(Hologram hologram, HologramLine line, int index);

    void rearrangeLines(Hologram hologram);
}
