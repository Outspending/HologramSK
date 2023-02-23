package org.hologramsk;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public interface Hologram {

    Location getLocation();

    List<HologramLine> getLines();

    String getName();

    float getLineHeight();

    void teleport(Location location);

    void showTo(Player player);

    void hideFrom(Player player);

    void addLine(HologramLine line);

    void addLine(String text);

    void removeLine(int index);

    void removeLine(HologramLine line);

    void update();

    void updateLine(HologramLine line);

    void insertLine(HologramLine line, int index);

    void rearrangeLines();

    int getLineCount();

    void delete();
}
