package org.hologramsk;

import org.bukkit.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface HologramData {

    Map<World, List<Hologram>> holograms = new HashMap<>();
}
