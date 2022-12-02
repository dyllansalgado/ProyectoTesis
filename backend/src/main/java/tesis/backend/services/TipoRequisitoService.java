package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.TipoRequisitoRepository;
import tesis.backend.models.TipoRequisito;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class TipoRequisitoService {
    private final TipoRequisitoRepository tipoRequisitoRepository;
    private final Gson gson;

    TipoRequisitoService(TipoRequisitoRepository tipoRequisitoRepository){
        this.gson = new GsonBuilder().create();
        this.tipoRequisitoRepository = tipoRequisitoRepository;
    }

    @GetMapping("/tiposRequisitos/")
    ResponseEntity<String> getListTipo_Requisitos(){
        List<TipoRequisito> tiposrequisitos = tipoRequisitoRepository.getListTipo_Requisitos();
        return new ResponseEntity<>(gson.toJson(tiposrequisitos),HttpStatus.OK);
    }

    
    @GetMapping("/tipoRequisito/{id_tipo_requisito}")
    ResponseEntity<String> getTipo_Requisito(@PathVariable Long id_tipo_requisito){
        TipoRequisito tipo = tipoRequisitoRepository.getTipo_Requisito(id_tipo_requisito);
        if(tipo != null){
            return new ResponseEntity<>(gson.toJson(tipo),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
}
