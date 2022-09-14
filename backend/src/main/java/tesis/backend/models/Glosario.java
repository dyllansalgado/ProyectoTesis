package tesis.backend.models;

public class Glosario {
    private Long id_glosario;
    private String nombre_glosario;
    private String descripcion_glosario;
    private boolean deleted;
            
    //Getter and Setter
    public Long getId_glosario() {
        return id_glosario;
    }
    public void setId_glosario(Long id_glosario) {
        this.id_glosario = id_glosario;
    }

    public String getNombre_glosario() {
        return nombre_glosario;
    }
    public void setNombre_glosario(String nombre_glosario) {
        this.nombre_glosario = nombre_glosario;
    }

    public String getDescripcion_glosario() {
        return descripcion_glosario;
    }
    public void setDescripcion_glosario(String descripcion_glosario) {
        this.descripcion_glosario = descripcion_glosario;
    }

    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
