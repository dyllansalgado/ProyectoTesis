package tesis.backend.models;

public class Usuario {
    private Long id_usuario;
    private Long id_rol;
    private String nombre_usuario;
    private String apellido_usuario;
    private String contrasena_usuario;
    private String correo_usuario;
    private boolean deleted;
    
    //Getter and Setter
    public Long getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }
    
    public Long getId_rol() {
        return id_rol;
    }
    public void setId_rol(Long id_rol) {
        this.id_rol = id_rol;
    }
    
    public String getNombre_usuario() {
        return nombre_usuario;
    }
    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getApellido_usuario() {
        return apellido_usuario;
    }
    public void setApellido_usuario(String apellido_usuario) {
        this.apellido_usuario = apellido_usuario;
    }

    public String getContrasena_usuario() {
        return contrasena_usuario;
    }
    public void setContrasena_usuario(String contrasena_usuario) {
        this.contrasena_usuario = contrasena_usuario;
    }
    
    public String getCorreo_usuario() {
        return correo_usuario;
    }
    public void setCorreo_usuario(String correo_usuario) {
        this.correo_usuario = correo_usuario;
    }

    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


}
