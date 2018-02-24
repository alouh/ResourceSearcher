package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: HanJiafeng
 * @Date: 15:24 2018/2/23
 * @Desc: 线程参数类
 */
public class ThreadParameter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadParameter.class);

    private static String firstSpider_Count;
    private static String firstSpider_IpCount;

    private static String secondSpider_Count;
    private static String secondSpider_AddressCount;

    static {
        try {
            Properties properties = new Properties();
            InputStream inputStream = ThreadParameter.class.getResourceAsStream("/thread.properties");
            properties.load(inputStream);

            firstSpider_Count = properties.getProperty("firstSpider.count");
            firstSpider_IpCount = properties.getProperty("firstSpider.ipCount");

            secondSpider_Count = properties.getProperty("secondSpider.count");
            secondSpider_AddressCount = properties.getProperty("secondSpider.addressCount");
        }catch (Exception e){
            LOGGER.error("加载线程配置类失败:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static int getFirstSpider_Count() {

        int a = 0;
        try {
            a = Integer.valueOf(firstSpider_Count);
        }catch (Exception e){
            LOGGER.error("一级蜘蛛个数格式错误:" + e.getMessage());
            e.printStackTrace();
        }
        return a;
    }

    public static int getFirstSpider_IpCount() {

        int a = 0;
        try {
            a = Integer.valueOf(firstSpider_IpCount);
        }catch (Exception e){
            LOGGER.error("一级蜘蛛取出IP个数格式错误:" + e.getMessage());
            e.printStackTrace();
        }
        return a;
    }

    public static int getSecondSpider_Count() {
        int a = 0;
        try {
            a = Integer.valueOf(secondSpider_Count);
        }catch (Exception e){
            LOGGER.error("二级蜘蛛个数格式错误:" + e.getMessage());
            e.printStackTrace();
        }
        return a;
    }

    public static int getSecondSpider_AddressCount() {
        int a = 0;
        try {
            a = Integer.valueOf(secondSpider_AddressCount);
        }catch (Exception e){
            LOGGER.error("二级蜘蛛取出地址个数格式错误:" + e.getMessage());
            e.printStackTrace();
        }
        return a;
    }
}
