package tesis.backend.repositories;
import tesis.backend.models.Voto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class VotoRepositoryImp implements VotoRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public Long countVoto(){
        String query = "select count(*) from voto";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }

    @Override
    public Long countVoto2(Long id_pregunta){
        String query = "select count(*) from voto  where id_pregunta=:id_pregunta";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query).addParameter("id_pregunta", id_pregunta).executeAndFetchFirst(Long.class);
        return resultado; 
    }

    public Voto createVoto(Voto voto){
        Long id_count= countVoto();
        String query= "";
        try(Connection conne = sql2o.open()){
            List<Voto> findVoto = conne.createQuery("select * from voto where id_usuario=:id_usuario and id_pregunta=:id_pregunta")
                .addParameter("id_usuario", voto.getId_usuario())
                .addParameter("id_pregunta", voto.getId_pregunta())
                .executeAndFetch(Voto.class);
            if(findVoto.size() == 1){
                return null;
            }else{
                query = "INSERT into voto (id_voto,tipo_voto,id_pregunta,id_usuario) values (:id_voto,:tipo_voto,:id_pregunta,:id_usuario)";
                try(Connection conn = sql2o.open()){
                    conn.createQuery(query,true).addParameter("id_voto",id_count)
                        .addParameter("tipo_voto", voto.getTipo_voto())
                        .addParameter("id_pregunta", voto.getId_pregunta())
                        .addParameter("id_usuario", voto.getId_usuario())
                        .executeUpdate().getKey();
                    voto.setId_voto(id_count);
                    return voto;
                } 
                catch(Exception e){
                    System.out.println(e.getMessage());
                    return null;
                }
            }
        }
    }
    
    @Override
    public Voto getVoto(Long id_voto){
        String query = "select * from voto where id_voto = :id_voto and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_voto",id_voto).executeAndFetchFirst(Voto.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Voto> getListVoto() {
        String query = "select * from voto";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(Voto.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override 
    public Voto updateVoto(Voto voto, Long id_voto){
        String query = "update voto set tipo_voto = :tipo_voto  where id_voto = :id_voto and deleted = false";
        try(Connection conn = sql2o.open()){
            Long id = (Long) conn.createQuery(query,true)
                .addParameter("id_voto", id_voto)
                .addParameter("tipo_voto", voto.getTipo_voto())
                .executeUpdate().getKey(Long.class);
            voto.setId_voto(id);
            return voto;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteVoto(Long id_voto){
        String query = "update voto set deleted = true where id_voto = :id_voto and deleted = false";
        try(Connection conn = sql2o.open()){
            id_voto = conn.createQuery(query).addParameter("id_voto",id_voto).executeUpdate().getKey(Long.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
