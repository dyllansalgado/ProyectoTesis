package tesis.backend.models;

public class Termino {
    private Long id_termino;
    private Long id_glosario;
    private String nombre_termino;
    private String descripcion_termino;
    private Boolean deleted;
            
    //Getter and Setter
    public Long getId_termino() {
        return id_termino;
    }
    public void setId_termino(Long id_termino) {
        this.id_termino = id_termino;
    }

    public Long getId_glosario() {
        return id_glosario;
    }
    public void setId_glosario(Long id_glosario) {
        this.id_glosario = id_glosario;
    }

    public String getNombre_termino() {
        return nombre_termino;
    }
    public void setNombre_termino(String nombre_termino) {
        this.nombre_termino = nombre_termino;
    }

    public String getDescripcion_termino() {
        return descripcion_termino;
    }
    public void setDescripcion_termino(String descripcion_termino) {
        this.descripcion_termino = descripcion_termino;
    }

    public Boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
