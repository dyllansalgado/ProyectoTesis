package tesis.backend.repositories;
import tesis.backend.models.Reunion;
import java.util.List;

public interface ReunionRepository {
    public Reunion createReunion(Reunion reunion);
    public List<Reunion> getListReunion();  
    public Reunion  getReunion (Long id_reunion);
    public Reunion updateReunion (Reunion reunion, Long id_reunion);
    public List<Reunion> getListReunionXidProyecto(Long id_proyecto);
    public boolean deleteReunion (Long id_reunion);
    public Long countReunion ();
}
