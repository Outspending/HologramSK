package me.outspending.core.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.outspending.core.NMSHologram;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

@Name("Can See Hologram")
@Description("Checks if a player can see a hologram")
@Since("1.0")
public class CondCanSee extends Condition {

    static {
        Skript.registerCondition(CondCanSee.class, "%player% can (see|view) holo[gram] %hologram%", "%player% can('t| not) (see|view) holo[gram] %hologram%");
    }

    private Expression<Player> player;
    private Expression<NMSHologram> hologram;
    private boolean canSee = true;

    @Override
    public boolean check(Event event) {
        NMSHologram hologram1 = hologram.getSingle(event);
        Player player1 = player.getSingle(event);
        List<UUID> hiddenPlayers = hologram1.getHiddenPlayers();
        return canSee ? hiddenPlayers.contains(player1.getUniqueId()) : !hiddenPlayers.contains(player1.getUniqueId());
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) expressions[0];
        hologram = (Expression<NMSHologram>) expressions[1];
        canSee = i != 0;
        return true;
    }
}
