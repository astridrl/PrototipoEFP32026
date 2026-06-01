/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.modeloCarreras;
import Controlador.Controlador.Carreras.clsCarreras;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author astri
 */
public class CarrerasDAO {
    
//sql
    private static final String SQL_SELECT = "SELECT codigo_carrera, nombre_carrera, codigo_facultad, estatus_carrera FROM carreras";

    private static final String SQL_INSERT = "INSERT INTO carreras (nombre_carrera, codigo_facultad, estatus_carrera) " + "VALUES (?, ?, ?)";

    private static final String SQL_UPDATE ="UPDATE carreras SET nombre_carrera = ?, codigo_facultad = ?, " + "estatus_carrera = ?" + "WHERE codigo_carrera = ?";

    private static final String SQL_DELETE = "DELETE FROM carreras WHERE codigo_carrera = ?";

    private static final String SQL_QUERY_POR_CODIGO = "SELECT codigo_carrera, nombre_carrera, codigo_facultad, estatus_carrera" + "FROM carreras WHERE codigo_carrera = ?";

    private static final String SQL_QUERY_POR_NOMBRE = "SELECT codigo_carrera, nombre_carrera, codigo_facultad, estatus_carrera" + "FROM carreras WHERE nombre_carrera LIKE ?";

    private static final String SQL_QUERY_POR_NIT ="SELECT codigo_carrera, nombre_carrera, codigo_facultad, estatus_carrera"+ "FROM carreras WHERE codigo_facultad = ?";

    private static final String SQL_QUERY_POR_ESTADO ="SELECT codigo_carrera, nombre_carrera, codigo_facultad, estatus_carrera"+ "FROM carreras WHERE Acreestado = ?";

    // INSERT

    public int ingresaCarreras(clsCarreras carreras) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, carreras.getnombre_carrera());
            stmt.setString(2, carreras.getcodigo_facultad());
            stmt.setString(3, carreras.getestatus_carrera());
         

            System.out.println("Ejecutando query: " + SQL_INSERT);
            rows = stmt.executeUpdate();
            System.out.println("Registros afectados: " + rows);

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    // SELECT todos
    public List<clsCarreras> consultaCarreras() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<clsCarreras> lista = new ArrayList<>();

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);

            System.out.println("Ejecutando query: " + SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {
                clsCarreras a = new clsCarreras(
                    rs.getString("codigo_carrera"),
                    rs.getString("nombre_carrera"),
                    rs.getString("codigo_facultad"),
                    rs.getString("estatus_carrera")
                );
                lista.add(a);
            }
            System.out.println("Registros encontrados: " + lista.size());

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return lista;
    }


   
    // UPDATE
    public int actualizaCarrera(clsCarreras carreras) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, carreras.getnombre_carrera());
            stmt.setString(2, carreras.getcodigo_facultad());
            stmt.setString(3, carreras.getestatus_carrera());
        

            System.out.println("Ejecutando query: " + SQL_UPDATE);
            rows = stmt.executeUpdate();
            System.out.println("Registros afectados: " + rows);

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    //DELETE
    public int borraCarrera(clsCarreras carreras) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setString(1, carreras.getcodigo_carrera());

            System.out.println("Ejecutando query: " + SQL_DELETE);
            rows = stmt.executeUpdate();
            System.out.println("Registros afectados: " + rows);

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

}
