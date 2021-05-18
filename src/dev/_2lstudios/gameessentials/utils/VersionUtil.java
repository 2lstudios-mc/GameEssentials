package dev._2lstudios.gameessentials.utils;

import org.bukkit.Bukkit;

public class VersionUtil {
    private static boolean oneDotNine;

    public static void init() {
        VersionUtil.oneDotNine = (!Bukkit.getServer().getVersion().contains("1.8")
                && !Bukkit.getServer().getVersion().contains("1.7"));
    }

    public static boolean isOneDotNine() {
        return VersionUtil.oneDotNine;
    }
}
