package by.training.dao;

import by.training.model.Owner;

import java.io.IOException;
import java.sql.*;

public class OwnerDao {

    private static final String ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String BIRTH_DATE = "birth_date";

    private static final String SAVE = "INSERT INTO owner(" + FIRST_NAME + ", " + LAST_NAME + ", " +
            BIRTH_DATE + ") VALUES (%s, %s, %tF)";
    private static final String GET_BY_ID = "SELECT " + FIRST_NAME + ", " + LAST_NAME + ", " +
            BIRTH_DATE + " FROM owner WHERE " + ID + " = %d";
    private static final String UPDATE_BY_ID = "UPDATE owner SET " + FIRST_NAME + " = %s, " + LAST_NAME +
            " = %s, " + BIRTH_DATE + " = %tF WHERE " + ID + " = %d";
    private static final String DELETE_BY_ID = "DELETE FROM owner WHERE " + ID + " = %d";

    private MySqlUtil util;

    public OwnerDao() throws IOException {
        util = MySqlUtil.getInstance("db.properties");
    }

    public OwnerDao(String filename) throws IOException {
        util = MySqlUtil.getInstance(filename);
    }

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
                owner.setId(set.getInt(ID));
                owner.setFirstName(set.getString(FIRST_NAME));
                owner.setLastName(set.getString(LAST_NAME));
                owner.setBirthDate(set.getDate(BIRTH_DATE));
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
