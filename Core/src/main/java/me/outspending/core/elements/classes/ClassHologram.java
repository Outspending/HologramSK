package me.outspending.core.elements.classes;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import me.outspending.core.NMSHologram;
import me.outspending.core.SkriptData;
import org.jetbrains.annotations.Nullable;

public class ClassHologram {

    static {
        Classes.registerClass(new ClassInfo<>(NMSHologram.class, "hologram")
                .user("hologram")
                .name("hologram")
                .description("A hologram created by the addon SKHologram")
                .parser(new Parser<NMSHologram>() {

                    @Override
                    public @Nullable NMSHologram parse(String s, ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return true;
                    }

                    @Override
                    public String toString(NMSHologram nmsHologram, int i) {
                        return toVariableNameString(nmsHologram);
                    }

                    @Override
                    public String toVariableNameString(NMSHologram nmsHologram) {
                        return "NMSHologram{" + nmsHologram.getName() + "}";
                    }
                })
                .changer(SkriptData.SOMETHING_IDK));
    }
}
