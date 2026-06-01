/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.modeloPlanilla;

import Controlador.controladorPlanilla.clsPlanillaDetalle;
import Controlador.clsBitacora;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Meilyn Garcia
 */
public class PlanillaDetalleDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // INSERTAR
    public boolean insertar(clsPlanillaDetalle d) {

        String sql = "INSERT INTO planilladetalle "
                + "(Placodigo, Empcodigo, Detsalario, Dettotalpercepciones, Dettotaldeducciones, Detliquido) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);

            // 🔥 cálculo automático recomendado
            double liquido = d.getDetsalario()
                    + d.getDettotalpercepciones()
                    - d.getDettotaldeducciones();

            d.setDetliquido(liquido);

            ps.setInt(1, d.getPlacodigo());
            ps.setInt(2, d.getEmpcodigo());
            ps.setDouble(3, d.getDetsalario());
            ps.setDouble(4, d.getDettotalpercepciones());
            ps.setDouble(5, d.getDettotaldeducciones());

            ps.setDouble(6, d.getDetliquido());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error INSERT planilla detalle: " + e.getMessage());
            return false;
        }
    }

    // LISTAR
    public List<clsPlanillaDetalle> listar() {

        List<clsPlanillaDetalle> lista = new ArrayList<>();

        String sql = "SELECT * FROM planilladetalle";

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                clsPlanillaDetalle d = new clsPlanillaDetalle();

                d.setDetcodigo(rs.getInt("Detcodigo"));
                d.setPlacodigo(rs.getInt("Placodigo"));
                d.setEmpcodigo(rs.getInt("Empcodigo"));
                d.setDetsalario(rs.getDouble("Detsalario"));
                d.setDettotalpercepciones(rs.getDouble("Dettotalpercepciones"));
                d.setDettotaldeducciones(rs.getDouble("Dettotaldeducciones"));
                d.setDetliquido(rs.getDouble("Detliquido"));

                lista.add(d);
            }

        } catch (SQLException e) {
            System.out.println("Error LISTAR planilla detalle: " + e.getMessage());
        }

        return lista;
    }

    // BUSCAR
    public clsPlanillaDetalle buscar(int id) {

        String sql = "SELECT * FROM planilladetalle WHERE Detcodigo=?";

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {

                clsPlanillaDetalle d = new clsPlanillaDetalle();

                d.setDetcodigo(rs.getInt("Detcodigo"));
                d.setPlacodigo(rs.getInt("Placodigo"));
                d.setEmpcodigo(rs.getInt("Empcodigo"));
                d.setDetsalario(rs.getDouble("Detsalario"));
                d.setDettotalpercepciones(rs.getDouble("Dettotalpercepciones"));
                d.setDettotaldeducciones(rs.getDouble("Dettotaldeducciones"));
                d.setDetliquido(rs.getDouble("Detliquido"));

                return d;
            }

        } catch (SQLException e) {
            System.out.println("Error BUSCAR planilla detalle: " + e.getMessage());
        }

        return null;
    }

    // ACTUALIZAR
    public boolean actualizar(clsPlanillaDetalle d) {

        String sql = "UPDATE planilladetalle SET "
                + "Placodigo=?, Empcodigo=?, Detsalario=?, "
                + "Dettotalpercepciones=?, Dettotaldeducciones=?, Detliquido=? "
                + "WHERE Detcodigo=?";

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);

            double liquido = d.getDetsalario()
                    + d.getDettotalpercepciones()
                    - d.getDettotaldeducciones();

            d.setDetliquido(liquido);

            ps.setInt(1, d.getPlacodigo());
            ps.setInt(2, d.getEmpcodigo());
            ps.setDouble(3, d.getDetsalario());
            ps.setDouble(4, d.getDettotalpercepciones());
            ps.setDouble(5, d.getDettotaldeducciones());
            ps.setDouble(6, d.getDetliquido());
            ps.setInt(7, d.getDetcodigo());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error UPDATE planilla detalle: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {

        String sql = "DELETE FROM planilladetalle WHERE Detcodigo=?";

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error DELETE planilla detalle: " + e.getMessage());
            return false;
        }
    }
}
