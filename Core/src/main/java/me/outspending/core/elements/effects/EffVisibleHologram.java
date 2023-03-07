package me.outspending.core.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.outspending.core.Core;
import me.outspending.core.NMSHologram;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.hologramsk.NMS;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class EffVisibleHologram extends Effect {

    static {
        Skript.registerEffect(EffVisibleHologram.class,
                "(1¦show|hide) holo[gram] %hologram% (from|to) %players%");
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

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        hologram = (Expression<NMSHologram>) expressions[0];
        players = (Expression<Player>) expressions[1];
        if (parseResult.mark == 1) {
            show = true;
        }
        return true;
    }
}
