package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.UsuarioproyectoRepository;
import tesis.backend.models.Usuario_proyecto;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class UsuarioproyectoService {
    private final UsuarioproyectoRepository usuarioproyectoRepository;
    private final Gson gson;

    UsuarioproyectoService(UsuarioproyectoRepository usuarioproyectoRepository){
        this.gson = new GsonBuilder().create();
        this.usuarioproyectoRepository = usuarioproyectoRepository;
    }
    @GetMapping("/usuarios_proyectos/")
    ResponseEntity<String> getListUsuario_proyecto() {
        List<Usuario_proyecto> usuarioproyecto = usuarioproyectoRepository.getListUsuario_proyecto();
        return new ResponseEntity<>(gson.toJson(usuarioproyecto), HttpStatus.OK);
    }

    @GetMapping("/usuario_proyecto/{id_usuario_proyecto}")
    ResponseEntity<String> getUsuario_proyecto(@PathVariable Long id_usuario_proyecto){
        Usuario_proyecto usuario_proyecto = usuarioproyectoRepository.getUsuario_proyecto(id_usuario_proyecto);
        if(usuario_proyecto != null){
            return new ResponseEntity<>(gson.toJson(usuario_proyecto),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/usuario_proyecto/create")
    ResponseEntity<String> createUsuario_proyecto(@RequestBody String request){
        Usuario_proyecto usuarioproyectoCreado = gson.fromJson(request, Usuario_proyecto.class);
        if (usuarioproyectoCreado != null){
            usuarioproyectoCreado  = usuarioproyectoRepository.createUsuario_proyecto(usuarioproyectoCreado );
            return new ResponseEntity<>(gson.toJson(usuarioproyectoCreado ),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = {"http://localhost:8080"})
    @ResponseBody
    @RequestMapping(value ="/usuario_proyecto/{id_usuario_proyecto}",  method =RequestMethod.PUT,consumes="application/json")
    ResponseEntity<String> updateUsuario_proyecto(@RequestBody String request, @PathVariable Long id_usuario_proyecto){
    Usuario_proyecto ok = gson.fromJson(request,Usuario_proyecto.class);
    Usuario_proyecto usuarioproyectoCreado = usuarioproyectoRepository.getUsuario_proyecto(id_usuario_proyecto);

        if(usuarioproyectoCreado != null){
            if(ok.getId_usuario() != null){
                usuarioproyectoCreado.setId_usuario(ok.getId_usuario());
            }
            if(ok.getId_proyecto() != null){
                usuarioproyectoCreado.setId_proyecto(ok.getId_proyecto());
            }
            usuarioproyectoCreado = usuarioproyectoRepository.updateUsuario_proyecto(usuarioproyectoCreado, id_usuario_proyecto);
            return new ResponseEntity<>(gson.toJson(usuarioproyectoCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/usuario_proyecto/{id_usuario_proyecto}")
    ResponseEntity<String> deleteUsuario_proyecto(@PathVariable Long id_usuario_proyecto) {
        if (usuarioproyectoRepository.deleteUsuario_proyecto(id_usuario_proyecto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
