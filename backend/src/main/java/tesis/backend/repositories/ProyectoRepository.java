package tesis.backend.repositories;
import tesis.backend.models.Proyecto;
import java.util.List;

public interface ProyectoRepository {
    public Proyecto createProyecto(Proyecto proyecto);
    public List<Proyecto > getListProyecto ();  
    public Proyecto  getProyecto (Long id_proyecto);
    public Proyecto  updateProyecto (Proyecto proyecto, Long id_proyecto);
    public boolean deleteProyecto (Long id_proyecto);
    public Long countProyecto ();
}
