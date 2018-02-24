package net.IpSearch;

import Service.MarkedIpService;
import Service.ServiceIpService;
import Service.impl.MarkedIpImpl;
import Service.impl.ServiceIpImpl;
import utils.ThreadParameter;

/**
 * @Author: HanJiafeng
 * @Date: 17:07 2018/2/23
 * @Desc:
 */
public class FirstSpider implements Runnable{

    private boolean isRunning = true;

    private MarkedIpService markedIpService = new MarkedIpImpl();

    private ServiceIpService serviceIpService = new ServiceIpImpl();

    @Override
    public void run() {

        int ipCount = ThreadParameter.getFirstSpider_IpCount();//单个线程每次取的IP数
        int port = 80;

        while (isRunning){

            try {
                Long ipLong = markedIpService.getIp(1);//获取标记Ip
                for (int i = 0;i < ipCount; i ++){

                    Long ip = ipLong + i;

                    boolean isAvailable = ServiceCheck.isAvailable(ip,port);
                    if (isAvailable){
                        //存入可用ServiceIp表
                        serviceIpService.insertIp(ip,port);
                    }
                    Thread.sleep(2);//2ms循环一次,防止占用太多资源
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭蜘蛛
     */
    public void close(){

        isRunning = false;
    }
}
