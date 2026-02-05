package controlador;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.example.MongoProvider;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;


import modelo.Empleado;

public class EmpleadoController {

    public void insertarEmpleado(Long id, String nombre,int dep,double salario,String fechAlta, String oficio,double comision  ){
        try(MongoProvider mongoProvider= new MongoProvider();) {
            MongoCollection<Document>collection= mongoProvider.obtenerColeccion("empleados");
            Empleado e1= new Empleado(id, nombre, dep, 1200, fechAlta, oficio, comision);

            Document nuevoEmpleado=
            new Document("id",e1.getId()).append("nombre", e1.getNombre())
            .append("dep", e1.getDep()).append("salario", e1.getSalario()).append("fecha de alta", e1.getFech_alta()).append("oficio", e1.getOficio()).append("comisión", e1.getComisión());

            collection.insertOne(nuevoEmpleado);
        } catch (Exception e) {
            System.out.println("Error al insertar un empleado");
        }
    }

    public List<Document> obtenerEmpleado(int numdDep){
        List<Document> empleados= new ArrayList<>();
      try(MongoProvider mongoProvider= new MongoProvider()) {
        MongoCollection<Document> collection= mongoProvider.obtenerColeccion("empleados");

        collection.find(Filters.eq("dep",numdDep)).into(empleados);
        return empleados;

      } catch (Exception e) {
        System.out.println("Error");
        return null;
      }
    }

    /**
     * obtiene los empleados segun el dep pasandole los numeros de departamento(10 y 20)
     * @param numdDep
     * @param numdDep2
     * @return
     */
    public List<Document> obtenerEmpConNumDeps(int numdDep, int numdDep2){
        List<Document> empleados= new ArrayList<>();

      try(MongoProvider mongoProvider= new MongoProvider()) {
        MongoCollection<Document> collection= mongoProvider.obtenerColeccion("empleados");

        Bson filtro= Filters.or(Filters.eq("dep",numdDep),
    Filters.eq("dep",numdDep2)
    );

        collection.find(filtro).into(empleados);
        return empleados;

      } catch (Exception e) {
        System.out.println("Error");
        return null;
      }
    }

/**
 * Obtén los empleados con salario > 1300 y oficio Profesora.
 * @param salario
 * @param oficio
 * @return
 */
public List<Document> obtenerProfesorasConBuenSalario(double salario, String oficio) {
    List<Document> empleados = new ArrayList<>();
    
    try (MongoProvider mongoProvider = new MongoProvider()) {
        MongoCollection<Document> collection = mongoProvider.obtenerColeccion("empleados");

        Bson filtro = Filters.and(
            Filters.gt("salario", salario),         
            Filters.eq("oficio", oficio)   
        );

        collection.find(filtro).into(empleados);
        

    } catch (Exception e) {
        System.err.println("Error en la consulta: " + e.getMessage());
        
    }
    return empleados;


}

/**
 * Sube el salario según el oficio
 * @param oficio
 * @param salario es el monto a incrementar
 */

public void subirSalario(String oficio, double salario){
    try(MongoProvider mongoProvider= new MongoProvider();) {
        MongoCollection<Document> coleccion = mongoProvider.obtenerColeccion("empleados");

       Bson filtro= Filters.eq("oficio",oficio);

       Bson camposUpdate= Updates.inc("salario", salario);
        
       coleccion.updateMany(filtro, camposUpdate);
    } catch (Exception e) {
        System.out.println("No se pudo subir el salario de los emppleados con oficio de "+oficio+" y aumentarle "+ salario +" de salario");
        
    }

    

}
/**
 * Decrementa la comisión existente en X €
 * @param comision
 */

public void reducirSalario(double comision){
     try (MongoProvider mongoProvider= new MongoProvider()) {
        MongoCollection<Document>collection= mongoProvider.obtenerColeccion("empleados");

        Bson filtro=Filters.and(Filters.exists("comision"), Filters.gte("comision", "comision"));

        Bson campoUpdate= Updates.inc("comisión", comision);
        collection.updateMany(filtro, campoUpdate);
        
     } catch (Exception e) {
        System.out.println(e.getMessage());
     }    

    }

    /**
     * obtiene la media de todos los salarios
     * @return
     */

public double hacerMedia(){
    double suma=0;
     List<Document> empleados = new ArrayList<>();

    try(MongoProvider mongoProvider = new MongoProvider()) {
        MongoCollection<Document> collection= mongoProvider.obtenerColeccion("empleados");
    
        
         collection.find(Filters.exists("salario")).into(empleados);

         
         for (Document e : empleados) {
            suma+=e.getDouble("salario");
         }



    } catch (Exception e) {
        System.out.println("No se pudo hacer la media"+e.getMessage());
    }
      return suma/empleados.size() ;



}

public void obtenerNumEmplePorDep(int dep){
    try(MongoProvider mongoProvider = new MongoProvider()) {
        MongoCollection<Document> collection= mongoProvider.obtenerColeccion("empleados");

        Bson filtro= Filters.eq("dep",dep);

        List<Document> empleados= new ArrayList<>();

        collection.find(filtro).into(empleados);

        double salarioMax= 0;
        double suma=0;
        double numEmple= empleados.size();


     /*    List<Bson> listasa= List.of(Aggregates.group("$dep",
         Accumulators.sum("numEmpleados", 1),
       Accumulators.avg("salarioMedio", "$salario"),
       Accumulators.max("salarioMáximo", "$salario")
    ),
    Aggregates.sort(Sorts.ascending("dep"))); */

        for (Document e : empleados) {
      
            

            if (e.getDouble("salario")>salarioMax) {
                salarioMax= e.getDouble("salario");
            }
            suma+=e.getDouble("salario");
            
            
            
        }

       
      
        double media = suma/empleados.size();
        
        System.out.println("El numero de empleados del departamento "+ dep+" es "+empleados.size());
        System.out.println("La media de todos los salarios es: "+ media);
        System.out.println("el salario máximo es: "+ salarioMax);


        


        
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}

    public void nombreDelSalarioMax(){
        try(MongoProvider mongoProvider = new MongoProvider()) {

            MongoCollection<Document> collection= mongoProvider.obtenerColeccion("empleados");

            List<Document> empleados = new ArrayList<>();

            collection.find().into(empleados);

            double salarioMax=0;

            String nombreMax="";

            for (Document d : empleados ) {
                if (d.getDouble("salario")> salarioMax) {
                    salarioMax= d.getDouble("salario");
                    nombreMax= d.getString("nombre");
                }
                
            }

            System.out.println("El nombre del empleado con salario máximo es "+nombreMax);
            
        } catch (Exception e) {
            System.out.println("No se pudo obtener el nombre del empleado con salario maximo"+e.getMessage());
        }
    }




//collection.find(filtro).projection(fields(include("nombre,","notaMedia","curso"),excludeId())).sort()


//projection, para visualizar lo q me interesa
    





}
