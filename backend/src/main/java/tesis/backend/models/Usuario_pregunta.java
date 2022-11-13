package tesis.backend.models;

public class Usuario_pregunta {
    private Long id_usuario_pregunta;
    private Long id_usuario;
    private Long id_pregunta;
    private boolean deleted;
                    
    //Getter and Setter
    public Long getId_usuario_pregunta() {
        return id_usuario_pregunta;
    }
    public void setId_usuario_pregunta(Long id_usuario_pregunta) {
        this.id_usuario_pregunta = id_usuario_pregunta;
    }

    public Long getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getId_pregunta() {
        return id_pregunta;
    }
    public void setId_pregunta(Long id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
