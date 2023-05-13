package com.gyzer.lianyao.legendaryalchemy.Events;

import com.gyzer.lianyao.legendaryalchemy.GUI.Categorizes;
import com.gyzer.lianyao.legendaryalchemy.Utils.MenuUtils;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InvCloseEvent implements Listener {
    @EventHandler
    public void inc(InventoryCloseEvent e)
    {
        if (e.getView().getTitle().contains(MenuUtils.put_title.replace("%hot%","")))
        {
             Player p= (Player) e.getPlayer();
            if (AlchemyBlock.begin.get(p)!=null) {
                AlchemyBlock.begin.get(p).setCanUpHot(true);
            }
            if (e.getInventory().getItem(MenuUtils.putSlot)!=null)
            {
                p.getInventory().addItem(e.getInventory().getItem(MenuUtils.putSlot));
            }
        }
        if (e.getView().getTitle().contains(MenuUtils.cat_title))
        {
            if (Categorizes.open.containsKey(e.getPlayer()))
            {
                Categorizes.open.remove(e.getPlayer());
            }
       return;
        }


    }
}
