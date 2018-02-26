package main;

import dao.DataSourceConfig;
import gui.MainFrame;
import gui.ParameterDialog;
import net.IpSearch.FirstSpider;
import net.PageSearch.SecondSpider;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: HanJiafeng
 * @Date: 11:04 2018/2/23
 * @Desc:
 */
public class Main {

    public static void main(String...args){

        init();

        MainFrame mainFrame = new MainFrame();

        boolean isParameterRight = ParameterDialog.showDialog();//是否是初始化设置参数
        if (isParameterRight) {//是显示主面板
            mainFrame.setVisible(true);
        }
    }

    /**
     * 初始化数据库连接池
     */
    private static void init(){

        DataSourceConfig.init();
    }
}