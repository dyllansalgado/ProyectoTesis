package tesis.backend.repositories;
import tesis.backend.models.Tema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class TemaRepositoryImp implements TemaRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public Long countTema(){
        String query = "select count(*) from tema";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }

    public Tema createTema(Tema tema){
        Long id_count = countTema();
        String query = "INSERT into tema (id_tema, nombre_tema, descripcion_tema, id_reunion) values (:id_tema,:nombre_tema,:descripcion_tema,:id_reunion)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(query,true).addParameter("id_tema",id_count)
                .addParameter("nombre_tema", tema.getNombre_tema())
                .addParameter("descripcion_tema", tema.getDescripcion_tema())
                .addParameter("id_reunion", tema.getId_reunion())
                .executeUpdate().getKey();
            tema.setId_tema(id_count);
            return tema;
        } 
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Tema> getListTemaXidReunion(Long id_reunion) {
        String query = "select t.* from tema t, reunion r " +
        "where r.id_reunion=:id_reunion and t.id_reunion = r.id_reunion";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_reunion", id_reunion).executeAndFetch(Tema.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Tema getTema(Long id_tema){
        String query = "select * from tema where id_tema = :id_tema and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_tema",id_tema).executeAndFetchFirst(Tema.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Tema> getListTema() {
        String query = "select * from tema";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(Tema.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override 
    public Tema updateTema(Tema tema, Long id_tema){
        String query = "update tema set nombre_tema = :nombre_tema, descripcion_tema = :descripcion_tema where id_tema = :id_tema and deleted = false";
        try(Connection conn = sql2o.open()){
            Long id = (Long) conn.createQuery(query,true)
                .addParameter("id_tema", id_tema)
                .addParameter("nombre_tema", tema.getNombre_tema())
                .addParameter("descripcion_tema", tema.getDescripcion_tema())
                .executeUpdate().getKey(Long.class);
            tema.setId_tema(id);
            return tema;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteTema(Long id_tema){
        String query = "update tema set deleted = true where id_tema = :id_tema and deleted = false";
        try(Connection conn = sql2o.open()){
            id_tema = conn.createQuery(query).addParameter("id_tema",id_tema).executeUpdate().getKey(Long.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
