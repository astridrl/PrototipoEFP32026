/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.modeloCuentasCorrientes;
import Controlador.controladorCuentasCorrientes.clsAcreedor;
import Controlador.controladorCuentasCorrientes.clsCompras;
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
public class AcreedorDAO {
    
//sql
    private static final String SQL_SELECT = "SELECT Acrecodigo, Acrenombre, Acrenit, Acrecuentabancaria, Acreestado " + "FROM acreedores ORDER BY Acrenombre";

    private static final String SQL_INSERT = "INSERT INTO acreedores (Acrenombre, Acrenit, Acrecuentabancaria, Acreestado) " + "VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE ="UPDATE acreedores SET Acrenombre = ?, Acrenit = ?, " + "Acrecuentabancaria = ?, Acreestado = ? " + "WHERE Acrecodigo = ?";

    private static final String SQL_DELETE = "DELETE FROM acreedores WHERE Acrecodigo = ?";

    private static final String SQL_QUERY_POR_CODIGO = "SELECT Acrecodigo, Acrenombre, Acrenit, Acrecuentabancaria, Acreestado " + "FROM acreedores WHERE Acrecodigo = ?";

    private static final String SQL_QUERY_POR_NOMBRE = "SELECT Acrecodigo, Acrenombre, Acrenit, Acrecuentabancaria, Acreestado " + "FROM acreedores WHERE Acrenombre LIKE ?";

    private static final String SQL_QUERY_POR_NIT ="SELECT Acrecodigo, Acrenombre, Acrenit, Acrecuentabancaria, Acreestado "+ "FROM acreedores WHERE Acrenit = ?";

    private static final String SQL_QUERY_POR_ESTADO ="SELECT Acrecodigo, Acrenombre, Acrenit, Acrecuentabancaria, Acreestado "+ "FROM acreedores WHERE Acreestado = ?";

    // INSERT

    public int ingresaAcreedor(clsCompras acreedor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, acreedor.getAcreNombre());
            stmt.setString(2, acreedor.getAcreNit());
            stmt.setString(3, acreedor.getAcreCuentaBancaria());
            stmt.setString(4, acreedor.getAcreEstado());

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
    public List<clsCompras> consultaAcreedores() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<clsCompras> lista = new ArrayList<>();

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);

            System.out.println("Ejecutando query: " + SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {
                clsCompras a = new clsCompras(
                    rs.getInt("Acrecodigo"),
                    rs.getString("Acrenombre"),
                    rs.getString("Acrenit"),
                    rs.getString("Acrecuentabancaria"),
                    rs.getString("Acreestado")
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


    //  SELECT por Codigo
    public clsCompras consultaAcreedorPorId(clsCompras acreedor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        clsCompras resultado = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_QUERY_POR_CODIGO);
            stmt.setInt(1, acreedor.getAcreCodigo());

            System.out.println("Ejecutando query: " + SQL_QUERY_POR_CODIGO);
            rs = stmt.executeQuery();

            if (rs.next()) {
                resultado = new clsCompras(
                    rs.getInt("Acrecodigo"),
                    rs.getString("Acrenombre"),
                    rs.getString("Acrenit"),
                    rs.getString("Acrecuentabancaria"),
                    rs.getString("Acreestado")
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return resultado;
    }

    // 
    // select por nombre

    public List<clsCompras> consultaAcreedoresPorNombre(clsCompras acreedor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<clsCompras> lista = new ArrayList<>();

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_QUERY_POR_NOMBRE);
            stmt.setString(1, "%" + acreedor.getAcreNombre() + "%");

            System.out.println("Ejecutando query: " + SQL_QUERY_POR_NOMBRE);
            rs = stmt.executeQuery();

            while (rs.next()) {
                clsCompras a = new clsCompras(
                    rs.getInt("Acrecodigo"),
                    rs.getString("Acrenombre"),
                    rs.getString("Acrenit"),
                    rs.getString("Acrecuentabancaria"),
                    rs.getString("Acreestado")
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

    // select por nit
    public clsCompras consultaAcreedorPorNit(clsCompras acreedor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        clsCompras resultado = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_QUERY_POR_NIT);
            stmt.setString(1, acreedor.getAcreNit());

            System.out.println("Ejecutando query: " + SQL_QUERY_POR_NIT);
            rs = stmt.executeQuery();

            if (rs.next()) {
                resultado = new clsCompras(
                    rs.getInt("Acrecodigo"),
                    rs.getString("Acrenombre"),
                    rs.getString("Acrenit"),
                    rs.getString("Acrecuentabancaria"),
                    rs.getString("Acreestado")
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return resultado;
    }

    // select por estado (A/I)
    public List<clsCompras> consultaAcreedoresPorEstado(clsCompras acreedor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<clsCompras> lista = new ArrayList<>();

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_QUERY_POR_ESTADO);
            stmt.setString(1, acreedor.getAcreEstado());

            System.out.println("Ejecutando query: " + SQL_QUERY_POR_ESTADO);
            rs = stmt.executeQuery();

            while (rs.next()) {
                clsCompras a = new clsCompras(
                    rs.getInt("Acrecodigo"),
                    rs.getString("Acrenombre"),
                    rs.getString("Acrenit"),
                    rs.getString("Acrecuentabancaria"),
                    rs.getString("Acreestado")
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
    public int actualizaAcreedor(clsCompras acreedor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, acreedor.getAcreNombre());
            stmt.setString(2, acreedor.getAcreNit());
            stmt.setString(3, acreedor.getAcreCuentaBancaria());
            stmt.setString(4, acreedor.getAcreEstado());
            stmt.setInt(5, acreedor.getAcreCodigo());

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
    public int borraAcreedor(clsCompras acreedor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, acreedor.getAcreCodigo());

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

    public List<clsAcreedor> consultaAcreedoresPorNombre(clsAcreedor acreedor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public clsAcreedor consultaAcreedorPorNit(clsAcreedor acreedor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<clsAcreedor> consultaAcreedoresPorEstado(clsAcreedor acreedor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int actualizaAcreedor(clsAcreedor acreedor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int borraAcreedor(clsAcreedor acreedor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public clsAcreedor consultaAcreedorPorId(clsAcreedor acreedor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int ingresaAcreedor(clsAcreedor acreedor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}