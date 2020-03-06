package loginapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbUtil.dbConnection;

public class LoginModel {
    Connection connection;

    public LoginModel() {
        try {
            this.connection = dbConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (this.connection == null) {
            System.exit(1);
        }
    }

    public boolean isDatabaseConnected() {
        return this.connection != null;
    }


    //il seguente metodo è utilizzato per l'interrogazione del database al momento del login con username e passw + option(admin/studente)

    public boolean isLogin(String user, String pass, String opt) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM login where username = ? and password = ? and division = ?";

        try {
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, user);
            pr.setString(2, pass);
            pr.setString(3, opt);

            rs = pr.executeQuery();

            return rs.next();
        } catch (SQLException ex) {
            return false;
        } finally {//finally è sempre eseguito
            assert pr != null;
            pr.close();
            assert rs != null;
            rs.close();
        }
    }
}