package me.outspending.core.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.outspending.core.NMSHologram;
import me.outspending.core.misc.HologramCreateEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Create Hologram Event")
@Description("Called when a hologram is created")
@Examples("on hologram create:")
@Since("1.0")
public class EvnCreateHologram extends SkriptEvent {

    static {
        Skript.registerEvent("Create Hologram", EvnCreateHologram.class, HologramCreateEvent.class, "holo[gram] create[d]");

        EventValues.registerEventValue(HologramCreateEvent.class, NMSHologram.class, new Getter<NMSHologram, HologramCreateEvent>() {
            @Override
            public @Nullable NMSHologram get(HologramCreateEvent hologramCreateEvent) {
                return hologramCreateEvent.getHologram();
            }
        }, 0);
    }

    @Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return null;
    }
}
