package Service;

/**
 * @Author: HanJiafeng
 * @Date: 15:54 2018/2/24
 * @Desc:
 */
public interface PageUrlService {

    void insertUrl(String url,int depth);

    int countAvailableUrl();
}
