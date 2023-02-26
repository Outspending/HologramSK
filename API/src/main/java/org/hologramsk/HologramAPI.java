package org.hologramsk;

public class HologramAPI {

    public static boolean hasPlaceholderAPI = false;

    public static void setPlaceholderAPI(boolean hasPlaceholderAPI) {
        HologramAPI.hasPlaceholderAPI = hasPlaceholderAPI;
    }

    public static boolean hasPlaceholderAPI() {
        return hasPlaceholderAPI;
    }
}
