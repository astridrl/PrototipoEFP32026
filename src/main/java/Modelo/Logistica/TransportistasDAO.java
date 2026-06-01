package Modelo.Logistica;

import Modelo.Conexion;
import Controlador.Logistica.clsTransportistas;
import Controlador.clsUsuarioConectado;
import Modelo.BitacoraDAO;
import java.sql.*;
import java.util.*;

/**
 * Autor: Anthony Hetzael Suc Gomez
 * Carné: 9959-24-389
 * Fecha de creación: 2026
 * 
 * Descripción:
 * DAO encargado de gestionar las operaciones CRUD de la tabla transportistas.
 * Permite insertar, actualizar, eliminar, listar y buscar registros en la base de datos.
 */
public class TransportistasDAO {

    // INSERTAR
    public boolean insertar(clsTransportistas obj) {
        String sql = "INSERT INTO transportistas (Trantipovehiculo, Empcodigo) VALUES (?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, obj.getTrantipovehiculo());
            ps.setInt(2, obj.getEmpcodigo());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR
    public boolean actualizar(clsTransportistas obj) {
        String sql = "UPDATE transportistas SET Trantipovehiculo=?, Empcodigo=? WHERE Tranid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, obj.getTrantipovehiculo());
            ps.setInt(2, obj.getEmpcodigo());
            ps.setInt(3, obj.getTranid());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {
        String sql = "DELETE FROM transportistas WHERE Tranid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // LISTAR
    public List<clsTransportistas> listar() {
        List<clsTransportistas> lista = new ArrayList<>();
        String sql = "SELECT * FROM transportistas";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clsTransportistas obj = new clsTransportistas();

                obj.setTranid(rs.getInt("Tranid"));
                obj.setTrantipovehiculo(rs.getString("Trantipovehiculo"));
                obj.setEmpcodigo(rs.getInt("Empcodigo"));

                lista.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID
    public clsTransportistas buscarPorId(int id) {
        String sql = "SELECT * FROM transportistas WHERE Tranid=?";
        clsTransportistas obj = null;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = new clsTransportistas();

                obj.setTranid(rs.getInt("Tranid"));
                obj.setTrantipovehiculo(rs.getString("Trantipovehiculo"));
                obj.setEmpcodigo(rs.getInt("Empcodigo"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }
    /**
     * Registra una acción en la bitácora del sistema.
     * 
     * @param accion Descripción de la acción realizada
     */
    private void registrarBitacora(String accion) {

        int usuario = clsUsuarioConectado.getUsuId();

        // Validación de usuario autenticado
        if (usuario == 0) {
            throw new RuntimeException("No hay usuario autenticado");
        }

        BitacoraDAO bitacora = new BitacoraDAO();

        // ID de aplicación para bitácora (debe existir en la BD)
        int aplCodigoBitacora = 2000;

        bitacora.insert(usuario, aplCodigoBitacora, accion);
    }
}