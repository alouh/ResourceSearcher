package dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IPConvector;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: HanJiafeng
 * @Date: 9:29 2018/2/24
 * @Desc: 端口可达IP表
 */
public class ServiceIpDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceIpDao.class);

    /**
     * 插入可达IP和端口
     * @param connection 连接
     * @param ip 可达ip
     * @param port 可达端口
     */
    public static void insertIp(Connection connection,Long ip,int port){

        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO service_ip(SI_IP, SI_PORT, SI_FLAG) VALUE (" + ip + "," + port + ",0)");
            LOGGER.info("插入端口:" + port + "可达IP:" + ip + "成功");
        }catch (Exception e){
            LOGGER.error("插入端口:" + port + "可达IP:" + ip + "失败:" + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                Objects.requireNonNull(statement).close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取指定数量的SocketAddress
     * @param connection 连接
     * @param secondSpiderAddressCount 二级蜘蛛单次任务量
     * @return 套接字地址list
     */
    public static List<InetSocketAddress> getSocketAddress(Connection connection, int secondSpiderAddressCount){

        Statement statement = null;
        ResultSet resultSet = null;
        List<InetSocketAddress> socketAddressList = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT SI_IP,SI_PORT FROM service_ip WHERE SI_FLAG = 0 LIMIT 0," + secondSpiderAddressCount);
            while (resultSet.next()){
                Long ip = resultSet.getLong(1);
                int port = resultSet.getInt(2);
                InetSocketAddress socketAddress = new InetSocketAddress(ip + "",port);
                socketAddressList.add(socketAddress);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                Objects.requireNonNull(resultSet).close();
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                Objects.requireNonNull(statement).close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return socketAddressList;
    }

    /**
     * 删除使用过的地址(伪删除)
     * @param connection 连接
     * @param socketAddressList 用过的地址
     */
    public static void deleteUsed(Connection connection,List<InetSocketAddress> socketAddressList){

        Statement statement = null;

        StringBuilder sqlSB = new StringBuilder("UPDATE service_ip SET SI_FLAG = 1 WHERE ");
        for (InetSocketAddress socketAddress : socketAddressList){

            long ip = IPConvector.ipToNum(socketAddress.getAddress().getHostName());
            int port = socketAddress.getPort();

            sqlSB.append("SI_IP = ").append(ip).append(" AND SI_PORT = ").append(port).append(" OR ");
        }
        String sqlStr = sqlSB.substring(0,sqlSB.length() - 4);

        try {
            statement = connection.createStatement();

            statement.executeUpdate(sqlStr);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                Objects.requireNonNull(statement).close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static int countAvailableIp(Connection connection){

        Statement statement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT count(*) FROM service_ip WHERE SI_FLAG = 0");
            while (resultSet.next()){
                count = resultSet.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                Objects.requireNonNull(resultSet).close();
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                Objects.requireNonNull(statement).close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return count;
    }

}
