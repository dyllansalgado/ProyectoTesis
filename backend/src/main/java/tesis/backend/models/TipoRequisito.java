package tesis.backend.models;

public class TipoRequisito {

    private Long id_tipo_requisito;
    private String nombre_tipo_requisito;
    private String descripcion_tipo_requisito;
    private boolean deleted;

    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public Long getId_tipo_requisito() {
        return id_tipo_requisito;
    }
    public void setId_tipo_requisito(Long id_tipo_requisito) {
        this.id_tipo_requisito = id_tipo_requisito;
    }

    public String getNombre_tipo_requisito() {
        return nombre_tipo_requisito;
    }
    public void setNombre_tipo_requisito(String nombre_tipo_requisito) {
        this.nombre_tipo_requisito = nombre_tipo_requisito;
    }

    public String getDescripcion_tipo_requisito() {
        return descripcion_tipo_requisito;
    }
    public void setDescripcion_tipo_requisito(String descripcion_tipo_requisito) {
        this.descripcion_tipo_requisito = descripcion_tipo_requisito;
    }

}