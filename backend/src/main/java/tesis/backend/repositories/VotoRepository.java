package tesis.backend.repositories;
import tesis.backend.models.Voto;
import java.util.List;

public interface VotoRepository {
    public Voto createVoto(Voto voto);
    public List<Voto> getListVoto();  
    public Voto  getVoto (Long id_voto);
    public Voto updateVoto (Voto voto, Long id_voto);
    public boolean deleteVoto (Long id_voto);
    public Long countVoto ();
}
