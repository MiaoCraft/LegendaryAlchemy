package com.gyzer.lianyao.legendaryalchemy.alchemy;

import com.destroystokyo.paper.inventory.meta.ArmorStandMeta;
import com.gyzer.lianyao.legendaryalchemy.Utils.FileUtils;
import com.gyzer.lianyao.legendaryalchemy.Utils.HoloUtils;
import com.gyzer.lianyao.legendaryalchemy.Utils.MsgUtils;
import com.gyzer.lianyao.legendaryalchemy.Utils.ReadMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Cauldron;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AlchemyBlock {

    public static HashMap<Player, Location> blockLoc=new HashMap<>();
    public static HashMap<Location, Player> locBlock=new HashMap<>();

    public static HashMap<Player,AlchemyRun> begin=new HashMap<>();
    public static HashMap<Player, List<ItemStack>> putListItem=new HashMap<>();
    public static HashMap<Player, List<Integer>> putListHot=new HashMap<>();


    public static boolean canPut(Player p)
    {
        if (blockLoc.containsKey(p))
        {
            p.sendMessage(ReadMessage.PluginTitle+ReadMessage.setblock_remove);
            p.getWorld().getBlockAt(blockLoc.get(p)).setType(Material.AIR);
            HoloUtils.removeHo(blockLoc.get(p));
            HoloUtils.cachePl.remove(blockLoc.get(p));
            HoloUtils.cacheLo.remove(blockLoc.get(p));
            FileUtils.removePotData(blockLoc.get(p));
            locBlock.remove(blockLoc.remove(p));
            if (begin.containsKey(p))
            {
                for (ItemStack i:putListItem.remove(p))
                {
                    p.getInventory().addItem(i);
                }
                putListHot.remove(p);
                begin.remove(p);
            }

            return false;
        }
        else {

            if (p.getTargetBlock(null,3).getLocation().distance(p.getLocation()) <= 3)
            {

                if (!p.getTargetBlock(null,3).isLiquid() && !p.getTargetBlock(null,3).isEmpty())
                {

                    Location loc=p.getTargetBlock(null,3).getLocation();
                    if (p.getWorld().getBlockAt(loc.getBlockX(),loc.getBlockY()+1,loc.getBlockZ()).getType().equals(Material.AIR) && p.getWorld().getBlockAt(loc.getBlockX(),loc.getBlockY()+2,loc.getBlockZ()).getType().equals(Material.AIR) &&p.getWorld().getBlockAt(loc.getBlockX(),loc.getBlockY()+3,loc.getBlockZ()).getType().equals(Material.AIR))
                    {

                        p.sendMessage(ReadMessage.PluginTitle+ReadMessage.setblock_on);
                        loc.add(0,1,0);
                        blockLoc.put(p, loc);
                        locBlock.put(loc,p);
                        p.getWorld().getBlockAt(loc).setType(Material.CAULDRON);
                        HoloUtils.createHolo("无","0%",p,loc);
                        FileUtils.addPotData(loc);
                        return true;
                    }
                }
                p.sendMessage(ReadMessage.PluginTitle+ReadMessage.setblock_cant);
                return false;
            }
            else {
                p.sendMessage(ReadMessage.PluginTitle+ReadMessage.setblock_cant);
                return false;
            }

        }
    }

    public static void removePlayerPot(Player p,String reason)
    {
        if (blockLoc.containsKey(p))
        {
            Location loc=blockLoc.remove(p);
            locBlock.remove(loc);
            loc.getWorld().getBlockAt(loc).setType(Material.AIR);
            HoloUtils.removeHo(HoloUtils.cacheLo.remove(p));
            HoloUtils.cachePl.remove(loc);
            AlchemyBlock.begin.remove(p);
            if (AlchemyBlock.putListItem.containsKey(p))
            {
                for (ItemStack i:putListItem.get(p))
                {
                    p.getInventory().addItem(i);
                }

            }
            putListItem.remove(p);
            putListHot.remove(p);
            Bukkit.getConsoleSender().sendMessage(ReadMessage.PluginTitle+ MsgUtils.msg("&4移除玩家"+p.getName()+"炼药锅 &e原因:"+reason));
            FileUtils.removePotData(loc);
         }
    }

    public static void checkInUnKnowPot()
    {
        if (FileUtils.getPotData().size() > 0)
        {
            for (Location loc:FileUtils.getPotData())
            {
                FileUtils.removePotData(loc);
                if (loc.getBlock().getType().equals(Material.CAULDRON))
                {
                    loc.getBlock().setType(Material.AIR);
                    Bukkit.getConsoleSender().sendMessage(MsgUtils.msg("&6LegendaryAlchemy &f>> &4检测到在 &e"+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+"&4处有个未知原因未被移除的炼药锅，目前已被处理"));
                }
            }
        }
    }

}
