package tesis.backend.models;

public class Voto {
    private Long id_voto;
    private Long id_pregunta;
    private Boolean tipo_voto;
    private Boolean deleted;
                
    //Getter and Setter
    public Long getId_voto() {
        return id_voto;
    }
    public void setId_voto(Long id_voto) {
        this.id_voto = id_voto;
    }

    public Long getId_pregunta() {
        return id_pregunta;
    }
    public void setId_pregunta(Long id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public Boolean getTipo_voto() {
        return tipo_voto;
    }
    public void setTipo_voto(Boolean tipo_voto) {
        this.tipo_voto = tipo_voto;
    }
    
    public Boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
