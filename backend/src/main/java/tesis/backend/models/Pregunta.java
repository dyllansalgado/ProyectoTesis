package tesis.backend.models;

public class Pregunta {
    private Long id_pregunta;
    private Long id_tema;
    private Long id_respuesta;
    private String pregunta;
    private String estado;
    private Boolean deleted;
                
    //Getter and Setter
    public Long getId_pregunta() {
        return id_pregunta;
    }
    public void setId_pregunta(Long id_pregunta) {
        this.id_pregunta = id_pregunta;
    }
    
    public Long getId_tema() {
        return id_tema;
    }
    public void setId_tema(Long id_tema) {
        this.id_tema = id_tema;
    }

    public Long getId_respuesta() {
        return id_respuesta;
    }
    public void setId_respuesta(Long id_respuesta) {
        this.id_respuesta = id_respuesta;
    }

    public String getPregunta() {
        return pregunta;
    }
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
