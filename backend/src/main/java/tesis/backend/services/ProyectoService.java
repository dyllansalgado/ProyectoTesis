package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.ProyectoRepository;
import tesis.backend.models.Proyecto;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class ProyectoService {
    private final ProyectoRepository proyectoRepository;
    private final Gson gson;

    ProyectoService(ProyectoRepository proyectoRepository){
        this.gson = new GsonBuilder().create();
        this.proyectoRepository = proyectoRepository;
    }

    @GetMapping("/proyectos/")
    ResponseEntity<String> getListProyecto() {
        List<Proyecto> proyecto = proyectoRepository.getListProyecto();
        return new ResponseEntity<>(gson.toJson(proyecto), HttpStatus.OK);
    }

    @GetMapping("/usuarioProyectos/{idUsuario}")
    ResponseEntity<String> getListUsuarioProyectos(@PathVariable Long idUsuario) {
        List<Proyecto> proyectos = proyectoRepository.getListUsuarioProyectos(idUsuario);
        return new ResponseEntity<>(gson.toJson(proyectos), HttpStatus.OK);
    }

    @GetMapping("/usuarioProyectosGeneral/{idUsuario}")
    ResponseEntity<String> getListUsuarioProyectosNoPertenece(@PathVariable Long idUsuario) {
        List<Proyecto> proyectos = proyectoRepository.getListUsuarioProyectosNoPertenece(idUsuario);
        return new ResponseEntity<>(gson.toJson(proyectos), HttpStatus.OK);
    }

    @GetMapping("/proyecto/{id_proyecto}")
    ResponseEntity<String> getProyecto(@PathVariable Long id_proyecto){
        Proyecto proyecto = proyectoRepository.getProyecto(id_proyecto);
        if(proyecto != null){
            return new ResponseEntity<>(gson.toJson(proyecto),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/proyecto/create/{id_usuario}")
    ResponseEntity<String> createProyecto(@RequestBody String request, @PathVariable Long id_usuario){
        Proyecto proyectoCreado = gson.fromJson(request, Proyecto.class);

        if (proyectoCreado!= null){
            proyectoCreado = proyectoRepository.createProyecto(proyectoCreado, id_usuario);
            return new ResponseEntity<>(gson.toJson(proyectoCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = {"http://localhost:8080"})
    @ResponseBody
    @RequestMapping(value ="/proyecto/{id_proyecto}",  method =RequestMethod.PUT,consumes="application/json")
    ResponseEntity<String> updateProyecto(@RequestBody String request, @PathVariable Long id_proyecto){
     Proyecto ok = gson.fromJson(request,Proyecto.class);
     Proyecto proyectoCreado = proyectoRepository.getProyecto(id_proyecto);

        if(proyectoCreado != null){
            if(ok.getNombre_proyecto() != null){
                proyectoCreado.setNombre_proyecto(ok.getNombre_proyecto());
            }
            if(ok.getFecha_inicio_proyecto() != null){
                proyectoCreado.setFecha_inicio_proyecto(ok.getFecha_inicio_proyecto());
            }
            if(ok.getEstado_proyecto() != null){
                proyectoCreado.setEstado_proyecto(ok.getEstado_proyecto());
            }
            if(ok.getObjetivo_proyecto() != null){
                proyectoCreado.setObjetivo_proyecto(ok.getObjetivo_proyecto());
            }
            if(ok.getContrasena() != null){
                proyectoCreado.setContrasena(ok.getContrasena());
            }
            proyectoCreado = proyectoRepository.updateProyecto(proyectoCreado, id_proyecto);
            return new ResponseEntity<>(gson.toJson(proyectoCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/proyecto/{id_proyecto}")
    ResponseEntity<String> deleteProyecto(@PathVariable Long id_proyecto) {
        if (proyectoRepository.deleteProyecto(id_proyecto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
