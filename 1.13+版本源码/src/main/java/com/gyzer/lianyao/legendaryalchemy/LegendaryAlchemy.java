package com.gyzer.lianyao.legendaryalchemy;

import com.gyzer.lianyao.legendaryalchemy.API.PlayerAPI;
import com.gyzer.lianyao.legendaryalchemy.Events.*;
import com.gyzer.lianyao.legendaryalchemy.Mysql.MySQLManager;
 import com.gyzer.lianyao.legendaryalchemy.Utils.*;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyBlock;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyData;
import com.gyzer.lianyao.legendaryalchemy.command.PluginCommand;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.List;
import java.util.Random;

public final class LegendaryAlchemy extends JavaPlugin {
     private static FileConfiguration config;
    public static Plugin LegendaryAlchemy;

     @Override
    public void onEnable() {


         AlchemyBlock.checkInUnKnowPot();
        Bukkit.getPluginCommand("lamy").setExecutor(new PluginCommand());
        // Plugin startup logic
        LegendaryAlchemy=this;

        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file=new File(getDataFolder(),"config.yml");
        if (!(file.exists())) {
            Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 生成config.yml");
            saveDefaultConfig();
        }
        file=new File(getDataFolder(),"Items.yml");
        if (!(file.exists())) {
            Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 生成Items.yml");
            saveResource("Items.yml",true);
        }
         file=new File(getDataFolder(),"PotData.yml");
         if (!(file.exists())) {
             Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 生成PotData.yml");
             saveResource("PotData.yml",true);
         }
        file=new File(getDataFolder(),"message.yml");
        if (!(file.exists())) {
            Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 生成message.yml");
            saveResource("message.yml",true);
        }
        file=new File(getDataFolder(),"GUI.yml");
        if (!(file.exists())) {
            Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 生成GUI.yml");
            saveResource("GUI.yml",true);
        }
        file=new File(getDataFolder(),"/Categorize/低级丹药.yml");
        if (!(file.exists())) {
            Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 生成低级丹药.yml");
            saveResource("Categorize/低级丹药.yml",true);
        }

        reloadConfig();
        config = getConfig();

        if (config.getBoolean("Store.mysql.enable")) {
            Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 启用Mysql存储，连接数据库...");
            new BukkitRunnable() {

                @Override
                public void run() {
                    MySQLManager.get().enableMySQL();
                }
            }.runTaskAsynchronously(this);
        }
        else {
            Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 关闭Mysql存储，默认使用YAML存储玩家数据");
        }

        Bukkit.getPluginManager().registerEvents(new JoinEvent(),this);
        Bukkit.getPluginManager().registerEvents(new InvClickEvent(),this);
        Bukkit.getPluginManager().registerEvents(new QuitEvent(),this);
        Bukkit.getPluginManager().registerEvents(new MoveEvent(),this);
        Bukkit.getPluginManager().registerEvents(new ChanegWordEvent(),this);
        Bukkit.getPluginManager().registerEvents(new RightEvent(),this);
        Bukkit.getPluginManager().registerEvents(new InvCloseEvent(),this);
        Bukkit.getPluginManager().registerEvents(new BreakEvent(),this);

        MenuUtils.LoadMenus();
        ReadConfig.reloadConfig();
        ReadMessage.reloadMessage();
        AlchemyData.load();

         if (!canEnable())
         {
             return;
         }
         if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
             new PAPI(this).register();
             Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 成功注册PlaceholderAPI变量");

         }
    }

    public boolean canEnable()
    {
        if (ReadConfig.hologram.equals("HolographicDisplays"))
        {
            if (Bukkit.getPluginManager().getPlugin("HolographicDisplays").isEnabled())
            {
                Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 使用全息插件 HolographicDisplays ");
                Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 检测到 HolographicDisplays 已安装");
                return true;
            }

        }
        if (ReadConfig.hologram.equals("CMI"))
        {
            if (Bukkit.getPluginManager().getPlugin("CMI").isEnabled())
            {
                Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 使用全息插件 CMI ");
                Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 检测到 CMI 已安装");
                return true;
            }
        }
        else
        {
            if (Bukkit.getPluginManager().getPlugin("DecentHolograms").isEnabled())
            {
                Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 使用全息插件 DecentHolograms ");
                Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 检测到 DecentHolograms 已安装");
                return true;
            }

        }
        Bukkit.getConsoleSender().sendMessage("LegendaryAlchemy >> 未检测到 "+ ReadConfig.hologram+" 这将影响插件的正常使用!");
        return false;
    }

    @Override
    public void onDisable()
    {

        if (ReadConfig.useMysql) {
            MySQLManager.get().shutdown(); //断开连接
        }

        for (Player p:Bukkit.getOnlinePlayers())
        {
            if (AlchemyBlock.blockLoc.containsKey(p))
            {
                AlchemyBlock.removePlayerPot(p,"关闭插件");
            }
        }
    }

    public static void run(Player p, List<String> l)
    {
        for (String cmd:l)
        {
            if (cmd.contains("[message]"))
            {
                p.sendMessage(MsgUtils.msg(cmd.replace("[message]","").replace("%player%",p.getName())));
            }
            if (cmd.contains("[player]"))
            {
                p.chat(cmd.replace("[player]","").replace("%player%",p.getName()));
            }
            if (cmd.contains("[op]"))
            {
                if (p.isOp())
                {
                    p.chat(cmd.replace("[op]","").replace("%player%",p.getName()));
                }
                else {
                    p.setOp(true);
                    p.chat(cmd.replace("[op]","").replace("%player%",p.getName()));
                    p.setOp(false);
                }
            }
            if (cmd.contains("[console]"))
            {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmd.replace("[console]","").replace("%player%",p.getName()));
            }
            if (cmd.contains("[title]"))
            {
                String c=cmd.replace("[title]","").replace("%player%",p.getName());
                String main=c.substring(0,c.indexOf(";"));
                String sub=c.substring(c.indexOf(";")+1);
                p.sendTitle(main,sub);
            }
            if (cmd.contains("[notice]"))
            {
                Bukkit.broadcastMessage(cmd.replace("%player%",p.getName()).replace("[notice]",""));
            }
        }
    }

    public static void runSuccess(Player p,String paperId) {
        run(p, AlchemyData.success_run.get(paperId));
        if (AlchemyData.success_exp.get(paperId) > 0) {
            PlayerAPI.addExp(p.getName(), AlchemyData.success_exp.get(paperId));
        }
        p.playSound(p.getLocation(), Sound.valueOf(ReadConfig.sound_success), 1, 1);
        Location loc = AlchemyBlock.blockLoc.get(p).clone();
        loc.add(0.5, 0.5, 0.5);
        for (double i = 0; i < 180; i += 180 / 6) {
            double radians = Math.toRadians(i);
            double radius = Math.sin(radians);
            double y = Math.cos(radians);
            for (double j = 0; j < 360; j += 180 / 6) {
                double radiansCircle = Math.toRadians(j);
                double x = Math.cos(radiansCircle) * radius;
                double z = Math.sin(radiansCircle) * radius;
                loc.add(x, y, z);
                loc.getWorld().spawnParticle( Particle.VILLAGER_HAPPY, loc,1);
                loc.subtract(x, y, z);
            }
        }

        //HoloUtils.removeHo(HoloUtils.cacheLo.get(p));
        HoloUtils.createHolo("无", "0%", p, HoloUtils.cacheLo.get(p));
        AlchemyBlock.begin.get(p).setCanUpHot(true);
        AlchemyBlock.begin.get(p).stop();
        AlchemyBlock.begin.remove(p);
        AlchemyBlock.putListHot.remove(p);
        AlchemyBlock.putListItem.remove(p);
        p.getInventory().addItem(ItemUtils.getSaveItem(AlchemyData.success_itemid.get(paperId)));
        ItemStack i = ItemUtils.getSaveItem(AlchemyData.success_itemid.get(paperId));
        String name = i.getType().name();
        if (i.hasItemMeta() && i.getItemMeta().hasDisplayName()) {
            name = i.getItemMeta().getDisplayName();
        }
        p.sendMessage(ReadMessage.PluginTitle + ReadMessage.begin_success.replace("%name%", name));
        String titlemain = ReadMessage.begin_success_title.contains(";") ? ReadMessage.begin_success_title.substring(0, ReadMessage.begin_success_title.indexOf(";")) : ReadMessage.begin_success_title;
        String titlesub = ReadMessage.begin_success_title.contains(";") ? ReadMessage.begin_success_title.substring(ReadMessage.begin_success_title.indexOf(";") + 1) : "";
        p.sendTitle(titlemain.replace("%name%",name),titlesub.replace("%name%",name));

    }

    public static void runFail(Player p,String paperId)
    {
        run(p, AlchemyData.fail_run.get(paperId));
        if (AlchemyData.fail_exp.get(paperId) > 0)
        {
            PlayerAPI.addExp(p.getName(), AlchemyData.fail_exp.get(paperId));
        }
        p.playSound(p.getLocation(),Sound.valueOf(ReadConfig.sound_fail),1,1);
        Location loc= AlchemyBlock.blockLoc.get(p).clone();
        loc.add(0.5,0.5,0.5);
        for (double i = 0; i < 180; i += 180 / 6) {
            double radians = Math.toRadians(i);
            double radius = Math.sin(radians);
            double y = Math.cos(radians);
            for (double j = 0; j < 360; j += 180 / 6) {
                double radiansCircle = Math.toRadians(j);
                double x = Math.cos(radiansCircle) * radius;
                double z = Math.sin(radiansCircle) * radius;
                loc.add(x, y, z);
                loc.getWorld().spawnParticle(Particle.VILLAGER_ANGRY,loc, 1);
                loc.subtract(x, y, z);
            }
        }
        //HoloUtils.removeHo(HoloUtils.cacheLo.get(p));
        HoloUtils.createHolo("无","0%",p, HoloUtils.cacheLo.get(p));


        if (AlchemyData.fail_back.get(paperId))
        {
            List<ItemStack> l= AlchemyBlock.putListItem.get(p);
            if (AlchemyData.fail_remove.get(paperId))
            {
                 int r=(new Random()).nextInt(l.size());
                 l.remove(r);
            }
            for (ItemStack i:l)
            {
                p.getInventory().addItem(i);
            }
        }
        AlchemyBlock.begin.get(p).setCanUpHot(true);
        AlchemyBlock.begin.get(p).stop();
        AlchemyBlock.begin.remove(p);
        AlchemyBlock.putListHot.remove(p);
        AlchemyBlock.putListItem.remove(p);
        p.sendMessage(ReadMessage.PluginTitle+ ReadMessage.begin_fail);
        String titlemain = ReadMessage.begin_fail_title.contains(";") ? ReadMessage.begin_fail_title.substring(0, ReadMessage.begin_fail_title.indexOf(";")) : ReadMessage.begin_fail_title;
        String titlesub = ReadMessage.begin_fail_title.contains(";") ? ReadMessage.begin_fail_title.substring(ReadMessage.begin_fail_title.indexOf(";") + 1) : "";
        p.sendTitle(titlemain,titlesub);

    }


}
