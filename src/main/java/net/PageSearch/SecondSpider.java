package net.PageSearch;

import Service.PageUrlService;
import Service.ServiceIpService;
import Service.impl.PageUrlImpl;
import Service.impl.ServiceIpImpl;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Author: HanJiafeng
 * @Date: 10:07 2018/2/24
 * @Desc:
 */
public class SecondSpider implements Runnable {

    private boolean isRunning = true;

    private ServiceIpService serviceIpService = new ServiceIpImpl();

    private PageUrlService pageUrlService = new PageUrlImpl();

    @Override
    public void run() {

        ContentCheck contentCheck = new ContentCheck();

        while (isRunning){
            try {
                //获取可达IP
                List<InetSocketAddress> socketAddressList = serviceIpService.getSocketAddresses();
                for (InetSocketAddress socketAddress : socketAddressList){
                    boolean isAvailable = contentCheck.check(socketAddress);
                    if (isAvailable){
                        pageUrlService.insertUrl(socketAddress.getHostName(),0);
                    }
                }
                Thread.sleep(2);//2ms循环一次,防止占用太多资源
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭线程
     */
    public void close(){
        isRunning = false;
    }
}
