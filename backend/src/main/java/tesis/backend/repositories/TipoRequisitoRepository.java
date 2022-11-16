package tesis.backend.repositories;
import tesis.backend.models.TipoRequisito;
import java.util.List;

public interface TipoRequisitoRepository {
    public List<TipoRequisito> getListTipo_Requisitos();  
    public TipoRequisito getTipo_Requisito(Long id_tipo_requisito);
    public Long countTipo_Requisito ();
}
