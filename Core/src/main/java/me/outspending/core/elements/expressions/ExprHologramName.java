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
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Hologram Name")
@Description("Gets the name of a hologram")
@Examples("set {_name} to name of {_hologram}")
@Since("1.0")
public class ExprHologramName extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprHologramName.class, String.class, ExpressionType.SIMPLE, "name of %hologram%");
    }

    private Expression<NMSHologram> hologram;

    @Override
    protected @Nullable String[] get(Event event) {
        NMSHologram hologram1 = hologram.getSingle(event);
        return new String[]{hologram1.getName()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
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
