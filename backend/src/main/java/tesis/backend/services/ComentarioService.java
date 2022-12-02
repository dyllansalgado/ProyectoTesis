package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.ComentarioRepository;
import tesis.backend.models.Comentario;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class ComentarioService {
    private final ComentarioRepository comentarioRepository;
    private final Gson gson;
    
    ComentarioService(ComentarioRepository comentarioRepository){
        this.gson = new GsonBuilder().create();
        this.comentarioRepository = comentarioRepository;
    }

    @GetMapping("/comentarios/")
    ResponseEntity<String> getListComentario() {
        List<Comentario> comentario = comentarioRepository.getListComentario();
        return new ResponseEntity<>(gson.toJson(comentario), HttpStatus.OK);
    }

    @GetMapping("/comentario/{id_comentario}")
    ResponseEntity<String> getComentario(@PathVariable Long id_comentario){
        Comentario comentario = comentarioRepository.getComentario(id_comentario);
        if(comentario != null){
            return new ResponseEntity<>(gson.toJson(comentario),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/comentario/create/{id_pregunta}/{id_usuario}")
    ResponseEntity<String> createComentario(@RequestBody String request,@PathVariable Long id_pregunta,@PathVariable Long id_usuario){
        Comentario comentarioCreado = gson.fromJson(request, Comentario.class);
        if (comentarioCreado!= null){
            comentarioCreado = comentarioRepository.createComentario(comentarioCreado,id_pregunta,id_usuario);
            return new ResponseEntity<>(gson.toJson(comentarioCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = {"http://localhost:8080"})
    @ResponseBody
    @RequestMapping(value ="/comentario/{id_comentario}",  method =RequestMethod.PUT,consumes="application/json")
    ResponseEntity<String> updateComentario(@RequestBody String request, @PathVariable Long id_comentario){
    Comentario ok = gson.fromJson(request,Comentario.class);
    Comentario comentarioCreado = comentarioRepository.getComentario(id_comentario);
        if(comentarioCreado != null){
            if(ok.getComentario() != null){
                comentarioCreado.setComentario(ok.getComentario());
            }
            comentarioCreado = comentarioRepository.updateComentario(comentarioCreado, id_comentario);
            return new ResponseEntity<>(gson.toJson(comentarioCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/comentario/{id_comentario}")
    ResponseEntity<String> deleteComentario(@PathVariable Long id_comentario) {
        if (comentarioRepository.deleteComentario(id_comentario)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/comentarioPregunta/{id_pregunta}")
    ResponseEntity<String>getListComentarioXidPregunta(@PathVariable Long id_pregunta) {
        List<Comentario> comentario = comentarioRepository.getListComentarioXidPregunta(id_pregunta);
        return new ResponseEntity<>(gson.toJson(comentario), HttpStatus.OK);
    }
}
