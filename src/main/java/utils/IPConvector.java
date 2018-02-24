package utils;

/**
 * @Author: HanJiafeng
 * @Date: 14:20 2018/2/24
 * @Desc:
 */
public class IPConvector {

    public static String numToIP(long ip) {
        StringBuilder sb = new StringBuilder();
        for (int i = 3; i >= 0; i--) {
            sb.append((ip >>> (i * 8)) & 0x000000ff);
            if (i != 0) {
                sb.append('.');
            }
        }
        return sb.toString();

    }

    /**
     * 点分十进制转换为long
     * @param ip 点分十进制IP
     * @return long IP
     */
    public static long ipToNum(String ip) {
        long num = 0;
        String[] sections = ip.split("\\.");
        int i = 3;
        for (String str : sections) {
            num += (Long.parseLong(str) << (i * 8));
            i--;
        }
        return num;
    }
}
