package tesis.backend.repositories;
import tesis.backend.models.Resultado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class ResultadoRepositoryImp implements ResultadoRepository{ 

    @Autowired
    private Sql2o sql2o;
    public List<Resultado> getListPreguntaSeleccionadaXidTema2(Long id_tema) {
        String query = "SELECT Pregunta.id_tema, Pregunta.pregunta, Respuesta.respuesta, Respuesta.id_respuesta, Pregunta.id_pregunta, pregunta.estado FROM Pregunta " +
        "LEFT JOIN Respuesta on Pregunta.id_pregunta = Respuesta.id_pregunta where Pregunta.id_tema=:id_tema and Pregunta.estado = true";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_tema", id_tema).executeAndFetch(Resultado.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
}
