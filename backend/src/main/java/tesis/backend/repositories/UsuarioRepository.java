package tesis.backend.repositories;
import tesis.backend.models.Usuario;
import java.util.List;

public interface UsuarioRepository {
    public Usuario createUsuario(Usuario usuario);
    public List<Usuario> getListUsuario();  
    public Usuario getUsuario(Long id_usuario);
    public Usuario updateUsuario(Usuario usuario, Long id_usuario);
    public boolean deleteUsuario(Long id_usuario);
    public Long countUsuario();
}
