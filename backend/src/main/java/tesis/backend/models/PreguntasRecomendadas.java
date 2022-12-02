package tesis.backend.models;

public class PreguntasRecomendadas {
    
    private Long id_pregunta_recomendada;
    private String pregunta_recomendada;
    private boolean deleted;
    
    public Long getId_pregunta_recomendada() {
        return id_pregunta_recomendada;
    }
    public void setId_pregunta_recomendada(Long id_pregunta_recomendada) {
        this.id_pregunta_recomendada = id_pregunta_recomendada;
    }
    public String getPregunta_recomendada() {
        return pregunta_recomendada;
    }
    public void setPregunta_recomendada(String pregunta_recomendada) {
        this.pregunta_recomendada = pregunta_recomendada;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
