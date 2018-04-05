package by.training.dao.utils;

public class Queries {
    //Owner
    public static final String SAVE_OWNER = "INSERT INTO owner(first_name, last_name, birth_date) VALUES (?, ?, ?)";
    public static final String GET_OWNER_BY_ID = "SELECT first_name, last_name, birth_date FROM owner WHERE id = ?";
    public static final String UPDATE_OWNER_BY_ID = "UPDATE owner SET first_name = ?, last_name = ?, birth_date = ? WHERE id = ?";
    public static final String DELETE_OWNER_BY_ID = "DELETE FROM owner WHERE id = ?";
    //Car
    public static final String SAVE_CAR = "INSERT INTO car(id_owner, manufacture_date, brand, model) VALUES(?, ?, ?, ?)";
}
