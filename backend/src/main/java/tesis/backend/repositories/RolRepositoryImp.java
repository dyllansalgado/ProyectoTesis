package tesis.backend.repositories;
import tesis.backend.models.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class RolRepositoryImp implements RolRepository{
    
    @Autowired
    private Sql2o sql2o;
    @Override
    public Long countRol(){
        String query = "select count(*) from rol";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }

    public Rol createRol(Rol rol){
        Long id_count= countRol();
        String query = "INSERT into rol (id_rol,tipo_rol) values (:id_rol,:tipo_rol)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(query,true).addParameter("id_rol",id_count)
                .addParameter("tipo_rol", rol.getTipo_rol())
                .executeUpdate().getKey();
            rol.setId_rol(id_count);
            return rol;
        } 
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Rol getRol(Long id_rol){
        String query = "select * from rol where id_rol = :id_rol and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_rol",id_rol).executeAndFetchFirst(Rol.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Rol> getListRol() {
        String query = "select * from rol";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(Rol.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override 
    public Rol updateRol(Rol rol, Long id_rol){
        String query = "update rol set tipo_rol = :tipo_rol  where id_rol = :id_rol and deleted = false";
        try(Connection conn = sql2o.open()){
            Long id = (Long) conn.createQuery(query,true)
                .addParameter("id_rol", id_rol)
                .addParameter("tipo_rol", rol.getTipo_rol())
                .addParameter("tipo_rol", rol.getTipo_rol())
                .executeUpdate().getKey(Long.class);
            rol.setId_rol(id);
            return rol;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteRol(Long id_rol){
        String query = "update rol set deleted = true where id_rol = :id_rol and deleted = false";
        try(Connection conn = sql2o.open()){
            id_rol = conn.createQuery(query).addParameter("id_rol",id_rol).executeUpdate().getKey(Long.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
}
