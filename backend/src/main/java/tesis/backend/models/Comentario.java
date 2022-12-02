package tesis.backend.models;

public class Comentario {
    private Long id_comentario;
    private String comentario;
    private String correo_creador_comentario;
    private String nombre_creador_comentario;
    private Long id_pregunta;
    private boolean deleted;
    
    public Long getId_comentario() {
        return id_comentario;
    }
    public void setId_comentario(Long id_comentario) {
        this.id_comentario = id_comentario;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public String getCorreo_creador_comentario() {
        return correo_creador_comentario;
    }
    public void setCorreo_creador_comentario(String correo_creador_comentario) {
        this.correo_creador_comentario = correo_creador_comentario;
    }
    public String getNombre_creador_comentario() {
        return nombre_creador_comentario;
    }
    public void setNombre_creador_comentario(String nombre_creador_comentario) {
        this.nombre_creador_comentario = nombre_creador_comentario;
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
