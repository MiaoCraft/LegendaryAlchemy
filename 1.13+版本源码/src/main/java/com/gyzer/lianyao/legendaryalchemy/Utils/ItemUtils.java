package com.gyzer.lianyao.legendaryalchemy.Utils;

import com.gyzer.lianyao.legendaryalchemy.command.PluginCommand;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class ItemUtils {
    public static ItemStack getSaveItem(String id)
    {
        File file=new File("./plugins/LegendaryAlchemy","Items.yml");
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
        return yml.getItemStack(id);

    }

    public static void saveItem(String id,ItemStack i)
    {
        File file=new File("./plugins/LegendaryAlchemy","Items.yml");
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
        yml.set(id,i);
        try {
            yml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean hasItem(String id)
    {
        File file=new File("./plugins/LegendaryAlchemy","Items.yml");
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
        return yml.contains(id);
    }

    public static Material getMaterial(String id)
    {
      return Material.getMaterial(id);
    }
}
