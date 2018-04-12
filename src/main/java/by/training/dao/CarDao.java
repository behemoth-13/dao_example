package by.training.dao;

import by.training.model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDao {
    private static final String ID = "id";
    private static final String OWNER_ID = "owner_id";
    private static final String MANUFACTURE_DATE = "manufacture_date";
    private static final String BRAND = "brand";
    private static final String MODEL = "model";

    private static final String SAVE = "INSERT INTO car(" + OWNER_ID + ", " + MANUFACTURE_DATE + ", " + BRAND + ", " +
            MODEL + ") VALUES(%d, %tF, %s, %s)";
    private static final String GET_ALL = "SELECT " + ID +" , " + OWNER_ID + ", " + MANUFACTURE_DATE + ", " + BRAND +
            ", " + MODEL + " FROM car";
    private static final String UPDATE_BY_ID = "UPDATE car SET "+ OWNER_ID +" = %d, " + MANUFACTURE_DATE + " = %tF, " +
            BRAND + " = %s, " + MODEL + " = %s WHERE " + ID + " = %d";
    private static final String DELETE_BY_ID = "DELETE FROM car WHERE " + ID + " = %d";

    private MySqlUtil util = MySqlUtil.getInstance();



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
                car.setId(set.getInt(ID));
                car.setOwnerId(set.getInt(OWNER_ID));
                car.setManufactureDate(set.getDate(MANUFACTURE_DATE));
                car.setBrand(set.getString(BRAND));
                car.setModel(set.getString(MODEL));
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