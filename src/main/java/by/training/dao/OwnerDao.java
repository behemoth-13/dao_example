package by.training.dao;

import by.training.model.Owner;

import java.sql.*;

public class OwnerDao {

    private static final String SAVE = "INSERT INTO owner(first_name, last_name, birth_date) VALUES (%s, %s, %tF)";
    private static final String GET_BY_ID = "SELECT first_name, last_name, birth_date FROM owner WHERE id = %d";
    private static final String UPDATE_BY_ID = "UPDATE owner SET first_name = %s, last_name = %s, birth_date = %tF WHERE id = %d";
    private static final String DELETE_BY_ID = "DELETE FROM owner WHERE id = %d";

    private MySqlUtil util = MySqlUtil.getInstance();

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_BIRTH_DATE = "birth_date";

    public void save(Owner owner) throws DaoException {
        try (Connection con = util.getConnection();
             Statement stmt = con.createStatement()) {

            String query = String.format(SAVE, owner.getFirstName(), owner.getLastName(), owner.getBirthDate());
            stmt.execute(query);
        } catch (SQLException e) {
            throw new DaoException("Error in save",e);
        }
    }

    public Owner getById(int id) throws DaoException {
        String query = String.format(GET_BY_ID, id);
        try (Connection con = util.getConnection();
             Statement stmt = con.createStatement();
             ResultSet set = stmt.executeQuery(query)) {

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
            throw new DaoException("Error in getById", e);
        }
    }

    public void updateById(Owner owner) throws DaoException {
        try (Connection con = util.getConnection();
             Statement stmt = con.createStatement()) {

            String query = String.format(UPDATE_BY_ID, owner.getFirstName(), owner.getLastName(), owner.getBirthDate(), owner.getId());
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
