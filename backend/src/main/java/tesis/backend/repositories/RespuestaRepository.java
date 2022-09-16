package tesis.backend.repositories;
import tesis.backend.models.Respuesta;
import java.util.List;

public interface RespuestaRepository {
    public Respuesta createRespuesta(Respuesta respuesta);
    public List<Respuesta> getListRespuesta();  
    public Respuesta getRespuesta(Long id_respuesta);
    public Respuesta updateRespuesta(Respuesta respuesta, Long id_respuesta);
    public boolean deleteRespuesta(Long id_respuesta);
    public Long countRespuesta();
}
