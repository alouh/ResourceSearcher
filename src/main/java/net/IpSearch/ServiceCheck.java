package net.IpSearch;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.Objects;

/**
 * @Author: HanJiafeng
 * @Date: 16:58 2018/2/23
 * @Desc: 检测主机端口是否可达
 */
public class ServiceCheck {

    /**
     * 检测该主机的80服务有没有打开
     * @param ip 待检测IP
     * @param port 检测端口
     * @return 检测结果
     */
    public static boolean isAvailable(Long ip,int port){

        Socket socket = null;
        boolean isAva = false;

        try {
            socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(ip + "",port);

            socket.connect(socketAddress,500);//指定超时时间
            isAva = true;
        }catch (SocketTimeoutException ignore){
            //忽略超时信息
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                Objects.requireNonNull(socket).close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return isAva;
    }
}
