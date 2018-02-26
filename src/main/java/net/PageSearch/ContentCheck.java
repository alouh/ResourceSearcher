package net.PageSearch;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Objects;

/**
 * @Author: HanJiafeng
 * @Date: 14:29 2018/2/24
 * @Desc:
 */
public class ContentCheck {

    /**
     * 检测是否包含关键字
     * @param socketAddress 地址
     * @return 是否包含关键字
     */
    public boolean check(InetSocketAddress socketAddress,String[] keywordArray){

        String page = getPage(socketAddress);

        for (String keyword : keywordArray){
            if (page.contains(keyword)){
                return true;
            }
        }

        return false;

    }
    /**
     * 使用Socket发送请求模拟http访问
     * @param socketAddress 目标地址
     * @return 目标页面内容
     */
    private String getPage(InetSocketAddress socketAddress){

        String page = "";
        Socket socket = new Socket();
        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            socket.connect(socketAddress,1000);
            socket.setSoTimeout(5 * 1000);
            outputStream = socket.getOutputStream();
            sendPacket(outputStream,socketAddress);//发送请求报文
            inputStream = socket.getInputStream();
            page = receivePacket(inputStream);
        }catch (SocketTimeoutException ignored){
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                Objects.requireNonNull(outputStream).close();
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                Objects.requireNonNull(inputStream).close();
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                Objects.requireNonNull(socket).close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return page;
    }

    private void sendPacket(OutputStream outputStream,InetSocketAddress socketAddress){

        // 指定请求方式 地址 注意空格
        StringBuilder sb = new StringBuilder();
        // 以下为请求头
        sb.append("GET / HTTP/1.1\r\n");
        sb.append("Host: ").append(socketAddress.getHostName()).append("\r\n");
        sb.append("Connection: keep-alive\r\n");
        sb.append("Upgrade-Insecure-Requests: 1\r\n");
        sb.append("User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3298.4 Safari/537.36\r\n");
        sb.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n");
        sb.append("Accept-Encoding: \r\n");
        sb.append("Accept-Language: zh-CN,zh;q=0.9\r\n");
        // 注意这里要换行结束请求头
        sb.append("\r\n");

        try {
            outputStream.write(sb.toString().getBytes());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 接收数据报文
     * @param inputStream 输入流
     */
    private String receivePacket(InputStream inputStream) throws Exception{

        StringBuilder stringBuilder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        String line;
        while ((line = reader.readLine()) != null){
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }
}
