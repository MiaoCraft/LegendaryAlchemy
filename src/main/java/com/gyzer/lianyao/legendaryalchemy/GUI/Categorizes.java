package com.gyzer.lianyao.legendaryalchemy.GUI;

import com.gyzer.lianyao.legendaryalchemy.API.PlayerAPI;
import com.gyzer.lianyao.legendaryalchemy.Utils.ItemUtils;
import com.gyzer.lianyao.legendaryalchemy.Utils.MenuUtils;
import com.gyzer.lianyao.legendaryalchemy.Utils.MsgUtils;
import com.gyzer.lianyao.legendaryalchemy.Utils.ReadMessage;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyBlock;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Categorizes {
    public static HashMap<Player,String> open =new HashMap<>();
    public static void open(Player p,String categorize,int page)
    {


        Inventory inv= Bukkit.createInventory(p,MenuUtils.cat_size,MenuUtils.cat_title+categorize.replace(".yml","")+ MsgUtils.msg("&0 第"+page+"页"));

        for (String  id:MenuUtils.cat_system.keySet())
        {
            for (int slot:MenuUtils.cat_systemslot.get(id))
            {
                inv.setItem(slot, MenuUtils.cat_system.get(id));
            }
        }
        int in=0;
        List<String> l=getPage(categorize,page);

        if (l.size() > 0)
        {
            for (int x=0;x<l.size();x++)
            {
                String paperid=AlchemyData.PaperItemId.get(l.get(in));
                int slot=MenuUtils.paperSlots.get(in);
                ItemStack i = ItemUtils.getSaveItem(paperid);
                ItemMeta id =i.getItemMeta();
                List<String> lore=id.getLore();
                if (PlayerAPI.hasUnLockPaper(p.getName(),l.get(in)))
                {
                    lore.add(" ");
                    lore.add(ReadMessage.gui_unlock);
                    lore.add(ReadMessage.gui_start);
                }
                else {
                    lore.add(" ");
                    lore.add(ReadMessage.gui_lock);
                }
                lore.add(ReadMessage.gui_preview);
                id.setLore(lore);
                i.setItemMeta(id);
                inv.setItem(slot,i);
                in++;
            }

        }
        else {
            return;
        }
        p.openInventory(inv);
        Categorizes.open.put(p, categorize.replace(".yml",""));

    }


    public static List<String> getPage(String categorize, int page)
    {
        List<String> l=new ArrayList<>();
        int start= (page-1)*MenuUtils.paperSlots.size();
        int end=(MenuUtils.paperSlots.size()-1)+(MenuUtils.paperSlots.size()*(page-1));

        for (int x=start;x<=end;x++)
        {
             if (AlchemyData.PaperIdList.get(categorize.replace(".yml","")).size() > x) {

                l.add(AlchemyData.PaperIdList.get(categorize.replace(".yml","")).get(x));

             }
        }
        return l;
    }

}
