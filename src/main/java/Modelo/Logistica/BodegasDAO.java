package Modelo.Logistica;

import Modelo.Conexion;
import Controlador.Logistica.clsBodegas;
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
 * DAO encargado de gestionar las operaciones CRUD de la tabla bodegas.
 * Permite insertar, actualizar, eliminar, listar y buscar registros en la base de datos.
 */
public class BodegasDAO {

    // INSERTAR
    public boolean insertar(clsBodegas obj) {
        String sql = "INSERT INTO bodegas (Bodnombre, Bodubicacion) VALUES (?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, obj.getBodnombre());
            ps.setString(2, obj.getBodubicacion());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR
    public boolean actualizar(clsBodegas obj) {
        String sql = "UPDATE bodegas SET Bodnombre=?, Bodubicacion=? WHERE bodegaid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, obj.getBodnombre());
            ps.setString(2, obj.getBodubicacion());
            ps.setInt(3, obj.getBodegaid());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {
        String sql = "DELETE FROM bodegas WHERE bodegaid=?";

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
    public List<clsBodegas> listar() {
        List<clsBodegas> lista = new ArrayList<>();
        String sql = "SELECT * FROM bodegas";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clsBodegas obj = new clsBodegas();

                obj.setBodegaid(rs.getInt("bodegaid"));
                obj.setBodnombre(rs.getString("Bodnombre"));
                obj.setBodubicacion(rs.getString("Bodubicacion"));

                lista.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID
    public clsBodegas buscarPorId(int id) {
        String sql = "SELECT * FROM bodegas WHERE bodegaid=?";
        clsBodegas obj = null;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = new clsBodegas();

                obj.setBodegaid(rs.getInt("bodegaid"));
                obj.setBodnombre(rs.getString("Bodnombre"));
                obj.setBodubicacion(rs.getString("Bodubicacion"));
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