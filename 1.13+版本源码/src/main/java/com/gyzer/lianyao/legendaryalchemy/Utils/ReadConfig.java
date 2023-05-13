package com.gyzer.lianyao.legendaryalchemy.Utils;

import com.gyzer.lianyao.legendaryalchemy.LegendaryAlchemy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ReadConfig {


    public static boolean useMysql;
    public static int maxLevel;
    public static String hot_pre;
    public static String hot_arg;
    public static String hot_color1;
    public static String hot_color2;
    public static HashMap<Integer,Double> need=new HashMap<>();
    public static String sound_fail;
    public static String sound_success;
    public static String sound_start;
    public static String sound_put;
    public static String hologram;

    public static void reloadConfig()
    {
        LegendaryAlchemy.LegendaryAlchemy.reloadConfig();
        FileConfiguration yml= LegendaryAlchemy.LegendaryAlchemy.getConfig();
        hologram = yml.getString("Set.Hologram.use","HolographicDisplays");
        useMysql = yml.getBoolean("Store.mysql.enable");
        maxLevel = yml.getInt("Set.level.maxlevel");
        for (int x=0;x<maxLevel;x++)
        {
            need.put(x,yml.getDouble("Set.level.require."+x));
        }
        hot_pre = MsgUtils.msg(yml.getString("Set.hot.pre"));
        hot_arg = MsgUtils.msg(yml.getString("Set.hot.arg"));
        hot_color1 = MsgUtils.msg(yml.getString("Set.hot.color1"));
        hot_color2 = MsgUtils.msg(yml.getString("Set.hot.color2"));
        sound_fail = getString("Set.sound.fail","ENTITY_ITEM_BREAK");
        sound_success = getString("Set.sound.success","ENTITY_FIREWORK_LAUNCH");
        sound_start = getString("Set.sound.start","BLOCK_NOTE_BLOCK_BELL");
        sound_put = getString("Set.sound.put","ITEM_BOTTLE_FILL");

    }

    public static String getString(String path,String def)
    {
        File file=new File("./plugins/LegendaryAlchemy","config.yml");
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
        if (yml.contains(path))
        {
            return yml.getString(path);
        }
        else {
            yml.set(path,def);
            try {
                yml.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return def;
        }
    }
}
