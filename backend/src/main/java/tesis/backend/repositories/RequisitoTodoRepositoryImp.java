package tesis.backend.repositories;
import tesis.backend.models.RequisitoTodo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class RequisitoTodoRepositoryImp implements RequisitoTodoRepository{

    @Autowired
    private Sql2o sql2o;
    public List<RequisitoTodo> getListRequisitosTodo(Long id_tema) {
        String query = "SELECT Requisito.nombre_requisito, Requisito.estado_requisito, Requisito.creador_requisito, Requisito.descripcion_requisito, Requisito.prioridad, Pregunta.pregunta, Respuesta.respuesta, tipo_requisito.nombre_tipo_requisito, tipo_requisito.descripcion_tipo_requisito " +
        "FROM Requisito LEFT JOIN Pregunta on Requisito.id_pregunta = Pregunta.id_pregunta " + 
        "LEFT JOIN Respuesta on Pregunta.id_pregunta = Respuesta.id_pregunta " +
        "LEFT JOIN tipo_requisito on Requisito.id_tipo_requisito = tipo_requisito.id_tipo_requisito " +
        "where Pregunta.id_tema=:id_tema and Pregunta.estado = true";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_tema", id_tema).executeAndFetch(RequisitoTodo.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
}