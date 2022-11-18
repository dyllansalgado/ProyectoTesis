package tesis.backend.models;
import java.util.Date;

public class Proyecto {
    private Long id_proyecto;
    private String nombre_proyecto;
    private Date fecha_inicio_proyecto;
    private Boolean estado_proyecto;
    private String objetivo_proyecto;
    private String contrasena;
    private String creadorProyecto;
    private boolean deleted;
        
    //Getter and Setter
    public Long getId_proyecto() {
        return id_proyecto;
    }
    public void setId_proyecto(Long id_proyecto) {
        this.id_proyecto = id_proyecto;
    }
    
    public String getCreadorProyecto() {
        return creadorProyecto;
    }
    public void setCreadorProyecto(String creadorProyecto) {
        this.creadorProyecto = creadorProyecto;
    }
    
    public String getNombre_proyecto() {
        return nombre_proyecto;
    }
    public void setNombre_proyecto(String nombre_proyecto) {
        this.nombre_proyecto = nombre_proyecto;
    }

    public Date getFecha_inicio_proyecto() {
        return fecha_inicio_proyecto;
    }
    public void setFecha_inicio_proyecto(Date fecha_inicio_proyecto) {
        this.fecha_inicio_proyecto = fecha_inicio_proyecto;
    }

    public Boolean getEstado_proyecto() {
        return estado_proyecto;
    }
    public void setEstado_proyecto(Boolean estado_proyecto) {
        this.estado_proyecto = estado_proyecto;
    }

    public String getObjetivo_proyecto() {
        return objetivo_proyecto;
    }
    public void setObjetivo_proyecto(String objetivo_proyecto) {
        this.objetivo_proyecto = objetivo_proyecto;
    }

    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
