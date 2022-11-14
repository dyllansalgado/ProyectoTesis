package tesis.backend.repositories;
import tesis.backend.models.Resultado;
import java.util.List;

public interface ResultadoRepository {

    public List<Resultado> getListPreguntaSeleccionadaXidTema2(Long id_tema);
}
