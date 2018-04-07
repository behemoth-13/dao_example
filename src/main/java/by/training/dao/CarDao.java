package by.training.dao;

import by.training.model.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDao {
    static final String SAVE = "INSERT INTO car(owner_id, manufacture_date, brand, model) VALUES(?, ?, ?, ?)";
    static final String GET_ALL = "SELECT * FROM car";
    static final String UPDATE_BY_ID = "UPDATE car SET owner_id = ?, manufacture_date = ?, brand = ?, model = ? WHERE id = ?";
    static final String DELETE_BY_ID = "DELETE FROM car WHERE id = ?";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_OWNER_ID = "owner_id";
    private static final String COLUMN_MANUFACTURE_DATE = "manufacture_date";
    private static final String COLUMN_BRAND = "brand";
    private static final String COLUMN_MODEL = "model";

    private MySqlUtil sqlUtil = MySqlUtil.getInstance();

    public void save(Car car) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlUtil.getConnection();
            ps = con.prepareStatement(SAVE);

            ps.setInt(1, car.getOwnerId());
            ps.setDate(2, car.getManufactureDate());
            ps.setString(3, car.getBrand());
            ps.setString(4, car.getModel());
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

    public List<Car> getAll() throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet set = null;
        try {
            con = sqlUtil.getConnection();
            ps = con.prepareStatement(GET_ALL);

            set = ps.executeQuery();

            List<Car> list = new ArrayList<>();
            while (set.next()) {
                Car car = new Car();
                car.setId(set.getInt(COLUMN_ID));
                car.setOwnerId(set.getInt(COLUMN_OWNER_ID));
                car.setManufactureDate(set.getDate(COLUMN_MANUFACTURE_DATE));
                car.setBrand(set.getString(COLUMN_BRAND));
                car.setModel(set.getString(COLUMN_MODEL));
               list.add(car);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e.getMessage());
        } finally {
            try {
                if (set != null) {
                    set.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
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

    public void updateById(Car car) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlUtil.getConnection();
            ps = con.prepareStatement(UPDATE_BY_ID);

            ps.setInt(1, car.getOwnerId());
            ps.setDate(2, car.getManufactureDate());
            ps.setString(3, car.getBrand());
            ps.setString(4, car.getModel());
            ps.executeUpdate();
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

    public void deleteById(int id) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlUtil.getConnection();
            ps = con.prepareStatement(DELETE_BY_ID);

            ps.setInt(1, id);
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
