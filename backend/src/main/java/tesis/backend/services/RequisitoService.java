package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.RequisitoRepository;
import tesis.backend.models.Requisito;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class RequisitoService {
    private final RequisitoRepository requisitoRepository;
    private final Gson gson;
    
    RequisitoService(RequisitoRepository requisitoRepository){
        this.gson = new GsonBuilder().create();
        this.requisitoRepository = requisitoRepository;
    }

    @GetMapping("/requisitos/")
    ResponseEntity<String> getListRequisito() {
        List<Requisito> requisito = requisitoRepository.getListRequisito();
        return new ResponseEntity<>(gson.toJson(requisito), HttpStatus.OK);
    }

    @GetMapping("/requisito/{id_requisito}")
    ResponseEntity<String> getRequisito(@PathVariable Long id_requisito){
        Requisito requisito = requisitoRepository.getRequisito(id_requisito);
        if(requisito != null){
            return new ResponseEntity<>(gson.toJson(requisito),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/requisito/create/{id_pregunta}")
    ResponseEntity<String> createRequisito(@RequestBody String request,@PathVariable Long id_pregunta){
        Requisito requisitoCreado = gson.fromJson(request, Requisito.class);
        if (requisitoCreado!= null){
            requisitoCreado = requisitoRepository.createRequisito(requisitoCreado,id_pregunta);
            return new ResponseEntity<>(gson.toJson(requisitoCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
