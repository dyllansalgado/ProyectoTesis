package tesis.backend.repositories;
import tesis.backend.models.Pregunta;
import java.util.List;

public interface PreguntaRepository {
    public Pregunta createPregunta(Pregunta pregunta, Long id_usuario );
    public List<Pregunta> getListPregunta();  
    public Pregunta  getPregunta (Long id_pregunta);
    public Pregunta updatePregunta (Pregunta pregunta, Long id_pregunta);
    public List<Pregunta> getListPreguntaXidTema(Long id_tema);
    public List<Pregunta> getListPreguntaSeleccionadaXidTema(Long id_tema);
    public boolean deletePregunta (Long id_pregunta);
    public Long countPregunta ();
    public Long countUsuarioPregunta();
}
