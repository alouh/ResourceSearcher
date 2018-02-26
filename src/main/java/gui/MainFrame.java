package gui;

import net.IpSearch.FirstSpider;
import net.PageSearch.SecondSpider;
import net.ThreadPoolMonitor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: HanJiafeng
 * @Date: 8:58 2018/2/26
 * @Desc:
 */
public class MainFrame extends JFrame{

    private JPanel mainPane;
    private JTextArea msgTArea;
    private JTextField ipCountTField;
    private JTextField urlCountTField;
    private JTextField resourceCountTField;
    private JButton startBtn;
    private JButton endBtn;

    private int firstSpiderCount;
    private int firstSpiderIpCount;
    private int secondSpiderCount;
    private int secondSpiderUrlCount;
    private int resourceSpiderCount;
    private int resourceSpiderUrlCount;
    private int interval;
    private String[] keywordArray;

    private int ipCount;
    private int urlCount;
    private int resourceCount;

    private short threadStatus = 0;//0:搜索线程停止;1:搜索线程运行;2:搜索线程暂停

    private ExecutorService firstPool;
    private ExecutorService secondPool;
    private ThreadPoolMonitor threadPoolMonitor;

    public MainFrame(){

        try{
            UIManager.setLookAndFeel(com.sun.java.swing.plaf.windows.WindowsLookAndFeel.class.getName());
        }catch (Exception e){
            e.printStackTrace();
        }
        add(mainPane);//添加主面板
        addMenuBar();

        startBtn.addActionListener(e -> startBtnEvent());

        endBtn.addActionListener(e -> endBtnEvent());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(500,200,600,370);
    }

    /**
     * 停止搜索线程
     */
    private void endBtnEvent(){

        firstPool.shutdownNow();

        secondPool.shutdownNow();

        threadPoolMonitor.stop();
    }
    /**
     * 开始按钮事件
     */
    private void startBtnEvent(){

        List<FirstSpider> firstSpiderList = new ArrayList<>();

        List<SecondSpider> secondSpiderList = new ArrayList<>();

        if (threadStatus == 0) {
            firstPool = Executors.newFixedThreadPool(firstSpiderCount);

            for (int i = 0; i < firstSpiderCount; i++) {

                FirstSpider firstSpider = new FirstSpider();
                firstSpiderList.add(firstSpider);
                firstPool.submit(firstSpider);
            }

            secondPool = Executors.newFixedThreadPool(secondSpiderCount);
            for (int i = 0; i < secondSpiderCount; i++) {

                SecondSpider secondSpider = new SecondSpider();
                secondSpiderList.add(secondSpider);
                secondPool.submit(secondSpider);
            }

            threadPoolMonitor = new ThreadPoolMonitor(firstSpiderList, secondSpiderList);
            Thread monitorThread = new Thread(threadPoolMonitor);
            monitorThread.start();//运行监控线程

            startBtn.setText("暂停");
            threadStatus = 1;//线程状态切换为运行中
            appendMsg("搜索开始");

        }
        else if (threadStatus == 1){

            for (FirstSpider firstSpider : firstSpiderList){
                firstSpider.pause();
            }
            for (SecondSpider secondSpider : secondSpiderList){
                secondSpider.pause();
            }
            startBtn.setText("开始");
            threadStatus = 2;//线程状态切换为暂停
            appendMsg("暂停搜索");

        }else if (threadStatus == 2){

            for (FirstSpider firstSpider : firstSpiderList){
                firstSpider.start();
            }
            for (SecondSpider secondSpider : secondSpiderList){
                secondSpider.start();
            }
            startBtn.setText("暂停");
            threadStatus = 1;//线程状态切换为运行中
            appendMsg("继续搜索");

        }
    }

    /**
     * 添加菜单栏
     */
    private void addMenuBar(){

        JMenuItem paraItem = new JMenuItem("参数配置");
        paraItem.addActionListener(e -> ParameterDialog.showDialog());

        JMenu menu = new JMenu("高级");
        menu.add(paraItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);

        setJMenuBar(menuBar);
    }

    public void setFirstSpiderCount(int firstSpiderCount) {
        this.firstSpiderCount = firstSpiderCount;
    }

    public void setFirstSpiderIpCount(int firstSpiderIpCount) {
        this.firstSpiderIpCount = firstSpiderIpCount;
    }

    public void setSecondSpiderCount(int secondSpiderCount) {
        this.secondSpiderCount = secondSpiderCount;
    }

    public void setResourceSpiderUrlCount(int resourceSpiderUrlCount) {
        this.resourceSpiderUrlCount = resourceSpiderUrlCount;
    }

    public int getResourceSpiderCount() {
        return resourceSpiderCount;
    }

    public void setResourceSpiderCount(int resourceSpiderCount) {
        this.resourceSpiderCount = resourceSpiderCount;
    }

    public int getFirstSpiderCount() {
        return firstSpiderCount;
    }

    public int getFirstSpiderIpCount() {
        return firstSpiderIpCount;
    }

    public int getSecondSpiderUrlCount() {
        return secondSpiderUrlCount;
    }

    public void setSecondSpiderUrlCount(int secondSpiderUrlCount) {
        this.secondSpiderUrlCount = secondSpiderUrlCount;
    }

    public int getSecondSpiderCount() {
        return secondSpiderCount;
    }

    public int getResourceSpiderUrlCount() {
        return resourceSpiderUrlCount;
    }

    public int getInterval() {
        return interval;
    }

    public void setSleepTime(int interval) {
        this.interval = interval;
    }

    /**
     * 更新ip数显示
     */
    public synchronized void countIpCountTField(){

        ipCount ++;
        ipCountTField.setText(ipCount + "");

    }

    /**
     * 更新url数显示
     */
    public synchronized void countUrlCountTField(){

        urlCount ++;
        urlCountTField.setText(urlCount + "");

    }

    /**
     * 更新资源数显示
     */
    public synchronized void countResourceTField(){

        resourceCount ++;
        resourceCountTField.setText(resourceCount + "");

    }

    /**
     * 更新消息面板
     * @param msg 消息
     */
    public synchronized void appendMsg(String msg){

        msgTArea.append(msg + "\n");
        msgTArea.setCaretPosition(msgTArea.getText().length());

    }

    public String[] getKeywordArray() {
        return keywordArray;
    }

    public void setKeywordArray(String[] keywordArray) {
        this.keywordArray = keywordArray;
    }
}