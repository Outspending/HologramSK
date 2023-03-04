package me.outspending.core.misc;

import me.outspending.core.NMSHologram;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class HologramCreateEvent extends Event {

    private NMSHologram hologram;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public HologramCreateEvent(NMSHologram hologram) {
        this.hologram = hologram;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public NMSHologram getHologram() {
        return hologram;
    }
}
