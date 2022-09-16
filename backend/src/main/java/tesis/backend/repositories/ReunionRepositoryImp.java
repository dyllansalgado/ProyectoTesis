package tesis.backend.repositories;
import tesis.backend.models.Reunion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class ReunionRepositoryImp implements ReunionRepository {
    @Autowired
    private Sql2o sql2o;

    @Override
    public Long countReunion(){
        String query = "select count(*) from reunion";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }

    public Reunion createReunion(Reunion reunion){
        Long id_count = countReunion();
        String query = "INSERT into reunion (id_reunion, fecha_reunion, lugar_reunion, hora_reunion, estado, id_proyecto, id_glosario) values (:id_reunion,:fecha_reunion,:lugar_reunion,:hora_reunion,:estado,:id_proyecto,:id_glosario)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(query,true).addParameter("id_reunion",id_count)
                .addParameter("fecha_reunion", reunion.getFecha_reunion())
                .addParameter("lugar_reunion", reunion.getLugar_reunion())
                .addParameter("hora_reunion", reunion.getHora_reunion())
                .addParameter("estado", reunion.getEstado())
                .addParameter("id_proyecto", reunion.getId_proyecto())
                .addParameter("id_glosario", reunion.getId_glosario())
                .executeUpdate().getKey();
            reunion.setId_reunion(id_count);
            return reunion;
        } 
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Reunion getReunion(Long id_reunion){
        String query = "select * from reunion where id_reunion = :id_reunion and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_reunion",id_reunion).executeAndFetchFirst(Reunion.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Reunion> getListReunion() {
        String query = "select * from reunion";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(Reunion.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override 
    public Reunion updateReunion(Reunion reunion, Long id_reunion){
        String query = "update reunion set fecha_reunion = :fecha_reunion, lugar_reunion = :lugar_reunion, hora_reunion = :hora_reunion, estado = :estado where id_reunion = :id_reunion and deleted = false";
        try(Connection conn = sql2o.open()){
            Long id = (Long) conn.createQuery(query,true)
                .addParameter("id_reunion", id_reunion)
                .addParameter("fecha_reunion", reunion.getFecha_reunion())
                .addParameter("lugar_reunion", reunion.getLugar_reunion())
                .addParameter("hora_reunion", reunion.getHora_reunion())
                .addParameter("estado", reunion.getEstado())
                .executeUpdate().getKey(Long.class);
            reunion.setId_reunion(id);
            return reunion;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteReunion(Long id_reunion){
        String query = "update reunion set deleted = true where id_reunion = :id_reunion and deleted = false";
        try(Connection conn = sql2o.open()){
            id_reunion = conn.createQuery(query).addParameter("id_reunion",id_reunion).executeUpdate().getKey(Long.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
