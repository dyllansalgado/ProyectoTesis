package tesis.backend.repositories;
import tesis.backend.models.Rol;
import java.util.List;

public interface RolRepository {
    public Rol createRol(Rol rol);
    public List<Rol> getListRol();  
    public Rol getRol(Long id_rol);
    public Rol updateRol(Rol rol, Long id_rol);
    public boolean deleteRol(Long id_rol);
    public Long countRol();
}
