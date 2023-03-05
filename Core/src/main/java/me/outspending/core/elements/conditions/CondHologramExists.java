package me.outspending.core.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.hologramsk.Hologram;
import org.hologramsk.HologramData;
import org.jetbrains.annotations.Nullable;

public class CondHologramExists extends Condition implements HologramData {

    static {
        Skript.registerCondition(CondHologramExists.class, "holo[gram] %string% exists");
    }

    private Expression<String> name;

    @Override
    public boolean check(Event event) {
        String name = this.name.getSingle(event);
        if (hologramsByName.get(name) == null) return false;
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) expressions[0];
        return true;
    }
}
