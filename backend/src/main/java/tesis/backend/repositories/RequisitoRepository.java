package tesis.backend.repositories;
import tesis.backend.models.Requisito;
import java.util.List;

public interface RequisitoRepository {
    public Requisito createRequisito(Requisito requisito, Long id_pregunta, Long id_usuario );
    public List<Requisito> getListRequisito();  
    public Requisito getRequisito (Long id_requisito);
    public Long countRequisito ();
    public Requisito updateRequisito (Requisito requisito, Long id_requisito);
    public boolean deleteRequisito (Long id_requisito);
}
