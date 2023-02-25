package me.outspending.core.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.outspending.core.NMSHologram;
import me.outspending.core.SkriptData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class EffCreateHologram extends Effect {

    static {
        Skript.registerEffect(EffCreateHologram.class, "create [a] holo[gram] (called|named) %string% [with line[s] %-strings%] at [location] %location% [and save]");
    }

    private Expression<String> hologramName;
    private Expression<String> hologramLines;
    private Expression<Location> hologramLocation;

    @Override
    protected void execute(Event event) {
        String name = hologramName.getSingle(event);
        Location location = hologramLocation.getSingle(event);
        NMSHologram hologram;
        if (hologramLines != null) {
            String[] lines = hologramLines.getArray(event);
            Bukkit.broadcastMessage(Arrays.toString(lines));
            hologram = new NMSHologram(name, lines[0], location);
            lines[0] = null;
            for (String line : lines) {
                hologram.addLine(line);
            }
        } else {
            hologram = new NMSHologram(name, location);
        }
        SkriptData.setLastMadeHologram(hologram);
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        hologramName = (Expression<String>) expressions[0];
        hologramLines = (Expression<String>) expressions[1];
        hologramLocation = (Expression<Location>) expressions[2];
        return true;
    }
}
