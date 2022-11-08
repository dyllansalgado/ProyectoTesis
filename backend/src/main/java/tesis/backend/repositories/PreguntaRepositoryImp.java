package tesis.backend.repositories;
import tesis.backend.models.Pregunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class PreguntaRepositoryImp implements PreguntaRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public Long countPregunta(){
        String query = "select count(*) from pregunta";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }

    public Pregunta createPregunta(Pregunta pregunta){
        Long id_count = countPregunta();
        String query = "INSERT into pregunta (id_pregunta, pregunta, estado, id_tema) values (:id_pregunta,:pregunta,:estado,:id_tema)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(query,true).addParameter("id_pregunta",id_count)
                .addParameter("pregunta", pregunta.getPregunta())
                .addParameter("estado", pregunta.getEstado())
                .addParameter("id_tema", pregunta.getId_tema())
                .executeUpdate().getKey();
            pregunta.setId_pregunta(id_count);
            return pregunta;
        } 
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Pregunta getPregunta(Long id_pregunta){
        String query = "select * from pregunta where id_pregunta = :id_pregunta and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_pregunta",id_pregunta).executeAndFetchFirst(Pregunta.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Pregunta> getListPregunta() {
        String query = "select * from pregunta";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(Pregunta.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override 
    public Pregunta updatePregunta(Pregunta pregunta, Long id_pregunta){
        String query = "update pregunta set pregunta = :pregunta, estado = :estado where id_pregunta = :id_pregunta and deleted = false";
        try(Connection conn = sql2o.open()){
            Long id = (Long) conn.createQuery(query,true)
                .addParameter("id_pregunta", id_pregunta)
                .addParameter("pregunta", pregunta.getPregunta())
                .addParameter("estado", pregunta.getEstado())
                .executeUpdate().getKey(Long.class);
            pregunta.setId_pregunta(id);
            return pregunta;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deletePregunta(Long id_pregunta){
        String query = "update pregunta set deleted = true where id_pregunta = :id_pregunta and deleted = false";
        try(Connection conn = sql2o.open()){
            id_pregunta = conn.createQuery(query).addParameter("id_pregunta",id_pregunta).executeUpdate().getKey(Long.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
