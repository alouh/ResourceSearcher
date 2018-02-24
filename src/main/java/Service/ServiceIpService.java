package Service;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Author: HanJiafeng
 * @Date: 9:38 2018/2/24
 * @Desc:
 */
public interface ServiceIpService {

    void insertIp(Long ip,int port);

    List<InetSocketAddress> getSocketAddresses();
}
