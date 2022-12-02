package tesis.backend.repositories;
import tesis.backend.models.Comentario;
import tesis.backend.models.Usuario;
import tesis.backend.models.Pregunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class ComentarioRepositoryImp implements ComentarioRepository{
    @Autowired
    private Sql2o sql2o;
    @Autowired
    private final PreguntaRepository preguntaRepository;
    private final UsuarioRepository usuarioRepository;
    public ComentarioRepositoryImp(PreguntaRepository preguntaRepository,UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.preguntaRepository = preguntaRepository;
    }
    @Override
    public Long countComentario(){
        String query = "select count(*) from comentario";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }

    public Comentario createComentario(Comentario comentario, Long id_pregunta,Long id_usuario){
        Long id_count = countComentario();
        Usuario usuarioCreador = usuarioRepository.getUsuario(id_usuario);
        Pregunta preguntaAsociada = preguntaRepository.getPregunta(id_pregunta);
        String query = "INSERT into comentario (id_comentario, comentario, id_pregunta, nombre_creador_comentario,correo_creador_comentario) values (:id_comentario,:comentario,:id_pregunta,:nombre_creador_comentario,:correo_creador_comentario)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(query,true).addParameter("id_comentario",id_count)
                .addParameter("comentario", comentario.getComentario())
                .addParameter("id_pregunta", preguntaAsociada.getId_pregunta())
                .addParameter("nombre_creador_comentario", usuarioCreador.getNombre_usuario())
                .addParameter("correo_creador_comentario", usuarioCreador.getCorreo_usuario())
                .executeUpdate().getKey();
            comentario.setId_comentario(id_count);
            return comentario;
        } 
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Comentario getComentario(Long id_comentario){
        String query = "select * from comentario where id_comentario = :id_comentario and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_comentario",id_comentario).executeAndFetchFirst(Comentario.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    
    @Override
    public  List<Comentario> getListComentario() {
        String query = "select * from comentario";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(Comentario.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override 
    public Comentario updateComentario(Comentario comentario, Long id_comentario){
        String query = "update comentario set comentario = :comentario  where id_comentario = :id_comentario and deleted = false";
        try(Connection conn = sql2o.open()){
            Long id = (Long) conn.createQuery(query,true)
                .addParameter("id_comentario", id_comentario)
                .addParameter("comentario", comentario.getComentario())
                .executeUpdate().getKey(Long.class);
            comentario.setId_comentario(id);
            return comentario;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteComentario(Long id_comentario){
        String query = "update comentario set deleted = true where id_comentario = :id_comentario and deleted = false";
        try(Connection conn = sql2o.open()){
            id_comentario = conn.createQuery(query).addParameter("id_comentario",id_comentario).executeUpdate().getKey(Long.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public  List<Comentario> getListComentarioXidPregunta(Long id_pregunta) {
        String query = "select distinct c.* from pregunta p, comentario c  " +
        "where c.id_pregunta=:id_pregunta and c.id_pregunta = p.id_pregunta and c.deleted = false ";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_pregunta", id_pregunta).executeAndFetch(Comentario.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
