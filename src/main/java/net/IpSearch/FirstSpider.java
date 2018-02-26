package net.IpSearch;

import Service.MarkedIpService;
import Service.ServiceIpService;
import Service.impl.MarkedIpImpl;
import Service.impl.ServiceIpImpl;
import gui.MainFrame;

import java.awt.*;

/**
 * @Author: HanJiafeng
 * @Date: 17:07 2018/2/23
 * @Desc: 搜索可用IP
 */
public class FirstSpider implements Runnable{

    private boolean isRunning = true;

    private final Boolean isPause1 = false;

    private boolean isPause2;

    private MarkedIpService markedIpService = new MarkedIpImpl();

    private ServiceIpService serviceIpService = new ServiceIpImpl();

    @Override
    public void run() {

        MainFrame mainFrame = (MainFrame) Frame.getFrames()[0];
        int ipCount = mainFrame.getFirstSpiderIpCount();//单个线程每次取的IP数
        int interval = mainFrame.getInterval();
        int port = 80;

        while (isRunning){

            try {
                synchronized (isPause1){//使线程进入等待状态
                    if (isPause2){
                        isPause1.wait();
                    }
                }
                Long ipLong = markedIpService.getIp(1,ipCount);//获取标记Ip
                for (int i = 0;i < ipCount; i ++){

                    Long ip = ipLong + i;

                    boolean isAvailable = ServiceCheck.isAvailable(ip,port);
                    if (isAvailable){
                        //存入可用ServiceIp表
                        serviceIpService.insertIp(ip,port);
                        String msg = "检索到活跃IP：" + ip + ":" + port;
                        mainFrame.appendMsg(msg);
                        mainFrame.countIpCountTField();
                    }
                    Thread.sleep(interval);//定时循环,防止占用太多资源
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭蜘蛛
     */
    public void stop(){

        isRunning = false;
    }

    /**
     * 暂停线程
     */
    public void pause(){

        isPause2 = true;

    }

    /**
     * 继续线程
     */
    public void start(){

        isPause2 = false;
        synchronized (isPause1) {
            isPause1.notify();
        }
    }
}
