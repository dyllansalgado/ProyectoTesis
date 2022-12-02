package tesis.backend.repositories;
import tesis.backend.models.PreguntasRecomendadas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class PreguntasRecomendadasRepositoryImp implements PreguntasRecomendadasRepository {
    @Autowired
    private Sql2o sql2o;

    @Override
    public Long countPreguntas_Recomendadas(){
        String query = "select count(*) from preguntas_recomendadas";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }

    @Override
    public PreguntasRecomendadas getPregunta_Recomendada(Long id_pregunta_recomendada){
        String query = "select * from preguntas_recomendadas where id_pregunta_recomendada = :id_pregunta_recomendada and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_pregunta_recomendada",id_pregunta_recomendada).executeAndFetchFirst(PreguntasRecomendadas.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public  List<PreguntasRecomendadas> getListPreguntas_Recomendadas() {
        String query = "select * from preguntas_recomendadas";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(PreguntasRecomendadas.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
