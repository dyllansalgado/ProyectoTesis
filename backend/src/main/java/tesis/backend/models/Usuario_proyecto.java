package tesis.backend.models;

public class Usuario_proyecto {
    
    private Long id_usuario_proyecto;
    private Long id_usuario;
    private Long id_proyecto;
    private boolean deleted;
    
    //Getter and Setter
    public Long getId_usuario_proyecto() {
        return id_usuario_proyecto;
    }
    public void setId_usuario_proyecto(Long id_usuario_proyecto) {
        this.id_usuario_proyecto = id_usuario_proyecto;
    }

    public Long getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getId_proyecto() {
        return id_proyecto;
    }
    public void setId_proyecto(Long id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
