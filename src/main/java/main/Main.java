package main;

import dao.DataSourceConfig;
import net.IpSearch.FirstSpider;
import net.PageSearch.SecondSpider;

/**
 * @Author: HanJiafeng
 * @Date: 11:04 2018/2/23
 * @Desc:
 */
public class Main {

    public static void main(String...args){

        init();

        /*FirstSpider firstSpider = new FirstSpider();
        Thread firstThread = new Thread(firstSpider);
        firstThread.start();*/

        SecondSpider secondSpider = new SecondSpider();
        Thread secondThread = new Thread(secondSpider);
        secondThread.start();
    }

    private static void init(){

        DataSourceConfig.init();
    }
}
