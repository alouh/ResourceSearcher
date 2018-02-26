package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

/**
 * @Author: HanJiafeng
 * @Date: 15:50 2018/2/24
 * @Desc:
 */
public class PageUrlDao {

    /**
     * 插入URL
     * @param connection 连接
     * @param url url
     * @param depth 深度
     */
    public static void insertURL(Connection connection, String url,int depth){

        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO page_url (PU_LINK, PU_DEPTH) VALUE ('" + url + "'," + depth + ")");
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

    /**
     * 统计可用url数
     * @param connection 数据库连接
     * @return 可用url数
     */
    public static int countAvailableUrl(Connection connection){

        int count = 0;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT count(*) FROM page_url WHERE PU_FLAG = 0");
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
