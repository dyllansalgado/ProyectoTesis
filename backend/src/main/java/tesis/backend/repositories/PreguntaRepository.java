package tesis.backend.repositories;
import tesis.backend.models.Pregunta;
import java.util.List;

public interface PreguntaRepository {
    public Pregunta createPregunta(Pregunta pregunta);
    public List<Pregunta> getListPregunta();  
    public Pregunta  getPregunta (Long id_pregunta);
    public Pregunta updatePregunta (Pregunta pregunta, Long id_pregunta);
    public boolean deletePregunta (Long id_pregunta);
    public Long countPregunta ();
}
