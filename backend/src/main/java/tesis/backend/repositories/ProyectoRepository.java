package tesis.backend.repositories;
import tesis.backend.models.Proyecto;
import java.util.List;

public interface ProyectoRepository {
    public Proyecto createProyecto(Proyecto proyecto,  Long id_usuario );
    public List<Proyecto> getListProyecto ();  
    public List<Proyecto> getListUsuarioProyectos(Long id_usuario);
    public List<Proyecto> getListUsuarioProyectosNoPertenece(Long id_usuario);
    public Proyecto  ingresarUsuarioAProyecto(Proyecto proyecto, Long id_usuario, Long id_proyecto);
    public Proyecto  getProyecto (Long id_proyecto);
    public Proyecto  updateProyecto (Proyecto proyecto, Long id_proyecto);
    public boolean deleteProyecto (Long id_proyecto);
    public Long countProyecto ();
    public Long countUsuarioProyecto();
}
