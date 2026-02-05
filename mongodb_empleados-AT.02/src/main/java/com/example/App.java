package com.example;

import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import controlador.EmpleadoController;

/**
 * Hello world!
 */
public final class App {

    public static void main(String[] args) {

        try {
            Scanner sc = new Scanner(System.in);

            EmpleadoController empleadoController = new EmpleadoController();

            System.out.println("Ingrese un id: ");
            Long id = Long.parseLong(sc.nextLine());
            System.out.println("Ingrese un nombre: ");
            String nombre = sc.nextLine();
            System.out.println("Ingrese un numero de dep");
            int dep = Integer.parseInt(sc.nextLine());
            System.out.println("Ingrese un salario: ");
            double salario = Double.parseDouble(sc.nextLine());
            System.out.println("Ingrese la fecha de alta");
            String fechaAlta = sc.nextLine();
            System.out.println("Ingrese el oficio: ");
            String oficio = sc.nextLine();
            System.out.println("Ingrese la comisión");
            double comision = Double.parseDouble(sc.nextLine());

            empleadoController.insertarEmpleado(id, nombre, dep, salario, fechaAlta, oficio, comision);

            System.out.println("-----CONSULTAR EMPLEADOS POR NUMERO DE DEPARTAMENTO------");
            System.out.println("Ingrese un numero de departamento: ");
            int idep = Integer.parseInt(sc.nextLine());
            List<Document> lista = empleadoController.obtenerEmpleado(idep);
            for (Document e : lista) {
                System.err.println(e);
            }

            System.out.println("----Consultando empleados del dep 10 y 20 -----");
            List<Document> listaa = empleadoController.obtenerEmpConNumDeps(10, 20);
            for (Document d : listaa) {
                System.out.println(d);
            }
            System.out.println();

            System.out.println("----Empleados con salario > 1300 y de oficio profesora---");
            System.out.println("Ingrese el salario");
            Double salariox = Double.parseDouble(sc.nextLine());
            System.out.println("Ingrese el oficio");
            String oficiox = sc.nextLine();
            List<Document> listab= empleadoController.obtenerProfesorasConBuenSalario(salariox, oficiox);
            for (Document document : listab) {
                System.out.println(document);
            }
            System.out.println();

            System.out.println("------Sube el salario de los analistas en 100€, a todos los analistas.---------");
            System.out.println("Ingrese el oficio: ");
            String oficioy = sc.nextLine();
            System.out.println("Ingrese el salario");
            double salarioy = Double.parseDouble(sc.nextLine());
            empleadoController.subirSalario(oficioy, salarioy);

            System.out.println("--------Decrementa la comisión existente en 20€.----------");
            empleadoController.reducirSalario(-20);

            // Consultando el promedio de todos los salarios
            double mediaSalario = empleadoController.hacerMedia();
            System.out.println("-----------LA MEDIA DE LOS SALARIOS----------");
            System.out.println(mediaSalario);

            //consultando el numero de empleados por departamento y el salario medio y el maximo.

            empleadoController.obtenerNumEmplePorDep(10);


            //consultado el empleado con el salario máximo
            empleadoController.nombreDelSalarioMax();

        } catch (Exception e) {
            System.out.println("ERROR");
        }

    }
}
