package tesis.backend.repositories;
import tesis.backend.models.Proyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class ProyectoRepositoryImp implements ProyectoRepository {
    @Autowired
    private Sql2o sql2o;

    @Override
    public Long countProyecto(){
        String query = "select count(*) from proyecto";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }

    public Proyecto createProyecto(Proyecto proyecto){
        Long id_count = countProyecto();
        String query = "INSERT into proyecto (id_proyecto, nombre_proyecto, fecha_inicio_proyecto, estado_proyecto, objetivo_proyecto, contrasena) values (:id_proyecto,:nombre_proyecto,:fecha_inicio_proyecto,:estado_proyecto,:objetivo_proyecto,:contrasena)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(query,true).addParameter("id_proyecto",id_count)
                .addParameter("nombre_proyecto", proyecto.getNombre_proyecto())
                .addParameter("fecha_inicio_proyecto", proyecto.getFecha_inicio_proyecto())
                .addParameter("estado_proyecto", proyecto.getEstado_proyecto())
                .addParameter("objetivo_proyecto", proyecto.getObjetivo_proyecto())
                .addParameter("contrasena", proyecto.getContrasena())
                .executeUpdate().getKey();
            proyecto.setId_proyecto(id_count);
            return proyecto;
        } 
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Proyecto getProyecto(Long id_proyecto){
        String query = "select * from proyecto where id_proyecto = :id_proyecto and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_proyecto",id_proyecto).executeAndFetchFirst(Proyecto.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public  List<Proyecto> getListProyecto() {
        String query = "select * from proyecto";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(Proyecto.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override 
    public Proyecto updateProyecto(Proyecto proyecto, Long id_proyecto){
        String query = "update proyecto set nombre_proyecto = :nombre_proyecto, fecha_inicio_proyecto = :fecha_inicio_proyecto, estado_proyecto = :estado_proyecto, objetivo_proyecto = :objetivo_proyecto, contrasena = :contrasena  where id_proyecto = :id_proyecto and deleted = false";
        try(Connection conn = sql2o.open()){
            Long id = (Long) conn.createQuery(query,true)
                .addParameter("id_proyecto", id_proyecto)
                .addParameter("nombre_proyecto", proyecto.getNombre_proyecto())
                .addParameter("fecha_inicio_proyecto", proyecto.getFecha_inicio_proyecto())
                .addParameter("estado_proyecto", proyecto.getEstado_proyecto())
                .addParameter("objetivo_proyecto", proyecto.getObjetivo_proyecto())
                .addParameter("contrasena", proyecto.getContrasena())
                .executeUpdate().getKey(Long.class);
            proyecto.setId_proyecto(id);
            return proyecto;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteProyecto(Long id_proyecto){
        String query = "update proyecto set deleted = true where id_proyecto = :id_proyecto and deleted = false";
        try(Connection conn = sql2o.open()){
            id_proyecto = conn.createQuery(query).addParameter("id_proyecto",id_proyecto).executeUpdate().getKey(Long.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
