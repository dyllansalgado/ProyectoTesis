package tesis.backend.models;

public class Pregunta {
    private Long id_pregunta;
    private Long id_tema;
    private String pregunta;
    private String creador;
    private String correoCreador;
    private Boolean estado;
    private Long votos;
    public Long getVotos() {
        return votos;
    }
    public void setVotos(Long votos) {
        this.votos = votos;
    }
    private boolean deleted;
                
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

    public String getPregunta() {
        return pregunta;
    }
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Boolean getEstado() {
        return estado;
    }
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    public String getCreador() {
        return creador;
    }
    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getCorreoCreador() {
        return correoCreador;
    }
    public void setCorreoCreador(String correoCreador) {
        this.correoCreador = correoCreador;
    }
}
