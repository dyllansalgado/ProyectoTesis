package tesis.backend.repositories;
import tesis.backend.models.Tema;
import java.util.List;

public interface TemaRepository {
    public Tema createTema(Tema tema);
    public List<Tema> getListTema();  
    public Tema  getTema (Long id_tema);
    public Tema updateTema (Tema tema, Long id_tema);
    public boolean deleteTema(Long id_tema);
    public Long countTema ();
}
