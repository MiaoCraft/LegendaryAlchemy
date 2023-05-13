package com.gyzer.lianyao.legendaryalchemy.Mysql;

public enum MysqlCommand {
    CREATE_TABLE1(
			"CREATE TABLE IF NOT EXISTS `LegendaryAlchemy` (" +
                    "`player` VARCHAR(100) NOT NULL," +
                    "`string` VARCHAR(10000) NOT NULL," +
                    "`level` INT NOT NULL DEFAULT 0," +
                    "`exp` DOUBLE NOT NULL DEFAULT 0.0," +
                    "PRIMARY KEY (`player`))"
    ),
    //这句话就算如果不存在TABLE1的表，创建之
    //有以下列数
    //int列，存储数据
    //string列，存储字符串
    //主键为int
    ADD_DATA(
			"INSERT INTO `LegendaryAlchemy` " +
                    "(`player`, `string`)" +
                    "VALUES (?, ?)"
    ),
    UPDATA_LEVEL(
            "UPDATE LegendaryAlchemy SET level=? WHERE player=?"
    ),
    UPDATA_EXP(
            "UPDATE LegendaryAlchemy SET exp=? WHERE player=?"
    ),
    UPDATA_DATA(
            "UPDATE LegendaryAlchemy SET string=? WHERE player=?"
    ),
    //添加一行数据，包含2个值
    DELETE_DATA(
			"DELETE FROM `LegendaryAlchemy` WHERE `player` = ?"
    ),
    //删除主键为[int]的一行数据
    SELECT_DATA(
			"SELECT * FROM `LegendaryAlchemy` WHERE `player` = ?"
    );
    //查找主键为[int]的一行数据

    /*
     * 这里可以添加更多的MySQL命令，格式如下
     * COMMAND_NAME(
     *    "YOUR_COMMAND_HERE" +
     *    "YOUR_COMMAND_HERE"
     * );
     */

    private String command;

    MysqlCommand(String command)
    {
        this.command = command;
    }


    public String commandToString()
    {
        return command;
    }
        }
