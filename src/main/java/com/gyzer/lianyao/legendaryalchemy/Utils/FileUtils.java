package com.gyzer.lianyao.legendaryalchemy.Utils;

import eu.decentsoftware.holograms.api.utils.scheduler.S;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static void addPotData(Location loc)
    {
        List<Location> list=getPotData();
        list.add(loc);
        setPotData(list);
    }
    public static void removePotData(Location loc)
    {
        List<Location> list=getPotData();
        list.remove(list.indexOf(loc));
        setPotData(list);
    }
    public static void setPotData(List<Location> l)
    {
        File file = new File("./plugins/LegendaryAlchemy","PotData.yml");
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
        ArrayList list=new ArrayList();
        for (Location loc:l)
        {
            list.add("!"+loc.getWorld().getName()+"@"+loc.getBlockX()+"#"+loc.getBlockY()+"$"+loc.getBlockZ());
        }
        yml.set("Locations",list);
        try {
            yml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Location> getPotData()
    {
        File file = new File("./plugins/LegendaryAlchemy","PotData.yml");
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
        List<Location> list=new ArrayList<>();
        if (yml.contains("Locations"))
        {
            if (yml.getStringList("Locations").size() > 0)
            {
                for (String l:yml.getStringList("Locations"))
                {
                    String world=l.substring(l.indexOf("!")+1,l.indexOf("@"));
                    int x=Integer.parseInt(l.substring(l.indexOf("@")+1,l.indexOf("#")));
                    int y=Integer.parseInt(l.substring(l.indexOf("#")+1,l.indexOf("$")));
                    int z=Integer.parseInt(l.substring(l.indexOf("$")+1));
                    list.add(new Location(Bukkit.getWorld(world),x,y,z));
                }
            }
        }
        return list;
    }
    public static List<String> getCategorizeList() {
        List<String> shops = new ArrayList<>();
        File file = new File("./plugins/LegendaryAlchemy/Categorize");
        if (!file.exists())
            return shops;
        File[] files = file.listFiles();
        byte b;
        int i;
        File[] arrayOfFile1;
        for (i = (arrayOfFile1 = files).length, b = 0; b < i; ) {
            File f = arrayOfFile1[b];
            if (!f.isDirectory())
                shops.add(f.getName());
            b++;
        }
        return shops;
    }

    public static YamlConfiguration getPlayerData(String player)
    {
        File file=new File("./plugins/LegendaryAlchemy/Data",player+".yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void createPlayerData(String player) {
        if (ReadConfig.useMysql) {
            return;
        }
        File file = new File("./plugins/LegendaryAlchemy/Data", player + ".yml");
        if (!file.exists()) {
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
            yml.set("level", 0);
            yml.set("exp", 0.0);
            List<String> l = new ArrayList<>();
            yml.set("unlock", l);
            try {
                yml.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 为玩家" + player + "插件数据文件");
        }
    }

}
