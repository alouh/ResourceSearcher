package net.PageSearch;

import Service.PageUrlService;
import Service.ServiceIpService;
import Service.impl.PageUrlImpl;
import Service.impl.ServiceIpImpl;
import gui.MainFrame;

import java.awt.*;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Author: HanJiafeng
 * @Date: 10:07 2018/2/24
 * @Desc: 二级蜘蛛搜索资源链接和页面url
 */
public class SecondSpider implements Runnable {

    private boolean isRunning = true;

    private final Boolean isPause1 = false;

    private boolean isPause2;

    private ServiceIpService serviceIpService = new ServiceIpImpl();

    private PageUrlService pageUrlService = new PageUrlImpl();

    @Override
    public void run() {

        MainFrame mainFrame = (MainFrame) Frame.getFrames()[0];
        int urlCount = mainFrame.getSecondSpiderUrlCount();
        int interval = mainFrame.getInterval();
        String[] keywordArray = mainFrame.getKeywordArray();

        ContentCheck contentCheck = new ContentCheck();

        while (isRunning){
            try {
                synchronized (isPause1){//使线程进入等待状态
                    if (isPause2){
                        isPause1.wait();
                    }
                }
                //获取可达IP
                List<InetSocketAddress> socketAddressList = serviceIpService.getSocketAddresses(urlCount);
                for (InetSocketAddress socketAddress : socketAddressList){
                    boolean isAvailable = contentCheck.check(socketAddress,keywordArray);
                    if (isAvailable){
                        String url = socketAddress.getHostName() + ":" + socketAddress.getPort();
                        pageUrlService.insertUrl(url,0);
                        String msg = "检索到URL：" + url;
                        mainFrame.appendMsg(msg);
                        mainFrame.countUrlCountTField();
                    }
                }
                Thread.sleep(interval);//定时循环,防止占用太多资源
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭线程
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
