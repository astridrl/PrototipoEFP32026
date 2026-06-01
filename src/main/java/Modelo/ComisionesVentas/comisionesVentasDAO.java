/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.ComisionesVentas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Controlador.ComisionesVentas.clsComisionVentas;
import Modelo.Conexion;
/**
 *
 * @author giron
 */
public class comisionesVentasDAO {
// Método para obtener solo el nombre del empleado por su ID
public String obtenerNombreEmpleado(int idEmpleado) {
    String nombre = "";
    String sql = "SELECT Empnombre FROM empleados WHERE Empcodigo = ?";
    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        // Asigna el ID del empleado como parámetro de búsqueda
        ps.setInt(1, idEmpleado);
        ResultSet rs = ps.executeQuery();
        // Si encuentra el registro, extrae el nombre
        if (rs.next()) {
            nombre = rs.getString("Empnombre");
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener nombre del empleado: " + e.getMessage());
    }
    return nombre;
}

// Método único que obtiene comisiones de vendedores, usado cuando se selecciona el radio button de vendedores
public List<clsComisionVentas> obtenerDatosComisiones(int idEmpleado, String tipofiltro, int idFiltro) {
    // Inicializa una lista vacía para almacenar los registros recuperados
    List<clsComisionVentas> lista = new ArrayList<>();
    // Base del SQL con todos los JOINs necesarios
    String sql = "SELECT cv.Comid, cv.Venid, cv.Commontoventas, cv.Commeta, cv.Comventasadicionales, cv.Comcomision, " +
                 "v.Vennombre, p.Prodnombre, m.marnombre, l.linnombre, l.lincomision, cp.Cppcodigo " +
                 "FROM ComisionesVendedores cv " +
                 "LEFT JOIN Vendedores v ON cv.Venid = v.Venid " +
                 "LEFT JOIN productos p ON cv.Venid = p.Prodid " +
                 "LEFT JOIN marcas m ON p.Prodid = m.marcaid " +
                 "LEFT JOIN lineas l ON m.marcaid = l.lineaid " +
                 "LEFT JOIN CuentasPorPagar cp ON cv.Venid = cp.Cppcodigo " +
                 "WHERE cv.Venid = ?";
    // Agrega el filtro adicional al SQL según el tipo seleccionado
    if (tipofiltro.equals("linea"))    sql += " AND l.lineaid = ?";
    if (tipofiltro.equals("marca"))    sql += " AND m.marcaid = ?";
    if (tipofiltro.equals("producto")) sql += " AND p.Prodid = ?";
    // Abre la conexión y prepara la consulta con recursos de cierre automático
    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        // Asigna el ID del empleado como primer parámetro siempre
        ps.setInt(1, idEmpleado);
        // Si hay filtro adicional, asigna el ID correspondiente como segundo parámetro
        if (!tipofiltro.equals("vendedor")) ps.setInt(2, idFiltro);
        ResultSet rs = ps.executeQuery();
        // Recorre cada fila devuelta por la base de datos
        while (rs.next()) {
            clsComisionVentas obj = new clsComisionVentas();
            obj.setId_comision(rs.getInt("Comid"));
            obj.setVenid(rs.getInt("Venid"));
            obj.setId_empleado(rs.getInt("Venid"));
            obj.setMonto_ventas(rs.getDouble("Commontoventas"));
            obj.setMeta(rs.getDouble("Commeta"));
            obj.setVentas_adicionales(rs.getDouble("Comventasadicionales"));
            obj.setComision(rs.getDouble("Comcomision"));
            obj.setVennombre(rs.getString("Vennombre"));
            obj.setProdnombre(rs.getString("Prodnombre"));
            obj.setMarnombre(rs.getString("marnombre"));
            obj.setLinnombre(rs.getString("linnombre"));
            obj.setLincomision(rs.getDouble("lincomision"));
            // Extrae el código de cuentas por pagar obtenido mediante el JOIN con la tabla CuentasPorPagar
            obj.setCppcodigo(rs.getString("Cppcodigo"));
            lista.add(obj);
        }
    } catch (SQLException e) {
        System.out.println("Error en el DAO al obtener datos completos: " + e.getMessage());
    }
    return lista;
}

// Método para obtener todos los vendedores y cargarlos en el combobox
public List<clsComisionVentas> obtenerTodosVendedores() {
    // Inicializa una lista vacía para almacenar los vendedores
    List<clsComisionVentas> lista = new ArrayList<>();
    // Consulta todos los vendedores registrados en la tabla
    String sql = "SELECT Venid, Vennombre FROM Vendedores";
    // Abre la conexión y prepara la consulta con recursos de cierre automático
    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ResultSet rs = ps.executeQuery();
        // Recorre cada vendedor devuelto por la base de datos
        while (rs.next()) {
            clsComisionVentas obj = new clsComisionVentas();
            // Obtiene el ID y nombre del vendedor
            obj.setVenid(rs.getInt("Venid"));
            obj.setVennombre(rs.getString("Vennombre"));
            lista.add(obj);
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener todos los vendedores: " + e.getMessage());
    }
    return lista;
}

