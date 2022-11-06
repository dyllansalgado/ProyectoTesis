package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.TerminoRepository;
import tesis.backend.models.Termino;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class TerminoService {
    private final TerminoRepository terminoRepository;
    private final Gson gson;
    
    TerminoService(TerminoRepository terminoRepository){
        this.gson = new GsonBuilder().create();
        this.terminoRepository = terminoRepository;
    }

    @GetMapping("/terminos/")
    ResponseEntity<String> getListTermino() {
        List<Termino> termino = terminoRepository.getListTermino();
        return new ResponseEntity<>(gson.toJson(termino), HttpStatus.OK);
    }

    @GetMapping("/termino/{id_termino}")
    ResponseEntity<String> getTermino(@PathVariable Long id_termino){
        Termino termino = terminoRepository.getTermino(id_termino);
        if(termino != null){
            return new ResponseEntity<>(gson.toJson(termino),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/termino/create")
    ResponseEntity<String> createTermino(@RequestBody String request){
        Termino terminoCreado = gson.fromJson(request, Termino.class);
        if (terminoCreado!= null){
            terminoCreado = terminoRepository.createTermino(terminoCreado);
            return new ResponseEntity<>(gson.toJson(terminoCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = {"http://localhost:8080"})
    @ResponseBody
    @RequestMapping(value ="/termino/{id_termino}",  method =RequestMethod.PUT,consumes="application/json")
    ResponseEntity<String> updateTermino(@RequestBody String request, @PathVariable Long id_termino){
     Termino ok = gson.fromJson(request,Termino.class);
     Termino terminoCreado = terminoRepository.getTermino(id_termino);

        if(terminoCreado != null){
            if(ok.getNombre_termino() != null){
                terminoCreado.setNombre_termino(ok.getNombre_termino());
            }
            if(ok.getDescripcion_termino() != null){
                terminoCreado.setDescripcion_termino(ok.getDescripcion_termino());
            }
            terminoCreado = terminoRepository.updateTermino(terminoCreado, id_termino);
            return new ResponseEntity<>(gson.toJson(terminoCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/terminoGlosario/{id_glosario}")
    ResponseEntity<String>getListTerminoXidGlosario(@PathVariable Long id_glosario) {
        List<Termino> termino = terminoRepository.getListTerminoXidGlosario(id_glosario);
        return new ResponseEntity<>(gson.toJson(termino), HttpStatus.OK);
    }

    @DeleteMapping("/termino/{id_termino}")
    ResponseEntity<String> deleteTermino(@PathVariable Long id_termino) {
        if (terminoRepository.deleteTermino(id_termino)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
