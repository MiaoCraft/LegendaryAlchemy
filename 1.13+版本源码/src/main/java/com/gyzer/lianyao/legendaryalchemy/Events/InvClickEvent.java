package com.gyzer.lianyao.legendaryalchemy.Events;

import com.gyzer.lianyao.legendaryalchemy.API.AlchemyFailedEvent;
import com.gyzer.lianyao.legendaryalchemy.API.AlchemySuccessEvent;
import com.gyzer.lianyao.legendaryalchemy.API.PlayerAPI;
import com.gyzer.lianyao.legendaryalchemy.GUI.Categorizes;
import com.gyzer.lianyao.legendaryalchemy.GUI.MainMenu;
import com.gyzer.lianyao.legendaryalchemy.LegendaryAlchemy;
import com.gyzer.lianyao.legendaryalchemy.Utils.*;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyBlock;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyData;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyRun;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InvClickEvent implements Listener {
    @EventHandler
    public void onic(InventoryClickEvent e)
    {
        //main
        if (e.getView().getTitle().equals(MenuUtils.main_title))
        {
            if (e.getRawSlot() >=0) {
                e.setCancelled(true);

                if (MenuUtils.main_categorizeSlot.containsKey(e.getRawSlot())) {
                    Player p = (Player) e.getWhoClicked();
                    Categorizes.open(p, MenuUtils.main_categorizeSlot.get(e.getRawSlot()), 1);
                    return;
                }
            }
            return;
        }
        //cat
        if (e.getView().getTitle().contains(MenuUtils.cat_title))
        {
            if (e.getRawSlot() >=0) {
                e.setCancelled(true);
                Player p=(Player) e.getWhoClicked();
                if (!e.getCurrentItem().getType().equals(Material.AIR)) {
                    if (MenuUtils.cat_systemslot.get("backItem").contains(e.getRawSlot()))
                    {
                        MainMenu.open(p);
                        return;
                    }
                    int page = Integer.parseInt(e.getView().getTitle().substring(e.getView().getTitle().indexOf("第") + 1, e.getView().getTitle().indexOf("页")));
                    String cat = Categorizes.open.get(p);

                    if (MenuUtils.cat_systemslot.get("nextItem").contains(e.getRawSlot()))
                    {
                        Categorizes.open(p,cat,(page+1));
                        return;
                    }
                    if (MenuUtils.cat_systemslot.get("preItem").contains(e.getRawSlot()))
                    {
                        if (page > 1) {
                            Categorizes.open(p, cat, (page - 1));
                        }
                        return;
                    }
                    if (MenuUtils.paperSlots.contains(e.getRawSlot())) {

                        if (Categorizes.open.containsKey(p)) {
                            List<String> l = Categorizes.getPage(cat, page);
                            String id = l.get(MenuUtils.paperSlots.indexOf(e.getRawSlot()));
                            if (e.isRightClick())
                            {

                                String paper= AlchemyData.success_itemid.get(id);
                                String name= ItemUtils.getSaveItem(paper).getType().name();
                                if (ItemUtils.getSaveItem(paper).getItemMeta().hasDisplayName())
                                {
                                    name= ItemUtils.getSaveItem(paper).getItemMeta().getDisplayName();
                                }

                                Inventory inv= Bukkit.createInventory(p,9,"预览 - "+name);
                                inv.setItem(4, ItemUtils.getSaveItem(paper));
                                p.openInventory(inv);
                                return;
                            }
                            if (PlayerAPI.hasUnLockPaper(p.getName(), id)) {

                                if (AlchemyData.needLevel.get(id) > PlayerAPI.getLevel(p.getName()))
                                {
                                    p.sendMessage(ReadMessage.PluginTitle+ ReadMessage.begin_level.replace("%level%",""+ AlchemyData.needLevel.get(id)));
                                    return;
                                }
                                AlchemyRun run = new AlchemyRun(p, id);
                                run.begin();
                                AlchemyBlock.begin.put(p, run);

                                List<ItemStack> list=new ArrayList<>();
                                List<Integer> list2=new ArrayList<>();
                                AlchemyBlock.putListItem.put(p,list);
                                AlchemyBlock.putListHot.put(p,list2);

                                p.closeInventory();
                                Location loc= HoloUtils.cacheLo.get(p);
                                //HoloUtils.removeHo(HoloUtils.cacheLo.get(p));

                                String paper= AlchemyData.success_itemid.get(id);
                                String name= ItemUtils.getSaveItem(paper).getType().name();
                                if (ItemUtils.getSaveItem(paper).getItemMeta().hasDisplayName())
                                {
                                    name= ItemUtils.getSaveItem(paper).getItemMeta().getDisplayName();
                                }

                                HoloUtils.createHolo(name,"0%",p,loc);

                                p.sendTitle(ReadMessage.begin_tip_title_main, ReadMessage.begin_tip_title_sub.replace("%name%",name));
                                for (String msg: ReadMessage.begin_tip_message)
                                {
                                    p.sendMessage(MsgUtils.msg(msg.replace("%name%",name)));
                                }
                                p.playSound(loc,Sound.valueOf(ReadConfig.sound_start),1,1);
                                return;
                            }
                        }
                        return;
                    }
                }
            }
            return;
        }
        //put
        Player p=(Player) e.getWhoClicked();
        if (e.getView().getTitle().contains(MenuUtils.put_title.replace("%hot%","")))
        {
            if (e.getRawSlot() >=0 && e.getRawSlot() < MenuUtils.put_size)
            {
                if (e.getRawSlot()!= MenuUtils.putSlot)
                {
                    e.setCancelled(true);
                    if (MenuUtils.put_systemslot.get("cancel").contains(e.getRawSlot()))
                    {
                        if (!AlchemyBlock.begin.containsKey(p))
                        {
                            p.closeInventory();
                            return;
                        }
                        AlchemyBlock.begin.get(p).setCanUpHot(true);
                        AlchemyBlock.begin.get(p).stop();
                        AlchemyBlock.begin.remove(p);
                        if (AlchemyBlock.putListItem.containsKey(p))
                        {
                            if (AlchemyBlock.putListItem.containsKey(p))
                            {
                                for (ItemStack i: AlchemyBlock.putListItem.get(p))
                                {
                                    p.getInventory().addItem(i);
                                }

                            }
                        }
                        AlchemyBlock.putListItem.remove(p);
                        AlchemyBlock.putListHot.remove(p);
                        p.sendMessage(ReadMessage.PluginTitle+ ReadMessage.put_cancel);
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ReadMessage.put_cancel));
                        p.closeInventory();
                        HoloUtils.createHolo("无","0%",p, HoloUtils.cacheLo.get(p));
                        return;

                    }
                    if (MenuUtils.put_systemslot.get("put").get(0)==e.getRawSlot())
                    {
                        if (e.getInventory().getItem(MenuUtils.putSlot)!=null)
                        {
                            if (!AlchemyBlock.begin.containsKey(p))
                            {
                                p.closeInventory();
                                return;
                            }
                            ItemStack i=e.getInventory().getItem(MenuUtils.putSlot);
                            String name=i.getType().name();
                            if (i.hasItemMeta() && i.getItemMeta().hasDisplayName())
                            {
                                name=i.getItemMeta().getDisplayName();
                            }

                            int hot= AlchemyBlock.begin.get(p).getHot(p);
                            List<ItemStack> il= AlchemyBlock.putListItem.get(p);
                            List<Integer> intl= AlchemyBlock.putListHot.get(p);
                            il.add(i);
                            intl.add(hot);
                            AlchemyBlock.putListItem.put(p,il);
                            AlchemyBlock.putListHot.put(p,intl);
                            e.getInventory().setItem(MenuUtils.putSlot,null);
                            int less= AlchemyData.PaperRequire.get(AlchemyBlock.begin.get(p).getId()).size()-il.size();
                            if (less > 0) {

                                double n=((double)il.size()/(double) AlchemyData.PaperRequire.get(AlchemyBlock.begin.get(p).getId()).size());
                                String sc = String.format("%.2f", new Object[] { Double.valueOf(n) });
                                double sc1 = Double.parseDouble(sc);
                                double sc2 = sc1 * 100.0D;
                                ItemStack dy= ItemUtils.getSaveItem(AlchemyData.success_itemid.get(AlchemyBlock.begin.get(p).getId()));
                                String dyname=dy.getItemMeta().hasDisplayName() ? dy.getItemMeta().getDisplayName() : dy.getType().name();
                                HoloUtils.createHolo(dyname,sc2+"%",p, HoloUtils.cacheLo.get(p));
                                p.playSound(p.getLocation(),Sound.valueOf(ReadConfig.sound_put),1,1);
                                p.sendMessage(ReadMessage.PluginTitle + ReadMessage.put_in.replace("%hot%",""+hot).replace("%name%", name).replace("%amount%", "" + i.getAmount()).replace("%less%",""+less));

                            }
                            else {
                                if (AlchemyData.canSuccess(p, AlchemyBlock.begin.get(p).getId()))
                                {
                                    AlchemySuccessEvent event=new AlchemySuccessEvent(p, AlchemyBlock.begin.get(p).getId());
                                    Bukkit.getPluginManager().callEvent(event);
                                    if (event.isCancelled())
                                    {
                                        return;
                                    }
                                    LegendaryAlchemy.runSuccess(p, AlchemyBlock.begin.get(p).getId());
                                }
                                else {
                                    AlchemyFailedEvent event=new AlchemyFailedEvent(p, AlchemyBlock.begin.get(p).getId());
                                    Bukkit.getPluginManager().callEvent(event);
                                    if (event.isCancelled())
                                    {
                                        return;
                                    }
                                    LegendaryAlchemy.runFail(p, AlchemyBlock.begin.get(p).getId());
                                }
                                p.closeInventory();
                                return;
                            }
                            p.closeInventory();
                            return;


                        }
                        p.sendMessage(ReadMessage.PluginTitle+ ReadMessage.put_null);
                        return;
                    }
                    return;
                }

            }

        }
        if (e.getView().getTitle().contains("预览 - "))
        {
            e.setCancelled(true);
        }
    }
}
