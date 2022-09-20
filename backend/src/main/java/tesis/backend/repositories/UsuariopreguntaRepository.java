package tesis.backend.repositories;
import tesis.backend.models.Usuario_pregunta;
import java.util.List;

public interface UsuariopreguntaRepository {
    public Usuario_pregunta createUsuario_pregunta(Usuario_pregunta usuario_pregunta);
    public List<Usuario_pregunta> getListUsuario_pregunta();  
    public Usuario_pregunta  getUsuario_pregunta(Long id_usuario_pregunta);
    public Usuario_pregunta updateUsuario_pregunta(Usuario_pregunta usuario_pregunta, Long id_usuario_pregunta);
    public boolean deleteUsuario_pregunta(Long id_usuario_pregunta);
    public Long countUsuario_pregunta ();
}
