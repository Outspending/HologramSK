package me.outspending.core.misc;

import me.outspending.core.Core;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.logging.Level;

public class UpdateChecker {

    public boolean isUpdateAvailable = false;
    public String latestVersion;

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(Core.getPlugin(), () -> {
            try (InputStream stream = new URL("N/A").openStream(); Scanner scanner = new Scanner(stream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException e) {
                Core.getPlugin().getLogger().log(Level.SEVERE, "Cannot look for updates", e.getMessage());
            }
        });
    }

    public boolean getIsUpdateAvailable() {
        return this.isUpdateAvailable;
    }

    public void setIsUpdateAvailable(boolean isUpdateAvailable) {
        this.isUpdateAvailable = isUpdateAvailable;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
    }
}
