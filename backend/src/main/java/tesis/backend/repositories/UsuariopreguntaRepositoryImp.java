package tesis.backend.repositories;
import tesis.backend.models.Usuario_pregunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class UsuariopreguntaRepositoryImp implements UsuariopreguntaRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public Long countUsuario_pregunta(){
        String query = "select count(*) from usuario_pregunta";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }

    public Usuario_pregunta createUsuario_pregunta(Usuario_pregunta usuario_pregunta){
        Long id_count = countUsuario_pregunta();
        String query = "INSERT into usuario_pregunta (id_usuario_pregunta, id_usuario, id_pregunta) values (:id_usuario_pregunta,:id_usuario,:id_pregunta)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(query,true).addParameter("id_usuario_pregunta",id_count)
                .addParameter("id_usuario", usuario_pregunta.getId_usuario())
                .addParameter("id_pregunta", usuario_pregunta.getId_pregunta())
                .executeUpdate().getKey();
            usuario_pregunta.setId_usuario_pregunta(id_count);
            return usuario_pregunta;
        } 
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Usuario_pregunta getUsuario_pregunta(Long id_usuario_pregunta){
        String query = "select * from usuario_pregunta where id_usuario_pregunta = :id_usuario_pregunta and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_usuario_pregunta",id_usuario_pregunta).executeAndFetchFirst(Usuario_pregunta.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Usuario_pregunta> getListUsuario_pregunta() {
        String query = "select * from usuario_pregunta";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(Usuario_pregunta.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override 
    public Usuario_pregunta updateUsuario_pregunta(Usuario_pregunta usuario_pregunta, Long id_usuario_pregunta){
        String query = "update usuario_pregunta set id_usuario = :id_usuario, id_pregunta = :id_pregunta where id_usuario_pregunta = :id_usuario_pregunta and deleted = false";
        try(Connection conn = sql2o.open()){
            Long id = (Long) conn.createQuery(query,true)
                .addParameter("id_usuario_pregunta", id_usuario_pregunta)
                .addParameter("id_usuario", usuario_pregunta.getId_usuario())
                .addParameter("id_pregunta", usuario_pregunta.getId_pregunta())
                .executeUpdate().getKey(Long.class);
            usuario_pregunta.setId_usuario_pregunta(id);
            return usuario_pregunta;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteUsuario_pregunta(Long id_usuario_pregunta){
        String query = "update usuario_pregunta set deleted = true where id_usuario_pregunta = :id_usuario_pregunta and deleted = false";
        try(Connection conn = sql2o.open()){
            id_usuario_pregunta = conn.createQuery(query).addParameter("id_usuario_pregunta",id_usuario_pregunta).executeUpdate().getKey(Long.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
