package com.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public final class App {
   

    //Notebooklm
   
    public static void main(String[] args) {
        System.out.println("Hello World!");



        try (MongoProvider mongoProvider = new MongoProvider()) {
            // Seleccionas la base de datos (se creará si no existe)
           

            // Seleccionas la colección (equivalente a la tabla)
           MongoCollection<Document> coleccion = mongoProvider.alumnado();

            
            

            // Creamos el "Documento" (el JSON)
            Document nuevoAlumno = new Document("nombre", "xd")
                                    .append("edad", 32)
                                    .append("curso","Programación Java")
                                    .append("aula", 15)
                                    .append("ciclo", "DAM")
                                    .append("fecha_registro", new java.util.Date());

             Document nuevoAlumno2 = new Document("nombre", "Marcos")
                                    .append("edad", 18)
                                    .append("curso","Programación Kotlin")
                                    .append("aula", 12)
                                    .append("ciclo", "DAw")
                                    .append("fecha_registro", new java.util.Date());

            List<Document>alumnos= new ArrayList<>();
            alumnos.add(nuevoAlumno);
            alumnos.add(nuevoAlumno2);

            // Insertamos el documento
            coleccion.insertMany(alumnos);

            System.out.println("¡Documento insertado con éxito!");
            List<Document> lista= obtenerAlumnos();

            for(Document d: lista){
              System.out.println(d.toJson());
            }

            Long num= borrarPorNombre("Felipe");
            if (num>0) {
              System.out.println("Se borró correctamente");
            } else{
              System.out.println("Error ");
            }

            



        } catch (MongoException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static List<Document> obtenerAlumnos(){

      List<Document> alumnos= new ArrayList<>();

      try(MongoProvider mongoProvider = new MongoProvider()) {
        MongoCollection<Document> coleccion = mongoProvider.alumnado();

        coleccion.find().into(alumnos);

        return alumnos;
        
      } catch (Exception e) {
        System.out.println("No se pudo obtener los alumnos");
        return null;
      }
    }

    public static long borrarPorNombre(String nombre){
      try(MongoProvider mongoProvider= new MongoProvider()) {

        MongoCollection<Document> coleccion= mongoProvider.alumnado();
        return coleccion.deleteOne(Filters.eq("nombre",nombre)).getDeletedCount();
        
      } catch (Exception e) {
        System.out.println("No se pudo borrar el alumno con nombre: "+nombre);
        return 0;
      }
    }

    public static void insertarAlumno(String nombre, int edad, String curso, int aula, String ciclo, Date fecha){

      try(MongoProvider mongoProvider= new MongoProvider()) {
        MongoCollection<Document>collection= mongoProvider.alumnado();

         Document nuevoAlumno = new Document("nombre", nombre)
                                    .append("edad",edad)
                                    .append("curso",curso)
                                    .append("aula", aula)
                                    .append("ciclo", ciclo)
                                    .append("fecha_registro", fecha);

       collection.insertOne(nuevoAlumno);

      } catch (Exception e) {
        System.out.println("");
      }
     
           

    }
  }







//mongosh -u admin -p admin123

//1.  
// db.mediciones.insertOne({sensorId:1,ts:new Date(2026,01,23),tipo:"hum",valor:24, ubicación:"Madrid"})
//db.mediciones.insertOne({sensorId:2,ts:new Date(2025,01,24),tipo:"temp",valor:25, ubicación:"Barcelona"})
//db.mediciones.insertOne({sensorId:3,ts:new Date(2025,06,12),tipo:"hum",valor:26, ubicación:"A Coruña"})


//2.db.mediciones.find({ts:{"$lt": new Date("2026-01-23")}})

//3. db.mediciones.find({valor:{"$gt":24}})

//4.
//db.mediciones.updateMany({ sensorId: 1 }, { $set: { valor: 29 }})
//db.mediciones.updateMany({ sensorId: 2 }, { $set: { valor: 30 }})


//5.
// db.mediciones.deleteMany({ts:{$lt: new Date("2026-01-01")}})

//TIENDA

//1.

/*db.cliente.insertMany([ // <--- Inicia Array
...   {
...     idCliente: 1,
...     nombre: "Juan",
...     emails: ["juan@gmail.com", "juansito@gmail.com", "juan123@gmail.com"],
...     direcciones: ["Rua flor", "rua recreo", "rua primavera"]
...   },
...   {
...     idCliente: 2,
...     nombre: "Pedro",
...     emails: ["pedro2@gmail.com", "pedro123@gmail.com"],
...     direcciones: ["Rua la luz", "Rua silvestre"]
...   },
...   {
...     idCliente: 3,
...     nombre: "Luis",
...     emails: ["luis@gmail.com", "luisito@gmail.com"],
...     direcciones: ["Rua rosalia", "Rua Otoño"]
...   }
... ]);*/

/*db.pedido.insertOne({
  idPedido: 103,
  idCliente: 2, // El ID de Juan, por ejemplo
  fecha: new Date(),
  lineas: [
    { sku: "MON-26", nombre: "Monitor 26", cantidad: 4, precio: 120.00 },
    { sku: "MOU-OPT", nombre: "Ratón Óptico", cantidad: 2, precio: 15.50 }
  ],
  estado: "entregado"
});
*/


//3.

//db.pedido.find({"lineas.cantidad":{"$gt":1}})


//4.Cambiar el estado de un pedido
//db.pedido.update({estado:"procesando"},{$set:{estado:"entregado"}})


//CONECTAR A LA BD
//mongodb://admin:admin123@localhost:27017/?authSource=admin

/*db.pedidos.aggregate([
  {$project:{
    _id:0,
    idPedido:1,
    idCliente:1,
    fecha:1,
    estado:1,
    cantidadTotal:{$sum : "$lineas.cantidad"}
  }},{
    $match:{
      cantiddaTotal:{$gt:100}
    }
  }
])*/

      