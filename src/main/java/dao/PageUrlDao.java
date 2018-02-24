package dao;

import java.sql.Connection;
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
}
