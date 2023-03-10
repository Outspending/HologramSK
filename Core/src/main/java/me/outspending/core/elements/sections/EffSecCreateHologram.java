package me.outspending.core.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.EffectSection;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import me.outspending.core.NMSHologram;
import me.outspending.core.SkriptData;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Name("Create Hologram")
@Description("Creates a hologram at a location")
@Examples("create holo \"test\" with lines \"This is a test!\" and \"This is another test!\" at location of player")
@Since("1.0")
public class EffSecCreateHologram extends EffectSection {

    static {
        Skript.registerSection(EffSecCreateHologram.class, "create [a] holo[gram] (called|named|with id) %string% [with line[s] %-strings%] at [location] %location% [and save]");
    }

    private Expression<String> name;
    private Expression<String> lines;
    private Expression<Location> location;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult, @Nullable SectionNode sectionNode, @Nullable List<TriggerItem> list) {
        name = (Expression<String>) expressions[0];
        if (expressions[1] != null) {
            lines = (Expression<String>) expressions[1];
        }
        location = (Expression<Location>) expressions[2];
        return true;
    }

    @Override
    protected @Nullable TriggerItem walk(Event event) {
        String name = this.name.getSingle(event);
        Location location = this.location.getSingle(event);
        if (this.lines != null) {
            String[] lines = this.lines.getArray(event);
            if (lines.length > 0) {
                NMSHologram hologram = new NMSHologram(name, lines[0], location);
                for (int i = 1; i < lines.length; i++) {
                    hologram.addLine(lines[i]);
                }
                SkriptData.setLastMadeHologram(hologram);
                return super.walk(event, false);
            }
        }
        SkriptData.setLastMadeHologram(new NMSHologram(name, "This is placeholder text!", location));
        return super.walk(event, true);
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return null;
    }
}
