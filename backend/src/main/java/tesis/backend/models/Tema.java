package tesis.backend.models;

public class Tema {
    private Long id_tema;
    private Long id_reunion;
    private String nombre_tema;
    private String descripcion_tema;
    private String estado;
    private Boolean deleted;
            
    //Getter and Setter
    public Long getId_tema() {
        return id_tema;
    }
    public void setId_tema(Long id_tema) {
        this.id_tema = id_tema;
    }

    public Long getId_reunion() {
        return id_reunion;
    }
    public void setId_reunion(Long id_reunion) {
        this.id_reunion = id_reunion;
    }

    public String getNombre_tema() {
        return nombre_tema;
    }
    public void setNombre_tema(String nombre_tema) {
        this.nombre_tema = nombre_tema;
    }
    
    public String getDescripcion_tema() {
        return descripcion_tema;
    }
    public void setDescripcion_tema(String descripcion_tema) {
        this.descripcion_tema = descripcion_tema;
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
