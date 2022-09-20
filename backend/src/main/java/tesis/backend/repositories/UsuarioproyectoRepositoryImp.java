package tesis.backend.repositories;
import tesis.backend.models.Usuario_proyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class UsuarioproyectoRepositoryImp implements UsuarioproyectoRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public Long countUsuario_proyecto(){
        String query = "select count(*) from usuario_proyecto";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }

    public Usuario_proyecto createUsuario_proyecto(Usuario_proyecto usuario_proyecto){
        Long id_count = countUsuario_proyecto();
        String query = "INSERT into usuario_proyecto (id_usuario_proyecto, id_usuario, id_proyecto) values (:id_usuario_proyecto,:id_usuario,:id_proyecto)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(query,true).addParameter("id_usuario_proyecto",id_count)
                .addParameter("id_usuario", usuario_proyecto.getId_usuario())
                .addParameter("id_proyecto", usuario_proyecto.getId_proyecto())
                .executeUpdate().getKey();
            usuario_proyecto.setId_usuario_proyecto(id_count);
            return usuario_proyecto;
        } 
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Usuario_proyecto getUsuario_proyecto(Long id_usuario_proyecto){
        String query = "select * from usuario_proyecto where id_usuario_proyecto = :id_usuario_proyecto and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_usuario_proyecto",id_usuario_proyecto).executeAndFetchFirst(Usuario_proyecto.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Usuario_proyecto> getListUsuario_proyecto() {
        String query = "select * from usuario_proyecto";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(Usuario_proyecto.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override 
    public Usuario_proyecto updateUsuario_proyecto(Usuario_proyecto usuario_proyecto, Long id_usuario_proyecto){
        String query = "update usuario_proyecto set id_usuario = :id_usuario, id_proyecto = :id_proyecto where id_usuario_proyecto = :id_usuario_proyecto and deleted = false";
        try(Connection conn = sql2o.open()){
            Long id = (Long) conn.createQuery(query,true)
                .addParameter("id_usuario_proyecto", id_usuario_proyecto)
                .addParameter("id_usuario", usuario_proyecto.getId_usuario())
                .addParameter("id_proyecto", usuario_proyecto.getId_proyecto())
                .executeUpdate().getKey(Long.class);
            usuario_proyecto.setId_usuario_proyecto(id);
            return usuario_proyecto;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteUsuario_proyecto(Long id_usuario_proyecto){
        String query = "update usuario_proyecto set deleted = true where id_usuario_proyecto = :id_usuario_proyecto and deleted = false";
        try(Connection conn = sql2o.open()){
            id_usuario_proyecto = conn.createQuery(query).addParameter("id_usuario_proyecto",id_usuario_proyecto).executeUpdate().getKey(Long.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }    
}
