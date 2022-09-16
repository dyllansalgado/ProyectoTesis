package tesis.backend.repositories;
import tesis.backend.models.Glosario;
import java.util.List;

public interface GlosarioRepository {
    public Glosario createGlosario(Glosario glosario);
    public List<Glosario> getListGlosario();  
    public Glosario  getGlosario (Long id_glosario);
    public Glosario updateGlosario (Glosario glosario, Long id_glosario);
    public boolean deleteGlosario (Long id_glosario);
    public Long countGlosario ();
}
