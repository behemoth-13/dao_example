package by.training.dao;

import by.training.dao.utils.MySqlUtil;
import by.training.dao.utils.Queries;
import by.training.model.Car;
import by.training.model.Owner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OwnerDao {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_BIRTH_DATE = "birth_date";

    private static OwnerDao instance = new OwnerDao();

    private MySqlUtil sqlUtil = MySqlUtil.getInstance();

    private OwnerDao() {}

    public static OwnerDao getInstance() {
        return instance;
    }

    public void save(Owner owner) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlUtil.getConnection();
            ps = con.prepareStatement(Queries.SAVE_OWNER);

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

    public void saveWithCars(Owner owner) throws DaoException {
        Connection con = null;
        PreparedStatement psOwner = null;
        PreparedStatement psCar = null;
        try {
            con = sqlUtil.getConnection();
            con.setAutoCommit(false);
            psOwner = con.prepareStatement(Queries.SAVE_OWNER);

            psOwner.setString(1, owner.getFirstName());
            psOwner.setString(2, owner.getLastName());
            psOwner.setDate(3, owner.getBirthDate());
            psOwner.execute();

            List<Car> cars = owner.getCars();
            if (cars != null && cars.size() > 0) {
                psCar = con.prepareStatement(Queries.SAVE_OWNER);

                for (Car car : cars) {
                    psCar.setInt(1, car.getOwnerId());
                    psCar.setDate(2, car.getManufactureDate());
                    psCar.setString(3, car.getBrand());
                    psCar.setString(4, car.getModel());
                    psCar.execute();
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
            throw new DaoException(e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            try {
                if (psOwner != null) {
                    psOwner.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (psCar != null) {
                    psCar.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Owner getById(int id) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet set = null;
        try {

            con = sqlUtil.getConnection();
            ps = con.prepareStatement(Queries.GET_OWNER_BY_ID);

            ps.setInt(1, id);
            set = ps.executeQuery();

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

    public void updateById(Owner owner) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlUtil.getConnection();
            ps = con.prepareStatement(Queries.UPDATE_OWNER_BY_ID);

            ps.setString(1, owner.getFirstName());
            ps.setString(2, owner.getLastName());
            ps.setDate(3, owner.getBirthDate());
            ps.setInt(4, owner.getId());
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
            ps = con.prepareStatement(Queries.DELETE_OWNER_BY_ID);

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