// Método para obtener información directamente de la tabla Vendedores por su ID
public List<clsComisionVentas> obtenerDatosPorVendedor(int idEmpleado) {
    // Inicializa una lista vacía para almacenar los registros recuperados
    List<clsComisionVentas> lista = new ArrayList<>();
    // Consulta directo la tabla Vendedores sin depender de ComisionesVendedores
    String sql = "SELECT Venid, Vennombre FROM Vendedores WHERE Venid = ?";
    // Abre la conexión y prepara la consulta con recursos de cierre automático
    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        // Asigna el ID del vendedor como parámetro
        ps.setInt(1, idEmpleado);
        ResultSet rs = ps.executeQuery();
        // Recorre cada fila devuelta por la base de datos
        while (rs.next()) {
            clsComisionVentas obj = new clsComisionVentas();
            // Obtiene el ID y nombre del vendedor
            obj.setVenid(rs.getInt("Venid"));
            obj.setVennombre(rs.getString("Vennombre"));
            lista.add(obj);
        }
    } catch (SQLException e) {
        System.out.println("Error en el DAO al obtener datos por vendedor: " + e.getMessage());
    }
    return lista;
}

// Método para obtener información directamente de la tabla marcas por su ID
public List<clsComisionVentas> obtenerDatosPorMarca(int idEmpleado, int idMarca) {
    // Inicializa una lista vacía para almacenar los registros recuperados
    List<clsComisionVentas> lista = new ArrayList<>();
    // Consulta directo la tabla marcas sin depender de ComisionesVendedores
    String sql = "SELECT v.Venid, v.Vennombre, m.marnombre, m.marcomision " +
                 "FROM marcas m " +
                 "JOIN Vendedores v ON v.Venid = ? " +
                 "WHERE m.marcaid = ?";
    // Abre la conexión y prepara la consulta con recursos de cierre automático
    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        // Asigna el ID del empleado como primer parámetro
        ps.setInt(1, idEmpleado);
        // Asigna el ID de la marca como segundo parámetro
        ps.setInt(2, idMarca);
        ResultSet rs = ps.executeQuery();
        // Recorre cada fila devuelta por la base de datos
        while (rs.next()) {
            clsComisionVentas obj = new clsComisionVentas();
            // Obtiene el ID y nombre del vendedor
            obj.setVenid(rs.getInt("Venid"));
            obj.setVennombre(rs.getString("Vennombre"));
            // Obtiene el nombre de la marca y su comisión
            obj.setMarnombre(rs.getString("marnombre"));
            obj.setMonto_ventas(rs.getDouble("marcomision"));
            lista.add(obj);
        }
    } catch (SQLException e) {
        System.out.println("Error en el DAO al obtener datos por marca: " + e.getMessage());
    }
    return lista;
}

// Método para obtener información directamente de la tabla lineas por su ID
public List<clsComisionVentas> obtenerDatosPorLinea(int idEmpleado, int idLinea) {
    // Inicializa una lista vacía para almacenar los registros recuperados
    List<clsComisionVentas> lista = new ArrayList<>();
    // Consulta directo la tabla lineas sin depender de ComisionesVendedores
    String sql = "SELECT v.Venid, v.Vennombre, l.linnombre, l.lincomision " +
                 "FROM lineas l " +
                 "JOIN Vendedores v ON v.Venid = ? " +
                 "WHERE l.lineaid = ?";
    // Abre la conexión y prepara la consulta con recursos de cierre automático
    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        // Asigna el ID del empleado como primer parámetro
        ps.setInt(1, idEmpleado);
        // Asigna el ID de la línea como segundo parámetro
        ps.setInt(2, idLinea);
        ResultSet rs = ps.executeQuery();
        // Recorre cada fila devuelta por la base de datos
        while (rs.next()) {
            clsComisionVentas obj = new clsComisionVentas();
            // Obtiene el ID y nombre del vendedor
            obj.setVenid(rs.getInt("Venid"));
            obj.setVennombre(rs.getString("Vennombre"));
            // Obtiene el nombre de la línea y su comisión
            obj.setLinnombre(rs.getString("linnombre"));
            obj.setLincomision(rs.getDouble("lincomision"));
            lista.add(obj);
        }
    } catch (SQLException e) {
        System.out.println("Error en el DAO al obtener datos por línea: " + e.getMessage());
    }
    return lista;
}

// Método para obtener información directamente de la tabla productos por su ID
public List<clsComisionVentas> obtenerDatosPorProducto(int idEmpleado, int idProducto) {
    // Inicializa una lista vacía para almacenar los registros recuperados
    List<clsComisionVentas> lista = new ArrayList<>();
    // Consulta directo la tabla productos sin depender de ComisionesVendedores
    String sql = "SELECT v.Venid, v.Vennombre, p.Prodnombre, p.prodcomision " +
                 "FROM productos p " +
                 "JOIN Vendedores v ON v.Venid = ? " +
                 "WHERE p.Prodid = ?";
    // Abre la conexión y prepara la consulta con recursos de cierre automático
    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        // Asigna el ID del empleado como primer parámetro
        ps.setInt(1, idEmpleado);
        // Asigna el ID del producto como segundo parámetro
        ps.setInt(2, idProducto);
        ResultSet rs = ps.executeQuery();
        // Recorre cada fila devuelta por la base de datos
        while (rs.next()) {
            clsComisionVentas obj = new clsComisionVentas();
            // Obtiene el ID y nombre del vendedor
            obj.setVenid(rs.getInt("Venid"));
            obj.setVennombre(rs.getString("Vennombre"));
            // Obtiene el nombre del producto y su comisión
            obj.setProdnombre(rs.getString("Prodnombre"));
            obj.setProdprecioventa(rs.getDouble("prodcomision"));
            lista.add(obj);
        }
    } catch (SQLException e) {
        System.out.println("Error en el DAO al obtener datos por producto: " + e.getMessage());
    }
    return lista;
}
}

