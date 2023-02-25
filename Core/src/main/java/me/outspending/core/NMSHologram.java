package me.outspending.core;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.hologramsk.Hologram;
import org.hologramsk.HologramData;
import org.hologramsk.HologramLine;
import org.hologramsk.NMS;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NMSHologram implements Hologram {

    private Location location;
    private List<HologramLine> lines = new ArrayList<>();
    private List<UUID> hiddenPlayers = new ArrayList<>();
    private String name;
    private float lineHeight = 0.25f;

    public NMSHologram(String name, Location location) {
        new NMSHologram(name, "This is a placeholder!", location);
    }

    public NMSHologram(String name, String text, Location location) {
        this.name = name;
        this.location = location;
        this.lines.add(new NMSHologramLine(text, this));
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public List<HologramLine> getLines() {
        return lines;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<UUID> getHiddenPlayers() {
        return this.hiddenPlayers;
    }

    @Override
    public float getLineHeight() {
        return lineHeight;
    }

    @Override
    public void teleport(Location location) {

    }

    @Override
    public void showTo(Player player) {

    }

    @Override
    public void hideFrom(Player player) {

    }

    @Override
    public void addLine(HologramLine line) {
        NMS nms = Core.getNMSVersion();
        nms.addHologramLine(this, line);
        this.addLine(line);
    }

    @Override
    public void addLine(String text) {
        NMS nms = Core.getNMSVersion();
        NMSHologramLine line = new NMSHologramLine(text, this);
        nms.addHologramLine(this, line);
        this.addLine(line);
    }

    @Override
    public void removeLine(int index) {

    }

    @Override
    public void removeLine(HologramLine line) {

    }

    @Override
    public void update() {

    }

    @Override
    public void updateLine(HologramLine line) {

    }

    @Override
    public void insertLine(HologramLine line, int index) {

    }

    @Override
    public void rearrangeLines() {

    }

    @Override
    public int getLineCount() {
        return lines.size();
    }

    @Override
    public void delete() {

    }
}
