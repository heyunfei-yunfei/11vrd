package cn.tedu.utils;

import com.alibaba.druid.pool.DruidDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DBUtils {
    private static DruidDataSource ds;

    static {
        //读取jdbc.properties里面的数据
        Properties p = new Properties();
        //获取文件输入流
        InputStream ips = DBUtils.class.getClassLoader()
                .getResourceAsStream("jdbc.properties");
        try {
            p.load(ips);//文件和对象关联
        } catch (IOException e) {
            e.printStackTrace();
        }
        //读取数据
        String driver = p.getProperty("db.driver");
        String url = p.getProperty("db.url");
        String username = p.getProperty("db.username");
        String password = p.getProperty("db.password");
        //创建连接池对象
        ds = new DruidDataSource();
        //设置连接池信息
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        //读取配置文件中的最大连接数量和初始连接数量
        String maxSize = p.getProperty("db.maxActive");
        String initSize = p.getProperty("db.initialSize");
        ds.setMaxActive(Integer.parseInt(maxSize));
        ds.setInitialSize(Integer.parseInt(initSize));
    }
    public static Connection getConn() throws Exception {

        //获取连接
        Connection conn = ds.getConnection();
        return conn;
    }
}
