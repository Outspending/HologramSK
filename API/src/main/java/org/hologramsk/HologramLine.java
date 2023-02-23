package org.hologramsk;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface HologramLine {

    String getText();

    Hologram getHologram();

    Location getLocation();

    LivingEntity getArmorStand();

    void showTo(Player player);

    void showToAll(Player player);

    void hideFrom(Player player);

    void teleport(Location location);

    void setText(String text);

    void updateTextFor(Player player);

    void updateLocationFor(Player player);

}
