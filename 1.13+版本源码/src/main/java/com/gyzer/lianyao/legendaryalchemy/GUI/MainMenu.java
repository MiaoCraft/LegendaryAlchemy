package com.gyzer.lianyao.legendaryalchemy.GUI;

import com.gyzer.lianyao.legendaryalchemy.Utils.MenuUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MainMenu {

    public static void open(Player p)
    {
        Inventory inv= Bukkit.createInventory(p, MenuUtils.main_size, MenuUtils.main_title);
        int a=0;
        for (String id: MenuUtils.main_items.keySet())
        {
            for (int slot: MenuUtils.main_slot.get(id))
            {
                inv.setItem(slot, MenuUtils.main_items.get(id));
            }
            a++;
        }
        p.openInventory(inv);
    }

}
