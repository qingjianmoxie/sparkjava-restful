package edu.pucmm.resful;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import edu.pucmm.resful.encapsulaciones.ErrorRespuesta;
import edu.pucmm.resful.encapsulaciones.Estudiante;
import edu.pucmm.resful.servicios.EstudianteService;
import edu.pucmm.resful.utilidades.JsonUtilidades;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

/**
 * Created by vacax on 26/06/17.
 */
public class Main {

    public final static String ACCEPT_TYPE = "application/json";
    public final static int  BAD_REQUEST = 400;
    public final static int  ERROR_INTERNO = 500;


    public static void main(String[] args) {
        System.out.println("Proyecto Demo SparkJava RESTFUL");

        //Clase que representa el servicio.
        EstudianteService estudianteService = EstudianteService.getInstancia();

        //Manejo de Excepciones.
        exception(IllegalArgumentException.class, (exception, request, response) -> {
             manejarError(Main.BAD_REQUEST,exception, request, response);
        });

        exception(JsonSyntaxException.class, (exception, request, response) -> {
            manejarError(Main.BAD_REQUEST,exception, request, response);
        });

        exception(Exception.class, (exception, request, response) -> {
            manejarError(Main.ERROR_INTERNO,exception, request, response);
        });

        get("/", (request, response) -> {
            return "Ejemplo Demo API REST - SPARK JAVA";
        });

        //rutas servicios RESTFUL
        path("/rest",() -> {
            //filtros especificos:
            after("/*", (request, response) -> { //indicando que todas las llamadas retorna un json.
                response.header("Content-Type", ACCEPT_TYPE);
            });
            //rutas del api
            path("/estudiantes", () -> {

                //listar todos los estudiantes.
                get("/", (request, response) -> {
                    return estudianteService.getAllEstudiantes();
                }, JsonUtilidades.json());

                //retorna un estudiante
                get("/:matricula", (request, response) -> {
                    return estudianteService.getEstudiante(Integer.parseInt(request.params("matricula")));
                }, JsonUtilidades.json());

                //crea un estudiante
                post("/", Main.ACCEPT_TYPE, (request, response) -> {
                    Estudiante estudiante = new Gson().fromJson(request.body(), Estudiante.class);
                    return estudianteService.crearEstudiante(estudiante);
                }, JsonUtilidades.json());

                //actualiza un estudiante
                put("/", Main.ACCEPT_TYPE, (request, response) -> {
                    Estudiante estudiante = new Gson().fromJson(request.body(), Estudiante.class);
                    return estudianteService.actualizarEstudiante(estudiante);
                }, JsonUtilidades.json());

                //eliminar un estudiante
                delete("/:matricula", (request, response) -> {
                    Estudiante estudiante = new Gson().fromJson(request.body(), Estudiante.class);
                    return estudianteService.eliminarEstudiante(Integer.parseInt(request.params("matricula")));
                }, JsonUtilidades.json());

            });
        });
    }

    private static void manejarError(int codigo,Exception exception, Request request, Response response ){
        response.status(codigo);
        response.body(JsonUtilidades.toJson(new ErrorRespuesta(100, exception.getMessage())));
        exception.printStackTrace();
    }
}
