package tesis.backend.models;
import java.util.Date;

public class Reunion {
    private Long id_reunion;
    private Long id_proyecto;
    private Date fecha_reunion;
    private String lugar_reunion;
    private boolean deleted;
        
    //Getter and Setter
    public Long getId_reunion() {
        return id_reunion;
    }
    public void setId_reunion(Long id_reunion) {
        this.id_reunion = id_reunion;
    }
    
    public Long getId_proyecto() {
        return id_proyecto;
    }
    public void setId_proyecto(Long id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public Date getFecha_reunion() {
        return fecha_reunion;
    }
    public void setFecha_reunion(Date fecha_reunion) {
        this.fecha_reunion = fecha_reunion;
    }

    public String getLugar_reunion() {
        return lugar_reunion;
    }
    public void setLugar_reunion(String lugar_reunion) {
        this.lugar_reunion = lugar_reunion;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
