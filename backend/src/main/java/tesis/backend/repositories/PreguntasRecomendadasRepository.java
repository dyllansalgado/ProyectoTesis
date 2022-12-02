package tesis.backend.repositories;
import tesis.backend.models.PreguntasRecomendadas;
import java.util.List;

public interface PreguntasRecomendadasRepository {
    public List<PreguntasRecomendadas> getListPreguntas_Recomendadas();  
    public PreguntasRecomendadas getPregunta_Recomendada(Long id_pregunta_recomendada);
    public Long countPreguntas_Recomendadas ();
}
