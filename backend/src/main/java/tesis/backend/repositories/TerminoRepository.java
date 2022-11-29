package tesis.backend.repositories;
import tesis.backend.models.Termino;
import java.util.List;

public interface TerminoRepository {
    public Termino createTermino(Termino termino, Long id_usuario );
    public List<Termino> getListTermino();  
    public Termino  getTermino (Long id_termino);
    public Termino updateTermino (Termino glosario, Long id_termino);
    public boolean deleteTermino (Long id_termino);
    public List<Termino> getListTerminoXidGlosario(Long id_glosario);
    public Long countTermino ();
    
}
