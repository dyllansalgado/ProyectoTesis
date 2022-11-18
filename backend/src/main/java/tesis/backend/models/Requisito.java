package tesis.backend.models;

public class Requisito {
    private Long id_requisito;
    private String nombre_requisito;
    private Boolean estado_requisito;
    private String descripcion_requisito;
    private Long prioridad;
    private Long id_tipo_requisito;
    private Long id_pregunta;
    private String creador_requisito;
    private Boolean deleted;

    public String getCreador_requisito() {
        return creador_requisito;
    }
    public void setCreador_requisito(String creador_requisito) {
        this.creador_requisito = creador_requisito;
    }
    
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId_pregunta() {
        return id_pregunta;
    }
    public void setId_pregunta(Long id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public Boolean getEstado_requisito() {
        return estado_requisito;
    }
    public void setEstado_requisito(Boolean estado_requisito) {
        this.estado_requisito = estado_requisito;
    }

    public Long getId_requisito() {
        return id_requisito;
    }
    public void setId_requisito(Long id_requisito) {
        this.id_requisito = id_requisito;
    }
    public String getNombre_requisito() {
        return nombre_requisito;
    }
    public void setNombre_requisito(String nombre_requisito) {
        this.nombre_requisito = nombre_requisito;
    }
    
    public String getDescripcion_requisito() {
        return descripcion_requisito;
    }
    public void setDescripcion_requisito(String descripcion_requisito) {
        this.descripcion_requisito = descripcion_requisito;
    }
    public Long getId_tipo_requisito() {
        return id_tipo_requisito;
    }
    public void setId_tipo_requisito(Long id_tipo_requisito) {
        this.id_tipo_requisito = id_tipo_requisito;
    }
    public Long getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(Long prioridad) {
        this.prioridad = prioridad;
    }


}
