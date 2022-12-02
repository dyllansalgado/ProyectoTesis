package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.PreguntasRecomendadasRepository;
import tesis.backend.models.PreguntasRecomendadas;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class PreguntasRecomendadasService {
    private final PreguntasRecomendadasRepository preguntasRecomendadasRepository;
    private final Gson gson;

    PreguntasRecomendadasService(PreguntasRecomendadasRepository preguntasRecomendadasRepository){
        this.gson = new GsonBuilder().create();
        this.preguntasRecomendadasRepository = preguntasRecomendadasRepository;
    }

    @GetMapping("/preguntasRecomendadas/")
    ResponseEntity<String> getListPreguntas_Recomendadas(){
        List<PreguntasRecomendadas> preguntasRecomendadas = preguntasRecomendadasRepository.getListPreguntas_Recomendadas();
        return new ResponseEntity<>(gson.toJson(preguntasRecomendadas),HttpStatus.OK);
    }

    
    @GetMapping("/preguntasRecomendadas/{id_pregunta_recomendada}")
    ResponseEntity<String> getPregunta_Recomendada(@PathVariable Long id_pregunta_recomendada){
        PreguntasRecomendadas pregunta = preguntasRecomendadasRepository.getPregunta_Recomendada(id_pregunta_recomendada);
        if(pregunta != null){
            return new ResponseEntity<>(gson.toJson(pregunta),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
