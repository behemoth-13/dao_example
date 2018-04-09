package by.training.dao;

import by.training.model.Car;
import by.training.model.Owner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OwnerDao {

    private static final String SAVE = "INSERT INTO owner(first_name, last_name, birth_date) VALUES (?, ?, ?)";
    private static final String GET_BY_ID = "SELECT first_name, last_name, birth_date FROM owner WHERE id = ?";
    private static final String UPDATE_BY_ID = "UPDATE owner SET first_name = ?, last_name = ?, birth_date = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM owner WHERE id = ?";

    private Connection con;

    private PreparedStatement save;
    private PreparedStatement saveCars;
    private PreparedStatement getById;
    private PreparedStatement updateById;
    private PreparedStatement deleteById;

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_BIRTH_DATE = "birth_date";

    private MySqlUtil sqlUtil = MySqlUtil.getInstance();

    public void init(Connection con) throws SQLException {
        this.con = con;
        save = con.prepareStatement(SAVE);
        saveCars = con.prepareStatement(CarDao.SAVE);
        getById = con.prepareStatement(GET_BY_ID);
        updateById = con.prepareStatement(UPDATE_BY_ID);
        deleteById = con.prepareStatement(DELETE_BY_ID);
    }

    public void close() throws SQLException {
        String message = "";
        try {
            save.close();
        } catch (SQLException e) {
            message += e.getMessage() + "\n";
        }
        try {
            saveCars.close();
        } catch (SQLException e) {
            message += e.getMessage() + "\n";
        }
        try {
            getById.close();
        } catch (SQLException e) {
            message += e.getMessage() + "\n";
        }
        try {
            updateById.close();
        } catch (SQLException e) {
            message += e.getMessage() + "\n";
        }
        try {
            deleteById.close();
        } catch (SQLException e) {
            message += e.getMessage() + "\n";
        }
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            message += e.getMessage();
        }
        if (!message.isEmpty()) {
            throw new SQLException(message);
        }
    }

    public void save(Owner owner) throws SQLException {
        save.setString(1, owner.getFirstName());
        save.setString(2, owner.getLastName());
        save.setDate(3, owner.getBirthDate());
        save.execute();
    }

    public void saveWithCars(Owner owner) throws SQLException {
        try {
            con.setAutoCommit(false);
            save.setString(1, owner.getFirstName());
            save.setString(2, owner.getLastName());
            save.setDate(3, owner.getBirthDate());
            save.execute();

            List<Car> cars = owner.getCars();
            if (cars != null && cars.size() > 0) {
                for (Car car : cars) {
                    saveCars.setInt(1, car.getOwnerId());
                    saveCars.setDate(2, car.getManufactureDate());
                    saveCars.setString(3, car.getBrand());
                    saveCars.setString(4, car.getModel());
                    saveCars.execute();
                }
            }
            con.commit();
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            throw e;
        }
    }

    public Owner getById(int id) throws SQLException {
        ResultSet set = null;
        try {
            getById.setInt(1, id);
            set = getById.executeQuery();

            if (set.next()) {
                Owner owner = new Owner();
                owner.setId(set.getInt(COLUMN_ID));
                owner.setFirstName(set.getString(COLUMN_FIRST_NAME));
                owner.setLastName(set.getString(COLUMN_LAST_NAME));
                owner.setBirthDate(set.getDate(COLUMN_BIRTH_DATE));
                return owner;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
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

    public void updateById(Owner owner) throws SQLException {
        updateById = con.prepareStatement(UPDATE_BY_ID);
        updateById.setString(1, owner.getFirstName());
        updateById.setString(2, owner.getLastName());
        updateById.setDate(3, owner.getBirthDate());
        updateById.setInt(4, owner.getId());
        updateById.executeUpdate();
    }

    public void deleteById(int id) throws SQLException {
        deleteById.setInt(1, id);
        deleteById.execute();
    }
}
