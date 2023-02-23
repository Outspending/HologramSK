package org.hologramsk;

import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

public interface HologramData {

    Map<World, Hologram> holograms = new HashMap<>();
}
