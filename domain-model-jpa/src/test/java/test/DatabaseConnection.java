package test;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;

public class DatabaseConnection {

    static {
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static final String URL = "jdbc:mysql://dbserver.alunos.di.fc.ul.pt:3306/css000";
    public static final String USER = "css000";
    public static final String PASSWORD = "css000";

    public static final Destination DESTINATION = new DriverManagerDestination(URL, USER, PASSWORD);

}
