package net;

import Service.PageUrlService;
import Service.ServiceIpService;
import Service.impl.PageUrlImpl;
import Service.impl.ServiceIpImpl;
import gui.MainFrame;
import net.IpSearch.FirstSpider;
import net.PageSearch.SecondSpider;

import java.awt.*;
import java.util.List;

/**
 * @Author: HanJiafeng
 * @Date: 14:00 2018/2/26
 * @Desc: 监控线程
 */
public class ThreadPoolMonitor implements Runnable {

    private boolean isRunning = true;

    private List<FirstSpider> firstSpiderList;

    private List<SecondSpider> secondSpiderList;

    private ServiceIpService serviceIpService = new ServiceIpImpl();

    private PageUrlService pageUrlService = new PageUrlImpl();

    public ThreadPoolMonitor(List<FirstSpider> firstSpiderList,List<SecondSpider> secondSpiderList){

        this.firstSpiderList = firstSpiderList;
        this.secondSpiderList = secondSpiderList;

    }
    @Override
    public void run() {

        MainFrame mainFrame = (MainFrame) Frame.getFrames()[0];
        while (isRunning){

            try {
                //监控可用ip数量,如果大于指定数量则暂停搜索
                int availableIp = serviceIpService.countAvailableIp();
                if (availableIp > 100) {
                    for (FirstSpider firstSpider : firstSpiderList){
                        firstSpider.pause();
                    }
                    mainFrame.appendMsg("可用IP有剩余,一级蜘蛛暂停");
                }else {
                    for (FirstSpider firstSpider : firstSpiderList){
                        firstSpider.start();
                    }
                    mainFrame.appendMsg("可用IP不足,一级蜘蛛启动");
                }
                //监控可用url数量,如果大于指定数量则暂停搜索
                int availableUrl = pageUrlService.countAvailableUrl();
                if (availableUrl > 100) {
                    for (SecondSpider secondSpider : secondSpiderList){
                        secondSpider.pause();
                    }
                    mainFrame.appendMsg("可用URL有剩余,二级蜘蛛暂停");
                }else if(availableUrl == 0){

                    for (SecondSpider secondSpider : secondSpiderList){
                        secondSpider.pause();
                    }
                    mainFrame.appendMsg("可用URL为0,二级蜘蛛暂停");
                }else {
                    for (SecondSpider secondSpider : secondSpiderList){
                        secondSpider.start();
                    }
                    mainFrame.appendMsg("可用URL不足,二级蜘蛛启动");
                }

                Thread.sleep(1000 * 3);//每十秒检测一次
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    /**
     * 停止线程
     */
    public void stop(){

        isRunning = false;
    }
}
