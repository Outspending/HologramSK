package me.outspending.core.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.outspending.core.NMSHologram;
import org.bukkit.event.Event;
import org.hologramsk.Hologram;
import org.hologramsk.HologramData;
import org.jetbrains.annotations.Nullable;

public class ExprGetHologram extends SimpleExpression<NMSHologram> implements HologramData {

    static {
        Skript.registerExpression(ExprGetHologram.class, NMSHologram.class, ExpressionType.SIMPLE, "holo[gram] [(named|called)] %string%");
    }

    private Expression<String> name;

    @Override
    protected @Nullable NMSHologram[] get(Event event) {
        NMSHologram hologram = (NMSHologram) hologramsByName.get(name.getSingle(event));
        if (hologram == null) return null;
        return new NMSHologram[]{hologram};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends NMSHologram> getReturnType() {
        return NMSHologram.class;
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
