/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.controladorPlanilla;

/**
 *
 * @author Meilyn Garcia
 */
public class clsPlanillaDetalle {
    private int detcodigo;
    private int placodigo;
    private int empcodigo;
    private double detsalario;
    private double dettotalpercepciones;
    private double dettotaldeducciones;
    private double detliquido;

    public clsPlanillaDetalle() {
    }

    public clsPlanillaDetalle(int detcodigo, int placodigo, int empcodigo, double detsalario,
            double dettotalpercepciones, double dettotaldeducciones, double detliquido) {
        this.detcodigo = detcodigo;
        this.placodigo = placodigo;
        this.empcodigo = empcodigo;
        this.detsalario = detsalario;
        this.dettotalpercepciones = dettotalpercepciones;
        this.dettotaldeducciones = dettotaldeducciones;
        this.detliquido = detliquido;
    }

    public int getDetcodigo() {
        return detcodigo;
    }

    public void setDetcodigo(int detcodigo) {
        this.detcodigo = detcodigo;
    }

    public int getPlacodigo() {
        return placodigo;
    }

    public void setPlacodigo(int placodigo) {
        this.placodigo = placodigo;
    }

    public int getEmpcodigo() {
        return empcodigo;
    }

    public void setEmpcodigo(int empcodigo) {
        this.empcodigo = empcodigo;
    }

    public double getDetsalario() {
        return detsalario;
    }

    public void setDetsalario(double detsalario) {
        this.detsalario = detsalario;
    }

    public double getDettotalpercepciones() {
        return dettotalpercepciones;
    }

    public void setDettotalpercepciones(double dettotalpercepciones) {
        this.dettotalpercepciones = dettotalpercepciones;
    }

    public double getDettotaldeducciones() {
        return dettotaldeducciones;
    }

    public void setDettotaldeducciones(double dettotaldeducciones) {
        this.dettotaldeducciones = dettotaldeducciones;
    }

    public double getDetliquido() {
        return detliquido;
    }

    public void setDetliquido(double detliquido) {
        this.detliquido = detliquido;
    }
}
