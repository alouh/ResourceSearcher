package Service.impl;

import Service.MarkedIpService;
import dao.DataSourceConfig;
import dao.MarkedIp;
import utils.ThreadParameter;

import java.sql.Connection;
import java.sql.Timestamp;

/**
 * @Author: HanJiafeng
 * @Date: 16:48 2018/2/23
 * @Desc:
 */
public class MarkedIpImpl implements MarkedIpService {

    @Override
    public synchronized Long getIp(int id) {

        Long ip = 0L;

        try {
            Connection connection = DataSourceConfig.DATA_SOURCE.getConnection();//获取连接

            ip = MarkedIp.getIp(connection,id);//获取IP地址

            Long newIp = ip + ThreadParameter.getFirstSpider_IpCount();//计算新的IP
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());//获取时间戳

            MarkedIp.updateIp(connection,newIp,timestamp);//更新
        }catch (Exception e){
            e.printStackTrace();
        }

        return ip;
    }
}
