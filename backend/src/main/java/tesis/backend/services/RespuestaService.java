package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.RespuestaRepository;
import tesis.backend.models.Respuesta;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class RespuestaService {
    private final RespuestaRepository respuestaRepository;
    private final Gson gson;

    RespuestaService(RespuestaRepository respuestaRepository){
        this.gson = new GsonBuilder().create();
        this.respuestaRepository = respuestaRepository;
    }
    
    @GetMapping("/respuestas/")
    ResponseEntity<String> getListRespuesta() {
        List<Respuesta> respuesta = respuestaRepository.getListRespuesta();
        return new ResponseEntity<>(gson.toJson(respuesta), HttpStatus.OK);
    }

    @GetMapping("/respuesta/{id_respuesta}")
    ResponseEntity<String> getRespuesta(@PathVariable Long id_respuesta){
        Respuesta respuesta = respuestaRepository.getRespuesta(id_respuesta);
        if(respuesta != null){
            return new ResponseEntity<>(gson.toJson(respuesta),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/respuesta/create")
    ResponseEntity<String> createRespuesta(@RequestBody String request){
        Respuesta respuestaCreado = gson.fromJson(request, Respuesta.class);
        if (respuestaCreado != null){
            respuestaCreado = respuestaRepository.createRespuesta(respuestaCreado);
            return new ResponseEntity<>(gson.toJson(respuestaCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = {"http://localhost:8080"})
    @ResponseBody
    @RequestMapping(value ="/respuesta/{id_respuesta}",  method =RequestMethod.PUT,consumes="application/json")
    ResponseEntity<String> updateRespuesta(@RequestBody String request, @PathVariable Long id_respuesta){
    Respuesta ok = gson.fromJson(request,Respuesta.class);
    Respuesta respuestaCreado = respuestaRepository.getRespuesta(id_respuesta);    
        if(respuestaCreado != null){
            if(ok.getRespuesta() != null){
                respuestaCreado.setRespuesta(ok.getRespuesta());
            }
            respuestaCreado = respuestaRepository.updateRespuesta(respuestaCreado, id_respuesta);
            return new ResponseEntity<>(gson.toJson(respuestaCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/respuesta/{id_respuesta}")
    ResponseEntity<String> deleteRespuesta(@PathVariable Long id_respuesta) {
        if (respuestaRepository.deleteRespuesta(id_respuesta)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
