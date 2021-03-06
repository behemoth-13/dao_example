package by.training.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlUtil {
    private static volatile MySqlUtil instance;

    private ComboPooledDataSource dataSource;

    public static MySqlUtil getInstance(String filename) throws IOException {
        MySqlUtil localInstance = instance;
        if (localInstance == null) {
            synchronized (MySqlUtil.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MySqlUtil(filename);
                }
            }
        }
        return localInstance;
    }

    private MySqlUtil(String filename) throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream(filename);
        Properties props = new Properties();
        props.load(inputStream);
        String url = props.getProperty("url");
        String login = props.getProperty("login");
        String password = props.getProperty("password");
        int initialPoolSize = Integer.parseInt(props.getProperty("initialPoolSize"));
        int minPoolSize = Integer.parseInt(props.getProperty("minPoolSize"));
        int acquireIncrement = Integer.parseInt(props.getProperty("acquireIncrement"));
        int maxPoolSize = Integer.parseInt(props.getProperty("maxPoolSize"));
        int maxStatements = Integer.parseInt(props.getProperty("maxStatements"));

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUser(login);
        dataSource.setPassword(password);

        // Optional Settings
        dataSource.setInitialPoolSize(initialPoolSize);
        dataSource.setMinPoolSize(minPoolSize);
        dataSource.setAcquireIncrement(acquireIncrement);
        dataSource.setMaxPoolSize(maxPoolSize);
        dataSource.setMaxStatements(maxStatements);

        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void destroy() {
        dataSource.close();
    }
}