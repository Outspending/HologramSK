package me.outspending.core;

import ch.njol.skript.classes.Changer;
import ch.njol.util.coll.CollectionUtils;
import org.jetbrains.annotations.Nullable;

public class SkriptData {

    public static NMSHologram lastMadeHologram;

    public static NMSHologram getLastMadeHologram() {
        return lastMadeHologram;
    }

    public static void setLastMadeHologram(NMSHologram hologram) {
        lastMadeHologram = hologram;
    }

    public static final Changer<NMSHologram> HOLOGRAM_CHANGER = new Changer<NMSHologram>() {

        @Override
        public @Nullable Class<?>[] acceptChange(ChangeMode changeMode) {
            return switch (changeMode) {
                case DELETE, ADD -> CollectionUtils.array(String.class);
                default -> null;
            };
        }

        @Override
        public void change(NMSHologram[] nmsHolograms, @Nullable Object[] objects, ChangeMode changeMode) {
            if (changeMode == ChangeMode.DELETE) {
                for (NMSHologram hologram : nmsHolograms) {
                    hologram.delete();
                }
            } else if (changeMode == ChangeMode.ADD) {
                for (NMSHologram hologram : nmsHolograms) {
                    Core.getNMSVersion().addHologramLine(hologram, new NMSHologramLine((String) objects[0], hologram));
                }
            }
        }
    };
}
