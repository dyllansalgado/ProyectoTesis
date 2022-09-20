package tesis.backend.repositories;
import tesis.backend.models.Usuario_proyecto;
import java.util.List;

public interface UsuarioproyectoRepository {
    public Usuario_proyecto createUsuario_proyecto(Usuario_proyecto usuario_proyecto);
    public List<Usuario_proyecto> getListUsuario_proyecto();  
    public Usuario_proyecto  getUsuario_proyecto(Long id_usuario_proyecto);
    public Usuario_proyecto updateUsuario_proyecto(Usuario_proyecto usuario_proyecto, Long id_usuario_proyecto);
    public boolean deleteUsuario_proyecto(Long id_usuario_proyecto);
    public Long countUsuario_proyecto ();
}
