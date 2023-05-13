package com.gyzer.lianyao.legendaryalchemy.Utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuUtils {

    public static String main_title;
    public static int main_size;
    public static HashMap<String, ItemStack> main_items=new HashMap<>();
    public static HashMap<String, List<Integer>> main_slot=new HashMap<>();
    public static HashMap<Integer,String> main_categorizeSlot=new HashMap<>();

    public static String cat_title;
    public static int cat_size;
    public static HashMap<String, ItemStack> cat_system=new HashMap<>();
    public static HashMap<String, List<Integer>> cat_systemslot=new HashMap<>();
    public static List<Integer> paperSlots;

    public static String put_title;
    public static int put_size;
    public static HashMap<String, ItemStack> put_system=new HashMap<>();
    public static HashMap<String, List<Integer>> put_systemslot=new HashMap<>();
    public static int putSlot;

    public static void LoadMenus()
    {
        File file = new File("./plugins/LegendaryAlchemy","GUI.yml");
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);

        Bukkit.getConsoleSender().sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &b加载GUI - 主界面"));
        main_title = MsgUtils.msg(yml.getString("main.title","丹药炼制 - 选择丹药配方分类"));
        main_size = yml.getInt("main.size",9);
        Bukkit.getConsoleSender().sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &b加载GUI - 主界面 元素中"));
        if (yml.contains("main.set"))
        {
            ConfigurationSection sec=yml.getConfigurationSection("main.set");
            if (sec != null)
            {
                for (String name:sec.getKeys(false))
                {
                    ItemStack i =new ItemStack(ItemUtils.getMaterial(yml.getString("main.set."+name+".id")),yml.getInt("main.set."+name+".amount"),(short)yml.getInt("main.set."+name+".data"));
                    ItemMeta id=i.getItemMeta();
                    id.setDisplayName(MsgUtils.msg(yml.getString("main.set."+name+".name")));
                    ArrayList l=new ArrayList();
                    for (String lore: yml.getStringList("main.set."+name+".lore"))
                    {
                        l.add(MsgUtils.msg(lore));
                    }
                    if (yml.getInt("main.set."+name+".model") != -1)
                    {
                        id.setCustomModelData(yml.getInt("main.set."+name+".model"));
                    }
                    id.setLore(l);
                    i.setItemMeta(id);
                    List<Integer> slots=new ArrayList<>();
                    if (yml.contains("main.set."+name+".slots"))
                    {
                        slots=yml.getIntegerList("main.set."+name+".slots");
                    }
                    else {
                        slots.add(yml.getInt("main.set."+name+".slot"));
                    }
                    main_items.put(name,i);
                    main_slot.put(name,slots);
                    if (yml.contains("main.set."+name+".openCategorize"))
                    {
                        String cat= yml.getString("main.set."+name+".openCategorize");
                        main_categorizeSlot.put(slots.get(0),cat);
                        Bukkit.getConsoleSender().sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &b加载GUI - 主界面 元素: &a"+name +" &3跳转分类 → "+cat));
                    }
                    else {
                        Bukkit.getConsoleSender().sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &b加载GUI - 主界面 元素: &a"+name));
                    }
                }
            }
        }
        Bukkit.getConsoleSender().sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &b加载GUI - 主界面 &d完成！"));

        paperSlots=yml.getIntegerList("CategorizeGui.PaperSlots");
        cat_title = MsgUtils.msg(yml.getString("CategorizeGui.title"));
        cat_size = yml.getInt("CategorizeGui.size");
        if (yml.contains("CategorizeGui.systemPart"))
        {
            ConfigurationSection sec=yml.getConfigurationSection("CategorizeGui.systemPart");
            for(String name: sec.getKeys(false))
            {
                ItemStack i =new ItemStack(ItemUtils.getMaterial(yml.getString("CategorizeGui.systemPart."+name+".id")),yml.getInt("CategorizeGui.systemPart."+name+".amount"),(short)yml.getInt("CategorizeGui.systemPart."+name+".data"));
                ItemMeta id=i.getItemMeta();
                id.setDisplayName(MsgUtils.msg(yml.getString("CategorizeGui.systemPart."+name+".name")));
                ArrayList l=new ArrayList();
                for (String lore: yml.getStringList("CategorizeGui.systemPart."+name+".lore"))
                {
                    l.add(MsgUtils.msg(lore));
                }
                if (yml.getInt("main.set."+name+".model") != -1)
                {
                    id.setCustomModelData(yml.getInt("main.set."+name+".model"));
                }
                id.setLore(l);
                i.setItemMeta(id);
                List<Integer> slots=new ArrayList<>();
                if (yml.contains("CategorizeGui.systemPart."+name+".slots"))
                {
                    slots=yml.getIntegerList("CategorizeGui.systemPart."+name+".slots");
                }
                else {
                    slots.add(yml.getInt("CategorizeGui.systemPart."+name+".slot"));
                }
                cat_system.put(name,i);
                cat_systemslot.put(name,slots);
            }
        }
        if (yml.contains("CategorizeGui.CustomPart"))
        {
            ConfigurationSection sec=yml.getConfigurationSection("CategorizeGui.CustomPart");
            for(String name: sec.getKeys(false))
            {
                ItemStack i =new ItemStack(ItemUtils.getMaterial(yml.getString("CategorizeGui.CustomPart."+name+".id")),yml.getInt("CategorizeGui.CustomPart."+name+".amount"),(short)yml.getInt("CategorizeGui.CustomPart."+name+".data"));
                ItemMeta id=i.getItemMeta();
                id.setDisplayName(MsgUtils.msg(yml.getString("CategorizeGui.CustomPart."+name+".name")));
                ArrayList l=new ArrayList();
                for (String lore: yml.getStringList("CategorizeGui.CustomPart."+name+".lore"))
                {
                    l.add(MsgUtils.msg(lore));
                }
                if (yml.getInt("main.set."+name+".model") != -1)
                {
                    id.setCustomModelData(yml.getInt("main.set."+name+".model"));
                }
                id.setLore(l);
                i.setItemMeta(id);
                List<Integer> slots=new ArrayList<>();
                if (yml.contains("CategorizeGui.CustomPart."+name+".slots"))
                {
                    slots=yml.getIntegerList("CategorizeGui.CustomPart."+name+".slots");
                }
                else {
                    slots.add(yml.getInt("CategorizeGui.CustomPart."+name+".slot"));
                }
                cat_system.put(name,i);
                cat_systemslot.put(name,slots);
            }
        }

        putSlot=yml.getInt("PutGui.MaterialPutSlot");
        put_title = MsgUtils.msg(yml.getString("PutGui.title"));
        put_size = yml.getInt("PutGui.size");
        if (yml.contains("PutGui.systemPart"))
        {
            ConfigurationSection sec=yml.getConfigurationSection("PutGui.systemPart");
            for(String name: sec.getKeys(false))
            {
                ItemStack i =new ItemStack(ItemUtils.getMaterial(yml.getString("PutGui.systemPart."+name+".id")),yml.getInt("PutGui.systemPart."+name+".amount"),(short)yml.getInt("PutGui.systemPart."+name+".data"));
                ItemMeta id=i.getItemMeta();
                id.setDisplayName(MsgUtils.msg(yml.getString("PutGui.systemPart."+name+".name")));
                ArrayList l=new ArrayList();
                for (String lore: yml.getStringList("PutGui.systemPart."+name+".lore"))
                {
                    l.add(MsgUtils.msg(lore));
                }
                if (yml.getInt("main.set."+name+".model") != -1)
                {
                    id.setCustomModelData(yml.getInt("main.set."+name+".model"));
                }
                id.setLore(l);
                i.setItemMeta(id);
                List<Integer> slots=new ArrayList<>();
                if (yml.contains("PutGui.systemPart."+name+".slots"))
                {
                    slots=yml.getIntegerList("PutGui.systemPart."+name+".slots");
                }
                else {
                    slots.add(yml.getInt("PutGui.systemPart."+name+".slot"));
                }
                put_system.put(name,i);
                put_systemslot.put(name,slots);
            }
        }
        if (yml.contains("PutGui.CustomPart"))
        {
            ConfigurationSection sec=yml.getConfigurationSection("PutGui.CustomPart");
            for(String name: sec.getKeys(false))
            {
                ItemStack i =new ItemStack(ItemUtils.getMaterial(yml.getString("PutGui.CustomPart."+name+".id")),yml.getInt("PutGui.CustomPart."+name+".amount"),(short)yml.getInt("PutGui.CustomPart."+name+".data"));
                ItemMeta id=i.getItemMeta();
                id.setDisplayName(MsgUtils.msg(yml.getString("PutGui.CustomPart."+name+".name")));
                ArrayList l=new ArrayList();
                for (String lore: yml.getStringList("PutGui.CustomPart."+name+".lore"))
                {
                    l.add(MsgUtils.msg(lore));
                }
                if (yml.getInt("main.set."+name+".model") != -1)
                {
                    id.setCustomModelData(yml.getInt("main.set."+name+".model"));
                }
                id.setLore(l);
                i.setItemMeta(id);
                List<Integer> slots=new ArrayList<>();
                if (yml.contains("PutGui.CustomPart."+name+".slots"))
                {
                    slots=yml.getIntegerList("PutGui.CustomPart."+name+".slots");
                }
                else {
                    slots.add(yml.getInt("PutGui.CustomPart."+name+".slot"));
                }
                put_system.put(name,i);
                put_systemslot.put(name,slots);
            }
        }
    }
}
