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

    public void init(Connection con) throws DaoException {
        try {
            this.con = con;
            save = con.prepareStatement(SAVE);
            saveCars = con.prepareStatement(CarDao.SAVE);
            getById = con.prepareStatement(GET_BY_ID);
            updateById = con.prepareStatement(UPDATE_BY_ID);
            deleteById = con.prepareStatement(DELETE_BY_ID);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void close() throws DaoException {
        DaoException exc = null;
        exc = close(save, exc);
        exc = close(saveCars, exc);
        exc = close(getById, exc);
        exc = close(updateById, exc);
        exc = close(deleteById, exc);
        exc = close(con, exc);
        if (exc != null) {
            throw exc;
        }
    }

    private DaoException close(AutoCloseable res, DaoException common) {
        try {
            if (res != null) {
                res.close();
            }
            return common;
        } catch (Exception e) {
            if (common == null) {
                common = new DaoException(e);
            } else {
                common.addSuppressed(e);
            }
            return common;
        }
    }

    public void save(Owner owner) throws DaoException {
        try {
            save.setString(1, owner.getFirstName());
            save.setString(2, owner.getLastName());
            save.setDate(3, owner.getBirthDate());
            save.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void saveWithCars(Owner owner) throws DaoException {
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
            DaoException exc = new DaoException(e);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException e1) {
                exc.addSuppressed(e1);
            }
            throw exc;
        }
    }

    public Owner getById(int id) throws DaoException {
        DaoException exc = null;
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
        }  catch (SQLException e) {
            exc = new DaoException(e);
            throw exc;
        } finally {
            exc = close(set, exc);
            if (exc != null) {
                throw exc;
            }
        }
    }

    public void updateById(Owner owner) throws DaoException {
        try {
            updateById = con.prepareStatement(UPDATE_BY_ID);
            updateById.setString(1, owner.getFirstName());
            updateById.setString(2, owner.getLastName());
            updateById.setDate(3, owner.getBirthDate());
            updateById.setInt(4, owner.getId());
            updateById.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void deleteById(int id) throws DaoException {
        try {
            deleteById.setInt(1, id);
            deleteById.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
