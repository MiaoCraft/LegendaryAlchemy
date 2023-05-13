package com.gyzer.lianyao.legendaryalchemy.GUI;

import com.gyzer.lianyao.legendaryalchemy.Utils.MenuUtils;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyBlock;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PutMaterial {

    public static  void open(Player p,int hot)
    {
        Inventory inv= Bukkit.createInventory(p, MenuUtils.put_size, MenuUtils.put_title.replace("%hot%",""+hot));

        for (String  id: MenuUtils.put_system.keySet())
        {
            for (int slot: MenuUtils.put_systemslot.get(id))
            {
                inv.setItem(slot, MenuUtils.put_system.get(id));
            }
        }
        p.openInventory(inv);
        AlchemyBlock.begin.get(p).setCanUpHot(false);
    }

}
