package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.PreguntaRepository;
import tesis.backend.models.Pregunta;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class PreguntaService {
    private final PreguntaRepository preguntaRepository;
    private final Gson gson;
    
    PreguntaService(PreguntaRepository preguntaRepository){
        this.gson = new GsonBuilder().create();
        this.preguntaRepository = preguntaRepository;
    }

    @GetMapping("/preguntas/")
    ResponseEntity<String> getListPregunta() {
        List<Pregunta> pregunta = preguntaRepository.getListPregunta();
        return new ResponseEntity<>(gson.toJson(pregunta), HttpStatus.OK);
    }

    @GetMapping("/pregunta/{id_pregunta}")
    ResponseEntity<String> getPregunta(@PathVariable Long id_pregunta){
        Pregunta pregunta = preguntaRepository.getPregunta(id_pregunta);
        if(pregunta != null){
            return new ResponseEntity<>(gson.toJson(pregunta),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/pregunta/create/{id_usuario}")
    ResponseEntity<String> createPregunta(@RequestBody String request, @PathVariable Long id_usuario){
        Pregunta preguntaCreado = gson.fromJson(request, Pregunta.class);
        if (preguntaCreado!= null){
            preguntaCreado = preguntaRepository.createPregunta(preguntaCreado, id_usuario);
            return new ResponseEntity<>(gson.toJson(preguntaCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = {"http://localhost:8080"})
    @ResponseBody
    @RequestMapping(value ="/pregunta/{id_pregunta}",  method =RequestMethod.PUT,consumes="application/json")
    ResponseEntity<String> updatePregunta(@RequestBody String request, @PathVariable Long id_pregunta){
     Pregunta ok = gson.fromJson(request,Pregunta.class);
     Pregunta preguntaCreado = preguntaRepository.getPregunta(id_pregunta);

        if(preguntaCreado != null){
            if(ok.getPregunta() != null){
                preguntaCreado.setPregunta(ok.getPregunta());
            }
            if(ok.getEstado() != null){
                preguntaCreado.setEstado(ok.getEstado());
            }
            preguntaCreado  = preguntaRepository.updatePregunta(preguntaCreado , id_pregunta);
            return new ResponseEntity<>(gson.toJson(preguntaCreado ),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/preguntaTema/{id_tema}")
    ResponseEntity<String>getListPreguntaXidTema(@PathVariable Long id_tema) {
        List<Pregunta> pregunta = preguntaRepository.getListPreguntaXidTema(id_tema);
        return new ResponseEntity<>(gson.toJson(pregunta), HttpStatus.OK);
    }

    @GetMapping("/preguntaSeleccionadaTema/{id_tema}")
    ResponseEntity<String>getListPreguntaSeleccionadaXidTema(@PathVariable Long id_tema) {
        List<Pregunta> preguntaSeleccionada = preguntaRepository.getListPreguntaSeleccionadaXidTema(id_tema);
        return new ResponseEntity<>(gson.toJson(preguntaSeleccionada), HttpStatus.OK);
    }
    
    @DeleteMapping("/pregunta/{id_pregunta}")
    ResponseEntity<String> deletePregunta(@PathVariable Long id_pregunta) {
        if (preguntaRepository.deletePregunta(id_pregunta)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
