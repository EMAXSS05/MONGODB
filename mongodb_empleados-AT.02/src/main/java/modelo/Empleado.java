package modelo;

public class Empleado {

    Long id;
    String nombre;
    int dep;
    double salario;
    String fech_alta;
    String oficio;
    double comision;


    

    public Empleado(Long id, String nombre, int dep, double salario, String fech_alta, String oficio, double comision) {
        this.id = id;
        this.nombre = nombre;
        this.dep = dep;
        this.salario = salario;
        this.fech_alta = fech_alta;
        this.oficio = oficio;
        this.comision = comision;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getDep() {
        return dep;
    }
    public void setDep(int dep) {
        this.dep = dep;
    }
    public double getSalario() {
        return salario;
    }
    public void setSalario(double salario) {
        this.salario = salario;
    }
    public String getFech_alta() {
        return fech_alta;
    }
    public void setFech_alta(String fech_alta) {
        this.fech_alta = fech_alta;
    }
    public String getOficio() {
        return oficio;
    }
    public void setOficio(String oficio) {
        this.oficio = oficio;
    }
    public double getComisión() {
        return comision;
    }
    public void setComisión(double comision) {
        this.comision = comision;
    }

    

}
