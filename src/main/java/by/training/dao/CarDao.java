package by.training.dao;

import by.training.model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDao {
    private static final String SAVE = "INSERT INTO car(owner_id, manufacture_date, brand, model) VALUES(%d, %tF, %s, %s)";
    private static final String GET_ALL = "SELECT * FROM car";
    private static final String UPDATE_BY_ID = "UPDATE car SET owner_id = %d, manufacture_date = %tF, brand = %s, model = %s WHERE id = %d";
    private static final String DELETE_BY_ID = "DELETE FROM car WHERE id = %d";

    private MySqlUtil util = MySqlUtil.getInstance();

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_OWNER_ID = "owner_id";
    private static final String COLUMN_MANUFACTURE_DATE = "manufacture_date";
    private static final String COLUMN_BRAND = "brand";
    private static final String COLUMN_MODEL = "model";

    public void save(Car car) throws DaoException {
        try (Connection con = util.getConnection();
             Statement stmt = con.createStatement()) {

            String query = String.format(SAVE, car.getOwnerId(), car.getManufactureDate(), car.getBrand(), car.getModel());
            stmt.execute(query);
        } catch (SQLException e) {
            throw new DaoException("Error in save", e);
        }
    }

    public List<Car> getAll() throws DaoException {
        try (Connection con = util.getConnection();
             Statement stmt = con.createStatement();
             ResultSet set = stmt.executeQuery(GET_ALL)) {

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
            throw new DaoException("Error in getAll", e);
        }
    }

    public void updateById(Car car) throws DaoException {
        try (Connection con = util.getConnection();
             Statement stmt = con.createStatement()) {

            String query = String.format(UPDATE_BY_ID, car.getOwnerId(), car.getManufactureDate(), car.getBrand(), car.getModel(), car.getId());
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new DaoException("Error in updateById", e);
        }

    }

    public void deleteById(int id) throws DaoException {
        try (Connection con = util.getConnection();
             Statement stmt = con.createStatement()) {

            String query = String.format(DELETE_BY_ID, id);
            stmt.execute(query);
        } catch (SQLException e) {
            throw new DaoException("Error in deleteById", e);
        }
    }
}