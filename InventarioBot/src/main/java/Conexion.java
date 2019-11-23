import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    String url = "jdbc:mysql://localhost:3306/inventario_db?serverTimezone=UTC";
    String user = "root";
    String password = "";
    Connection conexion = null;

    public Conexion()
    {
        try {
            conexion = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection obtenerConexion()
    {
        return conexion;
    }

    public void CerrarConexion()
    {
        try{
            if(!conexion.isClosed())
            {
                conexion.close();
                conexion = null;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
