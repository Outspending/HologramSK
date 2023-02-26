package me.outspending.core.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.outspending.core.Core;
import me.outspending.core.NMSHologram;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffMoveHologram extends Effect {

    static {
        Skript.registerEffect(EffMoveHologram.class, "move [the] holo[gram] %hologram% to %location%");
    }

    private Expression<NMSHologram> hologram;
    private Expression<Location> location;

    @Override
    protected void execute(Event event) {
        NMSHologram hologram = this.hologram.getSingle(event);
        Location location = this.location.getSingle(event);
        if (hologram != null && location != null) {
            Core.getNMSVersion().moveHologram(hologram, location);
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        hologram = (Expression<NMSHologram>) expressions[0];
        location = (Expression<Location>) expressions[1];
        return true;
    }
}
