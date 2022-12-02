package tesis.backend.repositories;
import tesis.backend.models.Comentario;
import java.util.List;

public interface ComentarioRepository {
    public Comentario createComentario(Comentario comentario, Long id_pregunta,Long id_usuario);
    public List<Comentario> getListComentario();  
    public Comentario getComentario(Long id_comentario);
    public List<Comentario> getListComentarioXidPregunta(Long id_pregunta);
    public Comentario updateComentario(Comentario comentario, Long id_comentario);
    public boolean deleteComentario(Long id_comentario);
    public Long countComentario();
}
