package tesis.backend.repositories;
import tesis.backend.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class UsuarioRepositoryImp implements UsuarioRepository {
    @Autowired
    private Sql2o sql2o;

    @Override
    public Long countUsuario(){
        String query = "select count(*) from usuario";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }

    public Usuario createUsuario(Usuario usuario){
        Long id_count = countUsuario();
        String query = "INSERT into usuario (id_usuario, nombre_usuario, apellido_usuario, contrasena_usuario, correo_usuario, id_rol) values (:id_usuario,:nombre_usuario,:apellido_usuario,:contrasena_usuario,:correo_usuario,:id_rol)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(query,true).addParameter("id_usuario",id_count)
                .addParameter("nombre_usuario", usuario.getNombre_usuario())
                .addParameter("apellido_usuario", usuario.getApellido_usuario())
                .addParameter("contrasena_usuario", usuario.getContrasena_usuario())
                .addParameter("correo_usuario", usuario.getCorreo_usuario())
                .addParameter("id_rol", usuario.getId_rol())
                .executeUpdate().getKey();
            usuario.setId_usuario(id_count);
            return usuario;
        } 
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Usuario getUsuario(Long id_usuario){
        String query = "select * from usuario where id_usuario = :id_usuario and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_usuario",id_usuario).executeAndFetchFirst(Usuario.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Usuario> getListUsuario() {
        String query = "select * from usuario";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(Usuario.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override 
    public Usuario updateUsuario(Usuario usuario, Long id_usuario){
        String query = "update usuario set nombre_usuario = :nombre_usuario, apellido_usuario = :apellido_usuario, contrasena_usuario = :contrasena_usuario  where id_usuario = :id_usuario and deleted = false";
        try(Connection conn = sql2o.open()){
            Long id = (Long) conn.createQuery(query,true)
                .addParameter("id_usuario", id_usuario)
                .addParameter("nombre_usuario", usuario.getNombre_usuario())
                .addParameter("apellido_usuario", usuario.getApellido_usuario())
                .addParameter("contrasena_usuario", usuario.getContrasena_usuario())
                .executeUpdate().getKey(Long.class);
            usuario.setId_usuario(id);
            return usuario;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteUsuario(Long id_usuario){
        String query = "update usuario set deleted = true where id_usuario = :id_usuario and deleted = false";
        try(Connection conn = sql2o.open()){
            id_usuario = conn.createQuery(query).addParameter("id_usuario",id_usuario).executeUpdate().getKey(Long.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
