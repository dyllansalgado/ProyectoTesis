package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.UsuariopreguntaRepository;
import tesis.backend.models.Usuario_pregunta;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class UsuariopreguntaService {
    private final UsuariopreguntaRepository usuariopreguntaRepository;
    private final Gson gson;

    UsuariopreguntaService(UsuariopreguntaRepository usuariopreguntaRepository){
        this.gson = new GsonBuilder().create();
        this.usuariopreguntaRepository = usuariopreguntaRepository;
    }

    @GetMapping("/usuarios_preguntas/")
    ResponseEntity<String> getListUsuario_pregunta() {
        List<Usuario_pregunta> usuariopregunta = usuariopreguntaRepository.getListUsuario_pregunta();
        return new ResponseEntity<>(gson.toJson(usuariopregunta), HttpStatus.OK);
    }

    @GetMapping("/usuario_pregunta/{id_usuario_pregunta}")
    ResponseEntity<String> getUsuario_pregunta(@PathVariable Long id_usuario_pregunta){
        Usuario_pregunta usuario_pregunta = usuariopreguntaRepository.getUsuario_pregunta(id_usuario_pregunta);
        if(usuario_pregunta != null){
            return new ResponseEntity<>(gson.toJson(usuario_pregunta),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/usuario_pregunta/create")
    ResponseEntity<String> createUsuario_pregunta(@RequestBody String request){
        Usuario_pregunta usuariopreguntaCreado = gson.fromJson(request, Usuario_pregunta.class);
        if (usuariopreguntaCreado != null){
            usuariopreguntaCreado  = usuariopreguntaRepository.createUsuario_pregunta(usuariopreguntaCreado );
            return new ResponseEntity<>(gson.toJson(usuariopreguntaCreado ),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = {"http://localhost:8080"})
    @ResponseBody
    @RequestMapping(value ="/usuario_pregunta/{id_usuario_pregunta}",  method =RequestMethod.PUT,consumes="application/json")
    ResponseEntity<String> updateUsuario_pregunta(@RequestBody String request, @PathVariable Long id_usuario_pregunta){
    Usuario_pregunta ok = gson.fromJson(request,Usuario_pregunta.class);
    Usuario_pregunta usuariopreguntaCreado = usuariopreguntaRepository.getUsuario_pregunta(id_usuario_pregunta);

        if(usuariopreguntaCreado != null){
            if(ok.getId_usuario() != null){
                usuariopreguntaCreado.setId_usuario(ok.getId_usuario());
            }
            if(ok.getId_pregunta() != null){
                usuariopreguntaCreado.setId_pregunta(ok.getId_pregunta());
            }
            usuariopreguntaCreado = usuariopreguntaRepository.updateUsuario_pregunta(usuariopreguntaCreado, id_usuario_pregunta);
            return new ResponseEntity<>(gson.toJson(usuariopreguntaCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/usuario_pregunta/{id_usuario_pregunta}")
    ResponseEntity<String> deleteUsuario_pregunta(@PathVariable Long id_usuario_pregunta) {
        if (usuariopreguntaRepository.deleteUsuario_pregunta(id_usuario_pregunta)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
