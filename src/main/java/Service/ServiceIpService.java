package Service;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Author: HanJiafeng
 * @Date: 9:38 2018/2/24
 * @Desc:
 */
public interface ServiceIpService {

    /**
     * 插入可用ip和端口
     * @param ip 可用ip
     * @param port 端口
     */
    void insertIp(Long ip,int port);

    /**
     * 获取指定数量的可用ip和端口
     * @param secondSpiderAddressCount 指定数量
     * @return 可用ip
     */
    List<InetSocketAddress> getSocketAddresses(int secondSpiderAddressCount);

    /**
     * 统计可用ip有多少
     * @return 可用ip数量
     */
    int countAvailableIp();
}
