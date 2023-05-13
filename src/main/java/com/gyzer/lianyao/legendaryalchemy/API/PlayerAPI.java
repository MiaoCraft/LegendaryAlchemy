package com.gyzer.lianyao.legendaryalchemy.API;

import com.gyzer.lianyao.legendaryalchemy.LegendaryAlchemy;
import com.gyzer.lianyao.legendaryalchemy.Mysql.MySQLManager;
import com.gyzer.lianyao.legendaryalchemy.Utils.FileUtils;
import com.gyzer.lianyao.legendaryalchemy.Utils.ItemUtils;
import com.gyzer.lianyao.legendaryalchemy.Utils.ReadConfig;
import com.gyzer.lianyao.legendaryalchemy.Utils.ReadMessage;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PlayerAPI {

    public static int getLevel(String player)
    {
        if (ReadConfig.useMysql)
        {
            return MySQLManager.get().findLevel(player, Bukkit.getConsoleSender());
        }
        return FileUtils.getPlayerData(player).getInt("level");
    }

    public static double getExp(String player)
    {
        if (ReadConfig.useMysql)
        {
            return MySQLManager.get().findExp(player,Bukkit.getConsoleSender());
        }
        return FileUtils.getPlayerData(player).getDouble("exp");
    }

    public static List<String> getUnlocks(String player)
    {


        if (ReadConfig.useMysql)
        {
            return MySQLManager.get().getList(player);
        }
        return FileUtils.getPlayerData(player).getStringList("unlock");
    }


    public static void addExp(String player,double amount)
    {
        double old=getExp(player);
        int level=getLevel(player);
        int addLevel=0;
        double add=old+amount;
        if (old + amount >= ReadConfig.need.get(level))
        {

            if (level==ReadConfig.maxLevel)
            {
                return;
            }
            addLevel=1;
            add-=ReadConfig.need.get(level);
            if (level+1 < ReadConfig.maxLevel) {
                for (int x = (level+1); x < ReadConfig.maxLevel; x++) {
                    if (ReadConfig.need.get(x) <= add)
                    {
                        add-=ReadConfig.need.get(x);
                        addLevel++;
                    }
                    else {
                        break;
                    }
                }
            }
        }
        if (ReadConfig.useMysql)
        {
            if (Bukkit.getPlayer(player)!=null)
            {
                Player p=Bukkit.getPlayer(player);
                p.sendMessage(ReadMessage.PluginTitle+ReadMessage.gainExp.replace("%amount%",""+amount));
                if (addLevel > 0)
                {
                    p.sendMessage(ReadMessage.PluginTitle+ReadMessage.gainLevel.replace("%level%",""+addLevel).replace("%now%",""+(level+addLevel)));

                }
            }
            MySQLManager.get().setExp(player,add);

            if (addLevel > 0) {
                MySQLManager.get().setLevel(player, (addLevel + level));
            }
        }
        else {
            if (Bukkit.getPlayer(player)!=null)
            {
                Player p=Bukkit.getPlayer(player);
                p.sendMessage(ReadMessage.PluginTitle+ReadMessage.gainExp.replace("%amount%",""+amount));
                if (addLevel > 0) {
                    p.sendMessage(ReadMessage.PluginTitle+ReadMessage.gainLevel.replace("%level%",""+addLevel).replace("%now%",""+(level+addLevel)));
                }
            }
            File file=new File("./plugins/LegendaryAlchemy/Data",player+".yml");
            YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
            yml.set("exp",add);
            if (addLevel > 0) {
                yml.set("level", (level + addLevel));

            }
            try {
                yml.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void addLevel(String player,int amount)
    {
        int level=getLevel(player);
        int add=amount;
        if (Bukkit.getPlayer(player)!=null)
        {
            if (level + amount >= ReadConfig.maxLevel)
            {
                add=ReadConfig.maxLevel-level;
            }
            Player p = Bukkit.getPlayer(player);
            p.sendMessage(ReadMessage.PluginTitle+ReadMessage.gainLevel.replace("%level%",""+add).replace("%now%",""+(level+add)));
        }
        if (ReadConfig.useMysql)
        {
            MySQLManager.get().setLevel(player,(level+add));
        }
        else {
            File file=new File("./plugins/LegendaryAlchemy/Data",player+".yml");
            YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
            yml.set("level",(level+add));
            try {
                yml.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean hasUnLockPaper(String player,String paperId)
    {
        if (getUnlocks(player).contains(paperId))
        {
            return true;
        }
        return false;
    }

    public static void UnLockPaper(String player,String paperId)
    {
        if (hasUnLockPaper(player,paperId))
        {
            if (Bukkit.getPlayer(player)!=null)
            {
                Bukkit.getPlayer(player).sendMessage(ReadMessage.PluginTitle+ReadMessage.alreadyUnlock);
            }
            return;
        }
        if (ReadConfig.useMysql)
        {
            if (Bukkit.getPlayer(player)!=null)
            {
                ItemStack i= ItemUtils.getSaveItem(AlchemyData.success_itemid.get(paperId));
                String name=i.getType().name();
                if (i.hasItemMeta()&&i.getItemMeta().hasDisplayName())
                {
                    name=i.getItemMeta().getDisplayName();
                }
                Bukkit.getPlayer(player).sendMessage(ReadMessage.PluginTitle+ReadMessage.unlock.replace("%name%",name));
            }
            MySQLManager.get().addData(player,paperId);
        }
        else {
            if (Bukkit.getPlayer(player)!=null)
            {
                ItemStack i= ItemUtils.getSaveItem(AlchemyData.success_itemid.get(paperId));
                String name=i.getType().name();
                if (i.hasItemMeta()&&i.getItemMeta().hasDisplayName())
                {
                    name=i.getItemMeta().getDisplayName();
                }
                Bukkit.getPlayer(player).sendMessage(ReadMessage.PluginTitle+ReadMessage.unlock.replace("%name%",name));
            }
            File file=new File("./plugins/LegendaryAlchemy/Data",player+".yml");
            YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
            List<String> l=yml.getStringList("unlock");
            l.add(paperId);
            yml.set("unlock",l);
            try {
                yml.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
