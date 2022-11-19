package tesis.backend.repositories;
import tesis.backend.models.Requisito;
import tesis.backend.models.Pregunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;
import tesis.backend.models.Usuario;

@Repository
public class RequisitoRepositoryImp implements RequisitoRepository {
    
    @Autowired
    private Sql2o sql2o;

    @Autowired
    private final PreguntaRepository preguntaRepository;
    private final UsuarioRepository usuarioRepository;
    public RequisitoRepositoryImp(PreguntaRepository preguntaRepository, UsuarioRepository usuarioRepository) {
        this.preguntaRepository = preguntaRepository;
        this.usuarioRepository = usuarioRepository;
    }
    @Override
    public Long countRequisito(){
        String query = "select count(*) from requisito";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }

    public Requisito createRequisito(Requisito requisito, Long id_pregunta, Long id_usuario ){
        Long id_count = countRequisito();
        Pregunta preguntaAsociada = preguntaRepository.getPregunta(id_pregunta);
        Usuario usuarioCreador = usuarioRepository.getUsuario(id_usuario);
        String query = "INSERT into requisito (id_requisito, nombre_requisito, estado_requisito, descripcion_requisito, prioridad, id_pregunta, id_tipo_requisito, creador_requisito, correo_creador) values (:id_requisito,:nombre_requisito,:estado_requisito,:descripcion_requisito,:prioridad,:id_pregunta,:id_tipo_requisito,:creador_requisito,:correo_creador)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(query,true).addParameter("id_requisito",id_count)
                .addParameter("nombre_requisito", requisito.getNombre_requisito())
                .addParameter("estado_requisito", requisito.getEstado_requisito())
                .addParameter("descripcion_requisito",requisito.getDescripcion_requisito())
                .addParameter("prioridad", requisito.getPrioridad())
                .addParameter("id_pregunta", preguntaAsociada.getId_pregunta())
                .addParameter("id_tipo_requisito",requisito.getId_tipo_requisito())
                .addParameter("creador_requisito", usuarioCreador.getNombre_usuario())
                .addParameter("correo_creador", usuarioCreador.getCorreo_usuario())
                .executeUpdate().getKey();
            requisito.setId_requisito(id_count);
            return requisito;
        } 
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Requisito getRequisito(Long id_requisito){
        String query = "select * from requisito where id_requisito = :id_requisito and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_requisito",id_requisito).executeAndFetchFirst(Requisito.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Requisito> getListRequisito() {
        String query = "select * from requisito";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(Requisito.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override 
    public Requisito updateRequisito(Requisito requisito, Long id_requisito){
        String query = "update requisito set nombre_requisito = :nombre_requisito, descripcion_requisito = :descripcion_requisito, estado_requisito = :estado_requisito, prioridad = :prioridad where id_requisito = :id_requisito and deleted = false";
        try(Connection conn = sql2o.open()){
            Long id = (Long) conn.createQuery(query,true)
                .addParameter("id_requisito", id_requisito)
                .addParameter("nombre_requisito", requisito.getNombre_requisito())
                .addParameter("descripcion_requisito", requisito.getDescripcion_requisito())
                .addParameter("estado_requisito", requisito.getEstado_requisito())
                .addParameter("prioridad", requisito.getPrioridad())
                .executeUpdate().getKey(Long.class);
            requisito.setId_requisito(id);
            return requisito;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteRequisito(Long id_requisito){
        String query = "update requisito set deleted = true where id_requisito = :id_requisito and deleted = false";
        try(Connection conn = sql2o.open()){
            id_requisito = conn.createQuery(query).addParameter("id_requisito",id_requisito).executeUpdate().getKey(Long.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
