package com.gyzer.lianyao.legendaryalchemy.alchemy;

import com.gyzer.lianyao.legendaryalchemy.Utils.FileUtils;
import com.gyzer.lianyao.legendaryalchemy.Utils.MsgUtils;
import com.gyzer.lianyao.legendaryalchemy.Utils.ReadMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AlchemyData {
    public static HashMap<String,String> PaperCategorize=new HashMap<>();
    public static HashMap<String,String> PaperItemId=new HashMap<>();
    public static HashMap<String,String> success_itemid=new HashMap<>();
    public static HashMap<String,List<String>> PaperIdList=new HashMap<>();

    public static HashMap<String ,Double> PaperChance=new HashMap<>();
    public static HashMap<String, List<String>> PaperRequire=new HashMap<>();
    public static HashMap<String ,Double> success_exp=new HashMap<>();
    public static HashMap<String ,Integer> needLevel=new HashMap<>();
    public static HashMap<String ,Integer> maxhot=new HashMap<>();
    public static HashMap<String ,Integer> maxtime=new HashMap<>();
    public static HashMap<String ,Double> fail_exp=new HashMap<>();
    public static HashMap<String ,List<String>> success_run=new HashMap<>();
    public static HashMap<String ,List<String>> fail_run=new HashMap<>();
    public static HashMap<String ,Boolean> fail_back=new HashMap<>();
    public static HashMap<String, Boolean> fail_remove=new HashMap<String, Boolean>();
    public static void load()
    {
        int paperAmount=0;
        for (String cat: FileUtils.getCategorizeList())
        {
            Bukkit.getConsoleSender().sendMessage(MsgUtils.msg("&6LegendaryAlchemy &f>> &a加载分类 &b"+cat.replace(".yml","")));
            File file=new File("./plugins/LegendaryAlchemy/Categorize",cat);
            YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);

            if (yml.contains("Papers") && yml.getConfigurationSection("Papers")!=null)
            {
                List<String> list=new ArrayList<>();
                ConfigurationSection sec=yml.getConfigurationSection("Papers");
                for (String id:sec.getKeys(false))
                {
                    list.add(id);
                    PaperCategorize.put(id,cat.replace(".yml",""));
                    PaperItemId.put(id,yml.getString("Papers."+id+".paperItem"));
                    PaperChance.put(id, yml.getDouble("Papers."+id+".chance"));
                    PaperRequire.put(id,yml.getStringList("Papers."+id+".need"));
                    success_exp.put(id, yml.getDouble("Papers."+id+".success.exp"));
                    fail_exp.put(id, yml.getDouble("Papers."+id+".fail.exp"));
                    success_run.put(id,yml.getStringList("Papers."+id+".success.run"));
                    fail_run.put(id,yml.getStringList("Papers."+id+".fail.run"));
                    fail_back.put(id,yml.getBoolean("Papers."+id+".fail.backMaterial"));
                    fail_remove.put(id, yml.getBoolean("Papers."+id+".fail.removeRandom"));
                    maxhot.put(id, yml.getInt("Papers."+id+".maxhot",10));
                    maxtime.put(id, yml.getInt("Papers."+id+".time",20));
                    needLevel.put(id, yml.getInt("Papers."+id+".level",0));
                    success_itemid.put(id, yml.getString("Papers."+id+".item"));
                    paperAmount++;
                }
                PaperIdList.put(cat.replace(".yml",""),list);
            }
        }
        Bukkit.getConsoleSender().sendMessage(MsgUtils.msg("&6LegendaryAlchemy &f>> &a共加载丹药配方 &b"+paperAmount+"&a 个"));
    }


    public static boolean canSuccess(Player p,String paperId)
    {
        List<ItemStack> litem=AlchemyBlock.putListItem.get(p);
        List<Integer> lhot=AlchemyBlock.putListHot.get(p);
        List<String> need=AlchemyData.PaperRequire.get(paperId);
         List<String> l=new ArrayList<>();
        int in=0;
        for (ItemStack i:litem)
        {
             if (i.hasItemMeta() && i.getItemMeta().hasDisplayName())
            {
                 l.add("n="+i.getItemMeta().getDisplayName().replace("§","&")+";a="+i.getAmount()+";h="+lhot.get(in));
            }
            else {
                l.add("t="+i.getType().name()+";d="+i.getData().getData()+";a="+i.getAmount()+";h="+lhot.get(in));
             }
            in++;
        }
        boolean kf=true;
        in=0;
         for (String require:need)
        {
            if (!require.equals(l.get(in)))
            {
                kf=false;
            }
            in++;
        }
        if (kf)
        {
            if ((new Random()).nextInt(101) > AlchemyData.PaperChance.get(paperId)*100)
            {
                p.sendMessage(ReadMessage.PluginTitle+ReadMessage.begin_fail_unluck);
                return false;
            }
            else {
                return true;
            }
        }
        return false;
    }
}
