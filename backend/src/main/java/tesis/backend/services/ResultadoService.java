package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.ResultadoRepository;
import tesis.backend.models.Resultado;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class ResultadoService {
    private final ResultadoRepository resultadoRepository;
    private final Gson gson;

    ResultadoService(ResultadoRepository resultadoRepository){
        this.gson = new GsonBuilder().create();
        this.resultadoRepository = resultadoRepository;
    }
    @GetMapping("/preguntaSeleccionadaTema2/{id_tema}")
    ResponseEntity<String>getListPreguntaSeleccionadaXidTema2(@PathVariable Long id_tema) {
        List<Resultado> preguntaSeleccionada = resultadoRepository.getListPreguntaSeleccionadaXidTema2(id_tema);
        return new ResponseEntity<>(gson.toJson(preguntaSeleccionada), HttpStatus.OK);
    }
}
