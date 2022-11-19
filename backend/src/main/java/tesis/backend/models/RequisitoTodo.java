package tesis.backend.models;

public class RequisitoTodo {
    private Long id_requisito_todo;
    private Long id_tema;
    private Long id_pregunta;
    private Long id_respuesta;
    private Long id_requisito;
    private Long id_tipo_requisito;
    private String nombre_requisito;
    private Boolean estado_requisito;
    private Boolean estado_pregunta;
    private String descripcion_requisito;
    private Long prioridad;
    private String pregunta;
    private String respuesta;
    private String tipo_requisito;
    private String nombre_tipo_requisito;
    private String descripcion_tipo_requisito;
    private String creador_requisito;
    private String correo_creador;
    
    public Long getId_requisito() {
        return id_requisito;
    }
    public void setId_requisito(Long id_requisito) {
        this.id_requisito = id_requisito;
    }
    public String getCorreo_creador() {
        return correo_creador;
    }
    public void setCorreo_creador(String correo_creador) {
        this.correo_creador = correo_creador;
    }
    public String getCreador_requisito() {
        return creador_requisito;
    }
    public void setCreador_requisito(String creador_requisito) {
        this.creador_requisito = creador_requisito;
    }
    public Long getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(Long prioridad) {
        this.prioridad = prioridad;
    }

    public Long getId_requisito_todo() {
        return id_requisito_todo;
    }
    public void setId_requisito_todo(Long id_requisito_todo) {
        this.id_requisito_todo = id_requisito_todo;
    }

    public Long getId_tema() {
        return id_tema;
    }
    public void setId_tema(Long id_tema) {
        this.id_tema = id_tema;
    }

    public Long getId_pregunta() {
        return id_pregunta;
    }
    public void setId_pregunta(Long id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public Long getId_respuesta() {
        return id_respuesta;
    }
    public void setId_respuesta(Long id_respuesta) {
        this.id_respuesta = id_respuesta;
    }

    public Long getId_tipo_requisito() {
        return id_tipo_requisito;
    }
    public void setId_tipo_requisito(Long id_tipo_requisito) {
        this.id_tipo_requisito = id_tipo_requisito;
    }

    public String getNombre_requisito() {
        return nombre_requisito;
    }
    public void setNombre_requisito(String nombre_requisito) {
        this.nombre_requisito = nombre_requisito;
    }

    public Boolean getEstado_requisito() {
        return estado_requisito;
    }
    public void setEstado_requisito(Boolean estado_requisito) {
        this.estado_requisito = estado_requisito;
    }

    public Boolean getEstado_pregunta() {
        return estado_pregunta;
    }
    public void setEstado_pregunta(Boolean estado_pregunta) {
        this.estado_pregunta = estado_pregunta;
    }
    
    public String getDescripcion_requisito() {
        return descripcion_requisito;
    }
    public void setDescripcion_requisito(String descripcion_requisito) {
        this.descripcion_requisito = descripcion_requisito;
    }

    public String getPregunta() {
        return pregunta;
    }
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getTipo_requisito() {
        return tipo_requisito;
    }
    public void setTipo_requisito(String tipo_requisito) {
        this.tipo_requisito = tipo_requisito;
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
