package me.outspending.core.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.outspending.core.NMSHologram;
import me.outspending.core.misc.HologramCreateEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvnCreateHologram extends SkriptEvent {

    static {
        Skript.registerEvent("Create Hologram", EvnCreateHologram.class, HologramCreateEvent.class, "create [of] holo[gram]");

        EventValues.registerEventValue(HologramCreateEvent.class, NMSHologram.class, new Getter<NMSHologram, HologramCreateEvent>() {
            @Override
            public @Nullable NMSHologram get(HologramCreateEvent hologramCreateEvent) {
                return hologramCreateEvent.getHologram();
            }
        }, 0);
    }

    @Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
        return false;
    }

    @Override
    public boolean check(Event event) {
        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return null;
    }
}
