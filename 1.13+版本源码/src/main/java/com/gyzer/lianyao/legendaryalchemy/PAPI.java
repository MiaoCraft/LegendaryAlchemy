package com.gyzer.lianyao.legendaryalchemy;

import com.gyzer.lianyao.legendaryalchemy.API.PlayerAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class PAPI extends PlaceholderExpansion {
    private final LegendaryAlchemy plugin;

    @Override
    public String getAuthor() {
        return "Gyzer";
    }

    @Override
    public String getIdentifier() {
        return "LegendaryAlchemy";
    }

    @Override
    public String getVersion() {
        return "1.1.0";
    }
    public PAPI(LegendaryAlchemy plugin) {
        this.plugin = plugin;
    }
    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("exp")) {
            return player == null ? "0.0" : PlayerAPI.getExp(player.getName())+""; // "name" requires the player to be valid
        }

        if(params.equalsIgnoreCase("level")) {
            return player == null ? "0" : PlayerAPI.getLevel(player.getName())+""; // "name" requires the player to be valid
        }
        return "null"; // Placeholder is unknown by the Expansion
    }
}
