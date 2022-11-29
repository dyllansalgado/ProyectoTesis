package tesis.backend.repositories;
import tesis.backend.models.Termino;
import tesis.backend.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class TerminoRepositoryImp implements TerminoRepository {
    @Autowired
    private Sql2o sql2o;
    @Autowired
    private final UsuarioRepository usuarioRepository;
    public TerminoRepositoryImp(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    @Override
    public Long countTermino(){
        String query = "select count(*) from termino";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }

    public Termino createTermino(Termino termino, Long id_usuario ){
        Long id_count = countTermino();
        Usuario usuarioCreador = usuarioRepository.getUsuario(id_usuario);
        String query = "INSERT into termino (id_termino, nombre_termino, descripcion_termino, id_glosario, correoCreador) values (:id_termino,:nombre_termino,:descripcion_termino,:id_glosario,:correoCreador)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(query,true).addParameter("id_termino",id_count)
                .addParameter("nombre_termino", termino.getNombre_termino())
                .addParameter("descripcion_termino", termino.getDescripcion_termino())
                .addParameter("id_glosario", termino.getId_glosario())
                .addParameter("correoCreador", usuarioCreador.getCorreo_usuario())
                .executeUpdate().getKey();
            termino.setId_termino(id_count);
            return termino;
        } 
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Termino getTermino(Long id_termino){
        String query = "select * from termino where id_termino = :id_termino and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_termino",id_termino).executeAndFetchFirst(Termino.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Termino> getListTermino() {
        String query = "select * from termino";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(Termino.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Termino> getListTerminoXidGlosario(Long id_glosario) {
        String query = "select te.* from termino te, glosario g " +
        "where g.id_glosario=:id_glosario and te.id_glosario = g.id_glosario and te.deleted = false and g.deleted = false ";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_glosario", id_glosario).executeAndFetch(Termino.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override 
    public Termino updateTermino(Termino termino, Long id_termino){
        String query = "update termino set nombre_termino = :nombre_termino, descripcion_termino = :descripcion_termino where id_termino = :id_termino and deleted = false";
        try(Connection conn = sql2o.open()){
            Long id = (Long) conn.createQuery(query,true)
                .addParameter("id_termino", id_termino)
                .addParameter("nombre_termino", termino.getNombre_termino())
                .addParameter("descripcion_termino", termino.getDescripcion_termino())
                .executeUpdate().getKey(Long.class);
            termino.setId_termino(id);
            return termino;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteTermino(Long id_termino){
        String query = "update termino set deleted = true where id_termino = :id_termino and deleted = false";
        try(Connection conn = sql2o.open()){
            id_termino = conn.createQuery(query).addParameter("id_termino",id_termino).executeUpdate().getKey(Long.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
