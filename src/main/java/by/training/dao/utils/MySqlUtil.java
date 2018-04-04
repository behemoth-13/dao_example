package by.training.dao.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlUtil {
    private static MySqlUtil instance = new MySqlUtil();

    private String url;
    private String login;
    private String password;

    private MySqlUtil() {}

    public void init(String path) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, IOException {
        InputStream inputStream = getClass().getResourceAsStream("/db.properties");
        Properties props = new Properties();
        props.load(inputStream);
        this.url = props.getProperty("url");
        this.login = props.getProperty("login");
        this.password = props.getProperty("password");
        String driverName = props.getProperty("driverName");
        Driver driver = (Driver) Class.forName(driverName).newInstance();
        DriverManager.registerDriver(driver);
        DriverManager.getConnection(url, login, password);
    }

    public static MySqlUtil getInstance() {
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, login, password);
    }
}