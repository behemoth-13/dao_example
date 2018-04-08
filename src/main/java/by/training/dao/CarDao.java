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
    private static final String GET_ALL = "SELECT * FROM car";
    private static final String UPDATE_BY_ID = "UPDATE car SET owner_id = ?, manufacture_date = ?, brand = ?, model = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM car WHERE id = ?";

    private Connection con;

    private PreparedStatement save;
    private PreparedStatement getAll;
    private PreparedStatement updateById;
    private PreparedStatement deleteById;

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_OWNER_ID = "owner_id";
    private static final String COLUMN_MANUFACTURE_DATE = "manufacture_date";
    private static final String COLUMN_BRAND = "brand";
    private static final String COLUMN_MODEL = "model";

    public void init(Connection con) throws SQLException {
        this.con = con;
        save = con.prepareStatement(SAVE);
        getAll = con.prepareStatement(GET_ALL);
        updateById = con.prepareStatement(UPDATE_BY_ID);
        deleteById = con.prepareStatement(DELETE_BY_ID);
    }

    public void destroy() {
        try {
            save.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            getAll.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            updateById.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            deleteById.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Car car) throws DaoException {
        try {
            save.setInt(1, car.getOwnerId());
            save.setDate(2, car.getManufactureDate());
            save.setString(3, car.getBrand());
            save.setString(4, car.getModel());
            save.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e.getMessage());
        }
    }

    public List<Car> getAll() throws DaoException {
        ResultSet set = null;
        try {
            set = getAll.executeQuery();

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
        }
    }

    public void updateById(Car car) throws DaoException {
        try {
            updateById.setInt(1, car.getOwnerId());
            updateById.setDate(2, car.getManufactureDate());
            updateById.setString(3, car.getBrand());
            updateById.setString(4, car.getModel());
            updateById.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e.getMessage());
        }
    }

    public void deleteById(int id) throws DaoException {
        try {
            deleteById.setInt(1, id);
            deleteById.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e.getMessage());
        }
    }
}