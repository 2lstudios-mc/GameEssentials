package dev._2lstudios.gameessentials.utils;

import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.CopyOption;
import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigurationUtil {
    private final Plugin plugin;

    public ConfigurationUtil(final Plugin plugin) {
        this.plugin = plugin;
    }

    public YamlConfiguration getConfiguration(final String filePath) {
        final File dataFolder = this.plugin.getDataFolder();
        final File file = new File(filePath.replace("%datafolder%", dataFolder.toPath().toString()));
        if (file.exists()) {
            return YamlConfiguration.loadConfiguration(file);
        }
        return new YamlConfiguration();
    }

    public void createConfiguration(String file) {
        try {
            final File dataFolder = this.plugin.getDataFolder();
            file = file.replace("%datafolder%", dataFolder.toPath().toString());
            final File configFile = new File(file);
            if (!configFile.exists()) {
                final String[] files = file.split("/");
                final InputStream inputStream = this.plugin.getClass().getClassLoader()
                        .getResourceAsStream(files[files.length - 1]);
                final File parentFile = configFile.getParentFile();
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
                if (inputStream != null) {
                    Files.copy(inputStream, configFile.toPath(), new CopyOption[0]);
                    System.out.println(("[%pluginname%] File " + configFile + " has been created!")
                            .replace("%pluginname%", this.plugin.getDescription().getName()));
                } else {
                    configFile.createNewFile();
                }
            }
        } catch (IOException e) {
            System.out.println("[%pluginname%] Unable to create configuration file!".replace("%pluginname%",
                    this.plugin.getDescription().getName()));
        }
    }

    public void saveConfiguration(final YamlConfiguration yamlConfiguration, final String file) {
        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try {
                yamlConfiguration.save(file.replace("%datafolder%", this.plugin.getDataFolder().toPath().toString()));
            } catch (IOException e) {
                System.out.println("[%pluginname%] Unable to save configuration file!".replace("%pluginname%",
                        this.plugin.getDescription().getName()));
            }
        });
    }

    public void deleteConfiguration(final String fileName) {
        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
            final File file = new File(
                    fileName.replace("%datafolder%", this.plugin.getDataFolder().toPath().toString()));
            if (file.exists()) {
                file.delete();
            }
        });
    }

    public void saveConfigurationSync(final YamlConfiguration yamlConfiguration, final String file) {
        try {
            final File dataFolder = this.plugin.getDataFolder();
            yamlConfiguration.save(file.replace("%datafolder%", dataFolder.toPath().toString()));
        } catch (IOException e) {
            System.out.println("[%pluginname%] Unable to save configuration file!".replace("%pluginname%",
                    this.plugin.getDescription().getName()));
        }
    }
}
