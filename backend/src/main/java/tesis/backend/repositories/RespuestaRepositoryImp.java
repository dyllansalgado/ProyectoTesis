package tesis.backend.repositories;
import tesis.backend.models.Respuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class RespuestaRepositoryImp implements RespuestaRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public Long countRespuesta(){
        String query = "select count(*) from respuesta";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }

    public Respuesta createRespuesta(Respuesta respuesta){
        Long id_count = countRespuesta();
        String query = "INSERT into respuesta (id_respuesta, respuesta) values (:id_respuesta,:respuesta)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(query,true).addParameter("id_respuesta",id_count)
                .addParameter("respuesta", respuesta.getRespuesta())
                .executeUpdate().getKey();
            respuesta.setId_respuesta(id_count);
            return respuesta;
        } 
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Respuesta getRespuesta(Long id_respuesta){
        String query = "select * from respuesta where id_respuesta = :id_respuesta and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_respuesta",id_respuesta).executeAndFetchFirst(Respuesta.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Respuesta> getListRespuesta() {
        String query = "select * from respuesta";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(Respuesta.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override 
    public Respuesta updateRespuesta(Respuesta respuesta, Long id_respuesta){
        String query = "update respuesta set respuesta = :respuesta where id_respuesta = :id_respuesta and deleted = false";
        try(Connection conn = sql2o.open()){
            Long id = (Long) conn.createQuery(query,true)
                .addParameter("id_respuesta", id_respuesta)
                .addParameter("respuesta", respuesta.getRespuesta())
                .executeUpdate().getKey(Long.class);
            respuesta.setId_respuesta(id);
            return respuesta;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteRespuesta(Long id_respuesta){
        String query = "update respuesta set deleted = true where id_respuesta = :id_respuesta and deleted = false";
        try(Connection conn = sql2o.open()){
            id_respuesta = conn.createQuery(query).addParameter("id_respuesta",id_respuesta).executeUpdate().getKey(Long.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
