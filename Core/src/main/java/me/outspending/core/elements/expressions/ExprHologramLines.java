package me.outspending.core.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.outspending.core.NMSHologram;
import me.outspending.core.NMSHologramLine;
import org.bukkit.event.Event;
import org.hologramsk.HologramLine;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Name("Hologram Lines")
@Description("Gets the lines of a hologram")
@Examples("set {_lines::*} to lines of {_hologram}")
@Since("1.0")
public class ExprHologramLines extends SimpleExpression<NMSHologramLine> {

    static {
        Skript.registerExpression(ExprHologramLines.class, NMSHologramLine.class, ExpressionType.SIMPLE, "lines of %hologram%");
    }

    private Expression<NMSHologram> hologram;

    @Override
    protected @Nullable NMSHologramLine[] get(Event event) {
        NMSHologram hologram1 = hologram.getSingle(event);
        assert hologram1 != null;
        NMSHologramLine[] lines = new NMSHologramLine[0];
        for (int i = 0; i < hologram1.getLineCount(); i++) {
            lines[i] = (NMSHologramLine) hologram1.getLines().get(i);
        }
        return lines;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends NMSHologramLine> getReturnType() {
        return NMSHologramLine.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        hologram = (Expression<NMSHologram>) expressions[0];
        return true;
    }
}
