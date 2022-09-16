package tesis.backend.models;

public class Respuesta {
    private Long id_respuesta;
    private String respuesta;
    private boolean deleted;
                
    //Getter and Setter
    public Long getId_respuesta() {
        return id_respuesta;
    }
    public void setId_respuesta(Long id_respuesta) {
        this.id_respuesta = id_respuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    
    public boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
