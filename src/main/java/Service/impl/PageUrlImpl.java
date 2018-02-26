package Service.impl;

import Service.PageUrlService;
import dao.DataSourceConfig;
import dao.PageUrlDao;

import java.sql.Connection;
import java.util.Objects;

/**
 * @Author: HanJiafeng
 * @Date: 15:55 2018/2/24
 * @Desc:
 */
public class PageUrlImpl implements PageUrlService {

    @Override
    public void insertUrl(String url, int depth) {

        Connection connection = null;

        try {
            connection = DataSourceConfig.DATA_SOURCE.getConnection();

            PageUrlDao.insertURL(connection,url,depth);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                Objects.requireNonNull(connection).close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public int countAvailableUrl() {

        int count = 0;
        Connection connection = null;

        try {
            connection = DataSourceConfig.DATA_SOURCE.getConnection();

            count = PageUrlDao.countAvailableUrl(connection);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                Objects.requireNonNull(connection).close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return count;
    }
}
