package me.outspending.core;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.hologramsk.Hologram;
import org.hologramsk.HologramLine;
import org.hologramsk.NMS;

public class NMSHologramLine implements HologramLine {

    private String text;
    private final NMSHologram hologram;
    private Location location;
    private int index;
    private LivingEntity armorStand;

    public NMSHologramLine(String text, NMSHologram hologram) {
        NMS nms = Core.getNMSVersion();

        this.text = text;
        this.hologram = hologram;
        this.index = hologram.getLineCount();
        this.location = hologram.getLocation().clone().add(0, index * hologram.getLineHeight(), 0);
        this.armorStand = nms.spawnArmorStand(location, text);

        nms.updateHologramLine(hologram, this);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Hologram getHologram() {
        return hologram;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public LivingEntity getArmorStand() {
        return armorStand;
    }

    @Override
    public void showTo(Player player) {

    }

    @Override
    public void showToAll(Player player) {

    }

    @Override
    public void hideFrom(Player player) {

    }

    @Override
    public void teleport(Location location) {

    }

    @Override
    public void setText(String text) {

    }

    @Override
    public void updateTextFor(Player player) {

    }

    @Override
    public void updateLocationFor(Player player) {

    }
}
