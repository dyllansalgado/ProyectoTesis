package tesis.backend.repositories;
import tesis.backend.models.Pregunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;
import tesis.backend.models.Usuario;


@Repository
public class PreguntaRepositoryImp implements PreguntaRepository{
    @Autowired
    private Sql2o sql2o;
    @Autowired
    private final UsuarioRepository usuarioRepository;
    public PreguntaRepositoryImp(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Long countPregunta(){
        String query = "select count(*) from pregunta";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }
    @Override
    public Long countUsuarioPregunta() {
        String query = "select count(*) from usuario_pregunta";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query, true).executeAndFetchFirst(Long.class);
        return resultado + 1;
    }

    public Pregunta createPregunta(Pregunta pregunta, Long id_usuario ){
        Long id_count = countPregunta();
        Long id_countUsuarioPregunta= countUsuarioPregunta();
        Usuario usuarioCreador = usuarioRepository.getUsuario(id_usuario);
        String query = "INSERT into pregunta (id_pregunta, pregunta, estado, id_tema, creador) values (:id_pregunta,:pregunta,:estado,:id_tema,:creador)";
        String queryusuario_pregunta = "INSERT into usuario_pregunta (id_usuario_pregunta, id_usuario, id_pregunta)" +
        " values (:id_usuario_pregunta,:id_usuario,:id_pregunta)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(query,true).addParameter("id_pregunta",id_count)
                .addParameter("pregunta", pregunta.getPregunta())
                .addParameter("estado", pregunta.getEstado())
                .addParameter("id_tema", pregunta.getId_tema())
                .addParameter("creador", usuarioCreador.getNombre_usuario())
                .executeUpdate().getKey();
            pregunta.setId_pregunta(id_count);
            //Crear tabla usuario_pregunta
            conn.createQuery(queryusuario_pregunta,true).addParameter("id_usuario_pregunta",id_countUsuarioPregunta)
            .addParameter("id_usuario", id_usuario)
            .addParameter("id_pregunta",id_count)
            .executeUpdate().getKey();
            pregunta.setId_pregunta(id_countUsuarioPregunta);
            return pregunta;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Pregunta getPregunta(Long id_pregunta){
        String query = "select * from pregunta where id_pregunta = :id_pregunta and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_pregunta",id_pregunta).executeAndFetchFirst(Pregunta.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Pregunta> getListPregunta() {
        String query = "select * from pregunta";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(Pregunta.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override 
    public Pregunta updatePregunta(Pregunta pregunta, Long id_pregunta){
        String query = "update pregunta set pregunta = :pregunta, estado = :estado where id_pregunta = :id_pregunta and deleted = false";
        try(Connection conn = sql2o.open()){
            Long id = (Long) conn.createQuery(query,true)
                .addParameter("id_pregunta", id_pregunta)
                .addParameter("pregunta", pregunta.getPregunta())
                .addParameter("estado", pregunta.getEstado())
                .executeUpdate().getKey(Long.class);
            pregunta.setId_pregunta(id);
            return pregunta;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Pregunta> getListPreguntaXidTema(Long id_tema) {
        String query = "select distinct p.* from pregunta p, tema t " +
        "where p.id_tema=:id_tema and t.id_tema = p.id_tema";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_tema", id_tema).executeAndFetch(Pregunta.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public  List<Pregunta> getListPreguntaSeleccionadaXidTema(Long id_tema) {
        String query = "select distinct p.* from pregunta p, tema t " +
        "where p.id_tema=:id_tema and p.estado = true and t.id_tema = p.id_tema";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_tema", id_tema).executeAndFetch(Pregunta.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public boolean deletePregunta(Long id_pregunta){
        String query = "update pregunta set deleted = true where id_pregunta = :id_pregunta and deleted = false";
        try(Connection conn = sql2o.open()){
            id_pregunta = conn.createQuery(query).addParameter("id_pregunta",id_pregunta).executeUpdate().getKey(Long.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
