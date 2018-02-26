package Service.impl;

import Service.ServiceIpService;
import dao.DataSourceConfig;
import dao.ServiceIpDao;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: HanJiafeng
 * @Date: 9:38 2018/2/24
 * @Desc:
 */
public class ServiceIpImpl implements ServiceIpService {

    private static final Object syn = false;//同步锁标志

    @Override
    public void insertIp(Long ip,int port) {

        Connection connection = null;
        try {
            synchronized (syn) {
                connection = DataSourceConfig.DATA_SOURCE.getConnection();//获取连接

                ServiceIpDao.insertIp(connection, ip, port);//插入ip端口
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                Objects.requireNonNull(connection).close();//归还连接
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<InetSocketAddress> getSocketAddresses(int secondSpiderAddressCount) {

        Connection connection = null;
        List<InetSocketAddress> socketAddressList = new ArrayList<>();
        try {
            synchronized (syn) {
                connection = DataSourceConfig.DATA_SOURCE.getConnection();

                socketAddressList = ServiceIpDao.getSocketAddress(connection,secondSpiderAddressCount);//获得地址池
                if (!socketAddressList.isEmpty()) {
                    ServiceIpDao.deleteUsed(connection, socketAddressList);//删除使用过的地址
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                Objects.requireNonNull(connection).close();//归还连接
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return socketAddressList;
    }

    @Override
    public int countAvailableIp() {

        Connection connection = null;
        int count = 0;
        try {
            synchronized (syn) {
                connection = DataSourceConfig.DATA_SOURCE.getConnection();

                count = ServiceIpDao.countAvailableIp(connection);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                Objects.requireNonNull(connection).close();//归还连接
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return count;
    }
}
