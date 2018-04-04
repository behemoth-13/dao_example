package by.training.dao.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatementUtil {
    public static final String SAVE_OWNER = "INSERT INTO owner(first_name, last_name, birth_date) VALUES (?, ?, ?)";

    private static StatementUtil instance = new StatementUtil();

    private StatementUtil(){}

    public static StatementUtil getInstance() {
        return instance;
    }

    public PreparedStatement getPStatement(Connection con, String query) throws SQLException {
        return con.prepareStatement(query);
    }
}
