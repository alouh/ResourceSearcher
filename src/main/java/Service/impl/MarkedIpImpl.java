package Service.impl;

import Service.MarkedIpService;
import dao.DataSourceConfig;
import dao.MarkedIpDao;
import utils.ThreadParameter;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Author: HanJiafeng
 * @Date: 16:48 2018/2/23
 * @Desc:
 */
public class MarkedIpImpl implements MarkedIpService {

    @Override
    public synchronized Long getIp(int id,int ipCount) {

        Long ip = 0L;
        Connection connection = null;

        try {
            connection = DataSourceConfig.DATA_SOURCE.getConnection();//获取连接

            ip = MarkedIpDao.getIp(connection,id);//获取IP地址

            Long newIp = ip + ipCount;//计算新的IP
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());//获取时间戳

            MarkedIpDao.updateIp(connection,newIp,ip,timestamp);//更新
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                Objects.requireNonNull(connection).close();//释放连接
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return ip;
    }
}
