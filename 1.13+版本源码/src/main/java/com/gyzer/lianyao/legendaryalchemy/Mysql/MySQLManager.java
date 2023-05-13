package com.gyzer.lianyao.legendaryalchemy.Mysql;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.gyzer.lianyao.legendaryalchemy.LegendaryAlchemy.LegendaryAlchemy;

public class MySQLManager {
    private String hostname;
    private String databaseName;
    private String userName;
    private String userPassword;
    private Connection connection;
    private int port;
    public static MySQLManager instance = null;

    public static MySQLManager get() {
        return instance == null ? instance = new MySQLManager() : instance;
    }

    public void enableMySQL()
    {
        hostname = LegendaryAlchemy.getConfig().getString("Store.mysql.hostname");
        databaseName = LegendaryAlchemy.getConfig().getString("Store.mysql.databasename");
        userName = LegendaryAlchemy.getConfig().getString("Store.mysql.user");
        userPassword = LegendaryAlchemy.getConfig().getString("Store.mysql.password");
        port = LegendaryAlchemy.getConfig().getInt("Store.mysql.port");
        Bukkit.getConsoleSender().sendMessage(hostname+":"+port);
        connectMySQL();
        String cmd = MysqlCommand.CREATE_TABLE1.commandToString();
        try {
            PreparedStatement ps = connection.prepareStatement(cmd);
            doCommand(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connectMySQL()
    {

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + databaseName + "?autoReconnect=true", userName, userPassword);

        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
    public void doCommand(PreparedStatement ps)
    {
        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("执行指令失败，以下为错误提示");
            e.printStackTrace();
        }
    }
    public void shutdown() {
        try {
            connection.close();

        } catch (SQLException e) {
            //断开连接失败
            e.printStackTrace();
        }
    }

    public void insertData(String data1, String data2) {
        try {

            PreparedStatement ps;
            String s = MysqlCommand.ADD_DATA.commandToString();
            ps = connection.prepareStatement(s);
            ps.setString(1, data1);
            ps.setString(2, data2);
            try {
                ps.executeUpdate();

            } catch (SQLException e) {

                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {

        }
    }


    public void deleteData(String data1, CommandSender sender) {
        try {

            PreparedStatement ps;
            String s = MysqlCommand.DELETE_DATA.commandToString();
            ps = connection.prepareStatement(s);
            ps.setString(1, data1);
            try {
                ps.executeUpdate();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {

        }
    }

    public String findData(String data1, CommandSender sender) {
        try {

            String s = MysqlCommand.SELECT_DATA.commandToString();
            PreparedStatement ps = connection.prepareStatement(s);

            ps.setString(1, data1);
            ResultSet rs = ps.executeQuery();
            List<String> l=new ArrayList<>();
            while (rs.next())
            {
                String str = rs.getString("string");
                return str;
            }
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块

        } catch (NumberFormatException e) {

        }
        return null;
    }
    public double findExp(String data1, CommandSender sender) {
        try {

            String s = MysqlCommand.SELECT_DATA.commandToString();
            PreparedStatement ps = connection.prepareStatement(s);

            ps.setString(1, data1);
            ResultSet rs = ps.executeQuery();
            List<String> l=new ArrayList<>();
            while (rs.next())
            {
                double str = rs.getDouble("exp");
                return str;
            }
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块

        } catch (NumberFormatException e) {

        }
        return 0.0;
    }
    public int findLevel(String data1, CommandSender sender) {
        try {

            String s = MysqlCommand.SELECT_DATA.commandToString();
            PreparedStatement ps = connection.prepareStatement(s);

            ps.setString(1, data1);
            ResultSet rs = ps.executeQuery();
            List<String> l=new ArrayList<>();
            while (rs.next())
            {
                int str = rs.getInt("level");
                return str;
            }
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块

        } catch (NumberFormatException e) {

        }
        return 0;
    }

    public void setExp(String p,double exp)
    {
        try {

            String s = MysqlCommand.UPDATA_EXP.commandToString();
            PreparedStatement ps = connection.prepareStatement(s);

            ps.setDouble(1, exp);
            ps.setString(2,p);
            try {
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块

        } catch (NumberFormatException e) {

        }
    }
    public void setLevel(String p,int level)
    {
        try {

            String s = MysqlCommand.UPDATA_LEVEL.commandToString();
            PreparedStatement ps = connection.prepareStatement(s);

            ps.setInt(1, level);
            ps.setString(2,p);
            try {
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块

        } catch (NumberFormatException e) {

        }
    }
    public ArrayList toList(String arg)
    {
        Gson gs=new Gson();
        return gs.fromJson(arg,new TypeToken<List<String>>(){}.getType());
    }

    public String toArg(List<String> l)
    {
        Gson gs=new Gson();
        return gs.toJson(l);
    }

    public List<String> getList(String p)
    {
        List<String> l=new ArrayList<>();
        if (findData(p,Bukkit.getConsoleSender())==null)
        {
            return l;
        }
        String arg=findData(p,Bukkit.getConsoleSender());
        l=toList(arg);
        return l;

    }

    public void addData(String p,String id)
    {

        List<String> list=MySQLManager.get().getList(p);
        list.add(id);
        try {
            String s = MysqlCommand.UPDATA_DATA.commandToString();
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setString(1, toArg(list));
            ps.setString(2,p);
            try {
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块

        } catch (NumberFormatException e) {

        }

    }
}
