package com.gyzer.lianyao.legendaryalchemy.Utils;

import me.clip.placeholderapi.util.Msg;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.YearMonth;
import java.util.List;

public class ReadMessage {
    public static String PluginTitle;
    public static String gainLevel;
    public static String gainExp;
    public static String alreadyUnlock;
    public static String gui_unlock;
    public static String gui_lock;
    public static String gui_start;
    public static String setblock_on;
    public static String setblock_remove;
    public static String setblock_cant;
    public static  String removePot_far;
    public static String put_cancel;
    public static String holo_head;
    public static String holo_object;
    public static String holo_progress;
    public static String holo_click;
    public static String gui_preview;
    public static String block_break;
    public static String put_null;
    public static String put_in;
    public static String begin_overtime;
    public static String begin_time;
    public static String begin_tip_title_main;
    public static String begin_tip_title_sub;
    public static List<String> begin_tip_message;
    public static String begin_fail;
    public static String begin_fail_title;
    public static String begin_success;
    public static String begin_success_title;
    public static String begin_fail_unluck;
    public static String begin_level;
    public static String permission;
    public static String unlock;
    public static List<String> info;


    public static void reloadMessage()
    {
        File file=new File("./plugins/LegendaryAlchemy","message.yml");
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);

        PluginTitle = MsgUtils.msg(yml.getString("pluginTitle","&6LegendaryAlchemy &f>> "));
        gainLevel = MsgUtils.msg(yml.getString("gainLevel","&a你的炼药等级提升了 &e%level% &a级，目前 &e%now%&a级!"));
        gainExp = MsgUtils.msg(yml.getString("gainExp","&a你获得了 &e%amount% &a点炼药经验"));
        alreadyUnlock = MsgUtils.msg(yml.getString("alreadyUnlock","&7你已经解锁了该 %name% &7的配方"));
        gui_unlock = MsgUtils.msg(yml.getString("gui_unlock","&f&l · &a已解锁该配方"));
        gui_lock = MsgUtils.msg(yml.getString("gui_lock","&f&l · &c该配方未解锁"));
        gui_start = MsgUtils.msg(yml.getString("gui_start","&7[ &b点击开始炼制该丹药 &7]"));
        setblock_on = MsgUtils.msg(yml.getString("setblock_on","&a你放置了炼药锅"));
        setblock_cant = MsgUtils.msg(yml.getString("setblock_cant","&c所指处无法放置炼药锅"));
        setblock_remove = MsgUtils.msg(yml.getString("setblock_remove","&e炼药锅已收回"));
        removePot_far = MsgUtils.msg(yml.getString("removePot_far","&c距离炼药锅太远，炼药锅已自动收回"));
        put_cancel = MsgUtils.msg(yml.getString("put_cancel","&c取消本次炼制，药材已返回至背包内"));
        holo_head = MsgUtils.msg(yml.getString("holo_head","&6&l炼药锅"));
        holo_object = MsgUtils.msg(yml.getString("holo_object","&e炼制: &a%id%"));
        holo_progress = MsgUtils.msg(yml.getString("holo_progress","&e进度: &a%progress%"));
        holo_click = MsgUtils.msg(yml.getString("holo_click","&f[ 右键操作 ]"));
        gui_preview = MsgUtils.msg(yml.getString("gui_preview","&7[ &d右键&3预览该丹药 &7]"));
        put_null = MsgUtils.msg(yml.getString("put_null","&c请在左边放入药材"));
        put_in = MsgUtils.msg(yml.getString("put_in","&a成功在火候为 &e%hot% &a时投入药材 %name%×%amount%，还需投入&e %less% &a种药材"));
        begin_overtime = MsgUtils.msg(yml.getString("begin_overtime","&c超出该丹药最大炼制时间，炼制失败！"));
        begin_time = MsgUtils.msg(yml.getString("begin_time","&e你需要在 &a%time% &e内完成炼药"));
        begin_tip_title_main = MsgUtils.msg(yml.getString("begin_tip.title.main","&a&l开始炼制丹药"));
        begin_tip_title_sub = MsgUtils.msg(yml.getString("begin_tip.title.sub","%name%"));
        begin_tip_message = yml.getStringList("begin_tip.message");
        begin_fail = MsgUtils.msg(yml.getString("begin_fail",""));
        begin_fail_title = MsgUtils.msg(getString("begin_fail_title","&c&l炼制失败！;&f出现了一点小状况..."));
        begin_success = MsgUtils.msg(yml.getString("begin_success",""));
        begin_success_title = MsgUtils.msg(getString("begin_success_title","&a炼制成功！获得 %name%"));
        begin_fail_unluck = MsgUtils.msg(yml.getString("begin_fail_unluck",""));
        block_break = MsgUtils.msg(yml.getString("break","&c你无法破坏炼药锅"));
        permission = MsgUtils.msg(yml.getString("permission","&c你没有权限使用该指令"));
        begin_level = MsgUtils.msg(yml.getString("begin_level","&c炼制该丹药需炼药等级不低于 &a%level% &c级"));
        unlock = MsgUtils.msg(yml.getString("unlock","&e你解锁了 %name% &e配方"));
        info = yml.getStringList("info");
    }

    public static String getString(String path,String def)
    {
        File file=new File("./plugins/LegendaryAlchemy","message.yml");
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
        if (yml.contains(path))
        {
            return yml.getString(path);
        }
        else {
            yml.set(path,def);
            try {
                yml.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return def;
        }
    }
}
