package me.outspending.core.elements.classes;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import me.outspending.core.NMSHologramLine;
import org.jetbrains.annotations.Nullable;

public class ClassHologramLine {

    static {
        Classes.registerClass(new ClassInfo<>(NMSHologramLine.class, "hologramline")
                .user("hologramline")
                .name("hologramline")
                .description("A line of a hologram created by the addon SKHologram")
                .parser(new Parser<NMSHologramLine>() {

                    @Override
                    public @Nullable NMSHologramLine parse(String s, ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return true;
                    }

                    @Override
                    public String toString(NMSHologramLine nmsHologramLine, int i) {
                        return toVariableNameString(nmsHologramLine);
                    }

                    @Override
                    public String toVariableNameString(NMSHologramLine nmsHologramLine) {
                        return "NMSHologramLine{" + nmsHologramLine.getHologram().getName() + ", " + nmsHologramLine.getText() + "}";
                    }
                }));
    }
}
