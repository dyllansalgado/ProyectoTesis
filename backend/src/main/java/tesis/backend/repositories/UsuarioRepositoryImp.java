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
        String query = "INSERT into usuario (id_usuario, nombre_usuario, apellido_usuario, contrasena_usuario, correo_usuario, id_rol, token_usuario) values (:id_usuario,:nombre_usuario,:apellido_usuario,:contrasena_usuario,:correo_usuario,:id_rol,:token_usuario)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(query,true).addParameter("id_usuario",id_count)
                .addParameter("nombre_usuario", usuario.getNombre_usuario())
                .addParameter("apellido_usuario", usuario.getApellido_usuario())
                .addParameter("contrasena_usuario", usuario.getContrasena_usuario())
                .addParameter("correo_usuario", usuario.getCorreo_usuario())
                .addParameter("id_rol", usuario.getId_rol())
                .addParameter("token_usuario", 0)
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
        String query = "update usuario set nombre_usuario = :nombre_usuario, apellido_usuario = :apellido_usuario, contrasena_usuario = :contrasena_usuario, token_usuario = :token_usuario where id_usuario = :id_usuario and deleted = false";
        try(Connection conn = sql2o.open()){
            Long id = (Long) conn.createQuery(query,true)
                .addParameter("id_usuario", id_usuario)
                .addParameter("nombre_usuario", usuario.getNombre_usuario())
                .addParameter("apellido_usuario", usuario.getApellido_usuario())
                .addParameter("contrasena_usuario", usuario.getContrasena_usuario())
                .addParameter("token_usuario", usuario.getToken_usuario())
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

    @Override
    public Usuario loginUsuario(Usuario usuario) {
        try(Connection conn = sql2o.open()){
            List<Usuario> findUsers = conn.createQuery("select * from usuario where nombre_usuario=:nombre_usuario and contrasena_usuario=:contrasena_usuario")
                .addParameter("nombre_usuario", usuario.getNombre_usuario())
                .addParameter("contrasena_usuario", usuario.getContrasena_usuario())
                .executeAndFetch(Usuario.class);
            if(findUsers.size() == 1){
                System.out.println("Usuario ingresado con exito");
                Usuario usuarioRespuesta = findUsers.get(0);
                usuarioRespuesta.setToken_usuario(1);
                updateUsuario(usuarioRespuesta,usuarioRespuesta.getId_usuario());
                System.out.println(usuarioRespuesta.getToken_usuario());
                return usuarioRespuesta;
            }else{
                System.out.println("Clave errada");
                return null;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public Usuario getUserToken(String token_usuario){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM usuario WHERE token_usuario = :v_token")
                    .addParameter("v_token", token_usuario)
                    .executeAndFetchFirst(Usuario.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }




    //AÑADIDA AHORA SI FALLA BORRAR
    @Override
    public String logOut(Usuario usuario){
        try(Connection conn = sql2o.open()){
            List<Usuario> findUsers = conn.createQuery("select * from usuario where id_usuario=:id_usuario")
                    .addParameter("id_usuario", usuario.getId_usuario())
                    .executeAndFetch(Usuario.class);
            if(findUsers.size() == 1){
                try(Connection con = sql2o.open()){
                    usuario.setToken_usuario(0);
                    return "Desconeccion exitosa";
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    return  "Desconeccion errada";
                }
            }else{
                return "Desconeccion fallida";
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return " Desconeccion fallida";
        }
    }


}
