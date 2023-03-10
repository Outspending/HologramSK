package me.outspending.core.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import jdk.jfr.Name;
import me.outspending.core.Core;
import me.outspending.core.NMSHologram;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.hologramsk.NMS;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

@Name("Hologram Visibility")
@Description("Shows or hides a hologram from a player")
@Examples({"hide hologram {_hologram} from player", "show hologram {_hologram} to player"})
@Since("1.0")
public class EffVisibleHologram extends Effect {

    static {
        Skript.registerEffect(EffVisibleHologram.class,
                "show holo[gram] %hologram% to %players%",
                "hide holo[gram] %hologram% from %players%");
    }

    private Expression<NMSHologram> hologram;
    private Expression<Player> players;
    private boolean show = false;

    @Override
    protected void execute(Event event) {
        NMSHologram hologram = this.hologram.getSingle(event);
        Player[] players = this.players.getArray(event);
        List<UUID> hiddenPlayers = hologram.getHiddenPlayers();
        final NMS nms = Core.getNMSVersion();
        for (int i = 0; i < players.length; i++) {
            final Player plr = players[i];
            if (show) {
                if (hiddenPlayers.contains(plr.getUniqueId())) {
                    nms.displayHologram(hologram, plr);
                    hiddenPlayers.remove(plr.getUniqueId());
                }
                continue;
            }
            if (!hiddenPlayers.contains(plr.getUniqueId())) {
                nms.hideHologram(hologram, plr);
                hiddenPlayers.add(plr.getUniqueId());
            }
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        hologram = (Expression<NMSHologram>) expressions[0];
        players = (Expression<Player>) expressions[1];
        show = i == 1;
        return true;
    }
}
