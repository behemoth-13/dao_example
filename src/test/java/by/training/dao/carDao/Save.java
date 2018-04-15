package by.training.dao.carDao;

import by.training.dao.CarDao;
import by.training.dao.DaoException;
import by.training.dao.MySqlUtil;
import by.training.model.Car;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

public class Save {
    @Before
    public void insertOwner() throws IOException {
        MySqlUtil util = MySqlUtil.getInstance("testDb.properties");
        try (Connection con = util.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute("DELETE FROM car");
            stmt.execute("DELETE FROM owner");
            stmt.execute("INSERT INTO owner(id, first_name, last_name, birth_date) VALUES (1, 'firstName', 'last_name', '1999-10-10')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void clear() throws IOException {
        MySqlUtil util = MySqlUtil.getInstance("testDb.properties");
        try (Connection con = util.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute("DELETE FROM car");
            stmt.execute("DELETE FROM owner");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws IOException, DaoException {
        Car exp = new Car();
        exp.setId(1);
        exp.setOwnerId(1);
        exp.setManufactureDate(new Date());
        exp.setBrand("brand");
        exp.setModel("model");
        CarDao dao = new CarDao("testDb.properties");
        dao.save(exp);
        List<Car> list = dao.getAll();
        Assert.assertEquals(1, list.size());
        Car act = list.get(0);
        Assert.assertEquals(exp.getOwnerId(), act.getOwnerId());
        Assert.assertEquals(String.format("%tF", exp.getManufactureDate()), String.format("%tF", act.getManufactureDate().getTime()));
        Assert.assertEquals(exp.getBrand(), act.getBrand());
        Assert.assertEquals(exp.getModel(), act.getModel());
    }
}
