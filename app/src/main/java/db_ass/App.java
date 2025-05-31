package db_ass;

import java.sql.SQLException;

import db_ass.data.DAOUtils;
import db_ass.model.Model;
import db_ass.view.Menu;

public final class App {

    public static void main(String[] args) throws SQLException {
        var connection = DAOUtils.localMySQLConnection("associazionesportiva", "root", "");
        var model = Model.fromConnection(connection);
        var view = new Menu();/* () -> {
            
            try {
                connection.close();
            } catch (Exception ignored) { }
        }); */

        var controller = new Controller(model, view);
        view.setController(controller);
    }
    
}
