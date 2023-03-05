package me.outspending.core.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.outspending.core.NMSHologram;
import me.outspending.core.SkriptData;
import me.outspending.core.elements.sections.EffSecCreateHologram;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprLastHologram extends SimpleExpression<NMSHologram> {

    static {
        Skript.registerExpression(ExprLastHologram.class, NMSHologram.class, ExpressionType.SIMPLE, "[the] hologram");
    }

    private boolean interception;

    @Override
    protected @Nullable NMSHologram[] get(Event event) {
        SkriptData data = new SkriptData();
        return new NMSHologram[]{data.getLastMadeHologram()};
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
        interception = getParser().isCurrentSection(EffSecCreateHologram.class);
        if (!interception) {
            Skript.error("The expression 'hologram' can only be used in the 'create hologram' section");
            return false;
        }
        return true;
    }
}
