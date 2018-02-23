package net.IpSearch;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Objects;

/**
 * @Author: HanJiafeng
 * @Date: 16:58 2018/2/23
 * @Desc:
 */
public class ServiceCheck {

    /**
     * 检测该主机的80服务有没有打开
     * @param ip 待检测IP
     * @return 检测结果
     */
    public boolean isAvailable(Long ip){

        Socket socket = null;
        boolean isAva = false;

        try {
            socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(ip + "",80);

            socket.connect(socketAddress,500);//指定超时时间
            isAva = true;
        }catch (Exception e){
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
