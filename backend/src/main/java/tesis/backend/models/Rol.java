package tesis.backend.models;

public class Rol {
    private Long id_rol;
    private String tipo_rol;
    private boolean deleted;
    
    //Getter and Setter
    public Long getId_rol() {
        return id_rol;
    }
    public void setId_rol(Long id_rol) {
        this.id_rol = id_rol;
    }
    
    public String getTipo_rol() {
        return tipo_rol;
    }
    public void setTipo_rol(String tipo_rol) {
        this.tipo_rol = tipo_rol;
    }
    
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
