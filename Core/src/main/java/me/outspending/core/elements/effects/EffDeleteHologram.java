package me.outspending.core.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.outspending.core.NMSHologram;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffDeleteHologram extends Effect {

    static {
        Skript.registerEffect(EffDeleteHologram.class, "delete [the] [holo[gram]] %hologram%");
    }

    private Expression<NMSHologram> hologram;

    @Override
    protected void execute(Event event) {
        NMSHologram hologram = this.hologram.getSingle(event);
        if (hologram != null) {
            hologram.delete();
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        hologram = (Expression<NMSHologram>) expressions[0];
        return true;
    }
}
