package by.training.dao;

import by.training.dao.utils.MySqlUtil;
import by.training.dao.utils.StatementUtil;
import by.training.model.Owner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CarDao {

    private static CarDao instance = new CarDao();

    private MySqlUtil sqlUtil = MySqlUtil.getInstance();
    private static StatementUtil statementUtil = StatementUtil.getInstance();

    private CarDao() {
    }

    public static CarDao getInstance() {
        return instance;
    }

    public void saveOwner(Owner owner) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlUtil.getConnection();
            ps = statementUtil.getPStatement(con, StatementUtil.SAVE_OWNER);

            ps.setString(1, owner.getFirstName());
            ps.setString(2, owner.getLastName());
            ps.setDate(3, owner.getBirthDate());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
