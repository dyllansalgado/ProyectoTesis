package tesis.backend.repositories;
import tesis.backend.models.Glosario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class GlosarioRepositoryImp implements GlosarioRepository {
    @Autowired
    private Sql2o sql2o;

    @Override
    public Long countGlosario(){
        String query = "select count(*) from glosario";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }

    public Glosario createGlosario(Glosario glosario){
        Long id_count = countGlosario();
        String query = "INSERT into glosario (id_glosario, nombre_glosario, descripcion_glosario, id_reunion) values (:id_glosario,:nombre_glosario,:descripcion_glosario,:id_reunion)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(query,true).addParameter("id_glosario",id_count)
                .addParameter("nombre_glosario", glosario.getNombre_glosario())
                .addParameter("descripcion_glosario", glosario.getDescripcion_glosario())
                .addParameter("id_reunion", glosario.getId_reunion())
                .executeUpdate().getKey();
            glosario.setId_glosario(id_count);
            return glosario;
        } 
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Glosario getGlosario(Long id_glosario){
        String query = "select * from glosario where id_glosario = :id_glosario and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_glosario",id_glosario).executeAndFetchFirst(Glosario.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Glosario> getListGlosario() {
        String query = "select * from glosario";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(Glosario.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override 
    public Glosario updateGlosario(Glosario glosario, Long id_glosario){
        String query = "update glosario set nombre_glosario = :nombre_glosario, descripcion_glosario = :descripcion_glosario where id_glosario = :id_glosario and deleted = false";
        try(Connection conn = sql2o.open()){
            Long id = (Long) conn.createQuery(query,true)
                .addParameter("id_glosario", id_glosario)
                .addParameter("nombre_glosario", glosario.getNombre_glosario())
                .addParameter("descripcion_glosario", glosario.getDescripcion_glosario())
                .executeUpdate().getKey(Long.class);
            glosario.setId_glosario(id);
            return glosario;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Glosario> getListGlosarioXidReunion(Long id_reunion) {
        String query = "select g.* from glosario g, reunion r " +
        "where r.id_reunion=:id_reunion and g.id_reunion = r.id_reunion and g.deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_reunion", id_reunion).executeAndFetch(Glosario.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public boolean deleteGlosario(Long id_glosario){
        String query = "update glosario set deleted = true where id_glosario = :id_glosario and deleted = false";
        try(Connection conn = sql2o.open()){
            id_glosario = conn.createQuery(query).addParameter("id_glosario",id_glosario).executeUpdate().getKey(Long.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
}
