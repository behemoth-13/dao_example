package by.training.dao;

import by.training.dao.utils.MySqlUtil;
import by.training.dao.utils.Queries;
import by.training.model.Owner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CarDao {

    private static CarDao instance = new CarDao();

    private MySqlUtil sqlUtil = MySqlUtil.getInstance();

    private CarDao() {
    }

    public static CarDao getInstance() {
        return instance;
    }


}
