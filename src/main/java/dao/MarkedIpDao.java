package dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Author: HanJiafeng
 * @Date: 16:27 2018/2/23
 * @Desc:
 */
public class MarkedIpDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarkedIpDao.class);

    /**
     * 根据主键ID查询IP
     * @param connection 数据库连接
     * @param id 主键Id
     * @return 查询到的ip
     */
    public static Long getIp(Connection connection,int id){

        Long ip = 0L;

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT MI_IP FROM marked_ip WHERE MI_ID = " + id);

            while (resultSet.next()){
                ip = resultSet.getLong(1);
            }
        }catch (Exception e){
            LOGGER.error("查询IP失败:" + e.getMessage());
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

        return ip;
    }

    /**
     * 更新IP
     * @param connection 连接
     * @param newIp 新标记IP
     * @param oldIp 被更改IP的IP
     * @param timestamp 时间戳
     */
    public static void updateIp(Connection connection, Long newIp, Long oldIp, Timestamp timestamp){

        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE marked_ip SET MI_IP = " + newIp + " , MI_TIME = '" + timestamp + "' WHERE MI_IP = " + oldIp);
            LOGGER.info("更新IP成功");
        }catch (Exception e){
            LOGGER.error("更新IP失败:" + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                Objects.requireNonNull(statement).close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
