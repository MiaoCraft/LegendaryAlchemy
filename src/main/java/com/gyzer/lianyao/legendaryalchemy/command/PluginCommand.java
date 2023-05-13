package com.gyzer.lianyao.legendaryalchemy.command;

import com.gyzer.lianyao.legendaryalchemy.API.PlayerAPI;
import com.gyzer.lianyao.legendaryalchemy.GUI.Categorizes;
import com.gyzer.lianyao.legendaryalchemy.GUI.MainMenu;
import com.gyzer.lianyao.legendaryalchemy.LegendaryAlchemy;
import com.gyzer.lianyao.legendaryalchemy.Mysql.MySQLManager;
import com.gyzer.lianyao.legendaryalchemy.Mysql.MysqlCommand;
import com.gyzer.lianyao.legendaryalchemy.Utils.*;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyBlock;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyData;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyRun;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class PluginCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==0 ) {
            Player p = (Player) sender;
            for (String msg:ReadMessage.info)
            {
                p.sendMessage(MsgUtils.msg(msg));
            }
            return true;
        }
        else if (args.length==1) {
            if (args[0].equals("set")&& sender.hasPermission("LegendaryAlchemy.use"))
            {
                Player p = (Player) sender;
                AlchemyBlock.canPut(p);
                return true;
            }
            if (args[0].equals("reload")) {
                if (!sender.isOp()) {
                    return true;
                }
                LegendaryAlchemy.LegendaryAlchemy.reloadConfig();
                MenuUtils.LoadMenus();
                ReadConfig.reloadConfig();
                ReadMessage.reloadMessage();
                AlchemyData.load();
                sender.sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &b插件重载完毕"));
                return  true;
            }
        }
        else if (args.length==2){
              if (args[0].equals("save"))
              {
                  if (!sender.isOp())
                  {
                      return true;
                  }
                  Player p=(Player) sender;
                  if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType().equals(Material.AIR))
                  {
                      sender.sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &7请手持需要保存的物品再输入该指令"));
                      return true;
                  }
                  String id=args[1];
                  if (ItemUtils.hasItem(id))
                  {
                      sender.sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &7该ID已经存在，输入 &a/lamy save "+id+" cover &7覆盖原来保存的物品"));
                      return true;
                  }
                  sender.sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &a成功保存手上物品为 &f"+id));
                  ItemUtils.saveItem(id,p.getInventory().getItemInMainHand());
                  return true;
              }

        }
        else if (args.length==3) {
            if (args[0].equals("exp") && sender.isOp()) {
                if (hasPlayer(args[1])) {
                    if (isMath(args[2])) {
                        PlayerAPI.addExp(args[1], Integer.parseInt(args[2]));
                        sender.sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &7操作成功！"));
                        Bukkit.getConsoleSender().sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &7玩家" + sender.getName() + "给与了" + args[1] + "经验 ×" + args[2]));

                        return true;
                    }
                    sender.sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &7请输入整数"));
                    return true;
                }
                sender.sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &7该玩家不存在"));
                return true;
            }
            if (args[0].equals("paper")&& sender.isOp()) {
                if (hasPlayer(args[1])) {
                    for (String cat:AlchemyData.PaperIdList.keySet())
                    {
                        if (AlchemyData.PaperIdList.get(cat).contains(args[2]))
                        {
                            PlayerAPI.UnLockPaper(args[1],args[2]);
                            return true;
                        }
                        sender.sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &7该图纸ID不存在"));
                        return true;
                    }
                }
                sender.sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &7该玩家不存在"));
                return true;
            }
            if (args[0].equals("level")&& sender.isOp())
            {
                if (hasPlayer(args[1]))
                {
                    if (isMath(args[2]))
                    {
                        PlayerAPI.addLevel(args[1],Integer.parseInt(args[2]));
                        Bukkit.getConsoleSender().sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &7玩家"+sender.getName()+"给与了"+args[1]+"等级 ×"+args[2]));
                        sender.sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &7操作成功！"));
                        return true;
                    }
                    sender.sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &7请输入整数"));
                    return true;
                }
                sender.sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &7该玩家不存在"));
                return true;
            }
            if (args[0].equals("save") && args[2].equals("cover")) {
                if (!sender.isOp()) {
                    return true;
                }
                Player p = (Player) sender;
                if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                    sender.sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &7请手持需要保存的物品再输入该指令"));
                    return true;
                }
                String id = args[1];
                sender.sendMessage(MsgUtils.msg("&eLegendaryAlchemy &f>> &a成功保存手上物品为 &f"+id));
                ItemUtils.saveItem(id,p.getInventory().getItemInMainHand());
                return true;
            }
        }
        sender.sendMessage(ReadMessage.PluginTitle+ReadMessage.permission);
        return true;
    }
    public boolean hasPlayer(String name)
    {
        for (Player p:Bukkit.getOnlinePlayers())
        {
            if(p.getName().equals(name))
            {
                return true;
            }
        }
        for (OfflinePlayer p:Bukkit.getOfflinePlayers())
        {
            if (p.getName().equals(name))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isMath(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i)))
                return false;
        }
        return true;
    }
}
