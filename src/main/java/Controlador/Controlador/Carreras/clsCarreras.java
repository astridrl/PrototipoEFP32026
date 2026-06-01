/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.Controlador.Carreras;

import Modelo.modeloCarreras.CarrerasDAO;
import java.util.List;

/**
 *
 * @author astri
 */
public class clsCarreras {

    // =========================================================
    // ATRIBUTOS
    // =========================================================
    private String    codigo_carrera;
    private String nombre_carrera;
    private String codigo_facultad;
    private String estatus_carrera;


    // =========================================================
    // CONSTRUCTOR VACÍO
    // =========================================================
    public clsCarreras() {
    }

    
    public clsCarreras(String codigo_carrera, String nombre_carrera, String codigo_facultad,
                       String estatus_carrera) {
        this.codigo_carrera         = codigo_carrera;
        this.nombre_carrera         = nombre_carrera;
        this.codigo_facultad            = codigo_facultad;
        this.estatus_carrera = estatus_carrera;

    }

    
    public String getcodigo_carrera() { return codigo_carrera; }
    public void setcodigo_carrera(String codigo_carrera) { this.codigo_carrera = codigo_carrera; }

    public String getnombre_carrera() { return nombre_carrera; }
    public void setnombre_carrera(String nombre_carrera) { this.nombre_carrera = nombre_carrera; }

    public String getcodigo_facultad() { return codigo_facultad; }
    public void setcodigo_facultad(String codigo_facultad) { this.codigo_facultad = codigo_facultad; }

    public String getestatus_carrera() { return estatus_carrera; }
    public void setestatus_carrera(String estatus_carrera) { this.estatus_carrera = estatus_carrera; }


  
    @Override
    public String toString() {
        return "Carreras{"
                + "codigo_carrera="         + codigo_carrera
                + ", nombre_carrera='"      + nombre_carrera
                + ", codigo_facultad='"         + codigo_facultad
                + ", estatus_carrera='" + estatus_carrera
                + '}';
    }
    // MÉTODOS DE ACCESO AL DAO (CRUD)
    

    // INSERT
    public int setIngresarCarreras(clsCarreras carreras) {
        CarrerasDAO daoCarreras = new CarrerasDAO();
        return daoCarreras.ingresaCarreras(carreras);
    }

    // SELECT todos
    public List<clsCarreras> getListadoCarreras() {
        CarrerasDAO daoCarreras = new CarrerasDAO();
        return daoCarreras.consultaCarreras();
    }

   
    // UPDATE
    public int setModificarCarrera(clsCarreras carreras) {
        CarrerasDAO daoCarreras = new CarrerasDAO();
        return daoCarreras.actualizaCarrera(carreras);
    }

    // DELETE
    public int setBorrarCarrera(clsCarreras carreras) {
        CarrerasDAO daoCarreras = new CarrerasDAO();
        return daoCarreras.borraCarrera(carreras);
    }

    
}