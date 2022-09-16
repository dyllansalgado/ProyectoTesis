package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.TemaRepository;
import tesis.backend.models.Tema;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class TemaService {
    private final TemaRepository temaRepository;
    private final Gson gson;
    
    TemaService(TemaRepository temaRepository){
        this.gson = new GsonBuilder().create();
        this.temaRepository = temaRepository;
    }

    @GetMapping("/temas/")
    ResponseEntity<String> getListTema() {
        List<Tema> tema = temaRepository.getListTema();
        return new ResponseEntity<>(gson.toJson(tema), HttpStatus.OK);
    }

    @GetMapping("/tema/{id_tema}")
    ResponseEntity<String> getTema(@PathVariable Long id_tema){
        Tema tema = temaRepository.getTema(id_tema);
        if(tema != null){
            return new ResponseEntity<>(gson.toJson(tema),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/tema/create")
    ResponseEntity<String> createTema(@RequestBody String request){
        Tema temaCreado = gson.fromJson(request, Tema.class);
        if (temaCreado!= null){
            temaCreado = temaRepository.createTema(temaCreado);
            return new ResponseEntity<>(gson.toJson(temaCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = {"http://localhost:8080"})
    @ResponseBody
    @RequestMapping(value ="/tema/{id_tema}",  method =RequestMethod.PUT,consumes="application/json")
    ResponseEntity<String> updateTema(@RequestBody String request, @PathVariable Long id_tema){
     Tema ok = gson.fromJson(request,Tema.class);
     Tema temaCreado = temaRepository.getTema(id_tema);

        if(temaCreado != null){
            if(ok.getNombre_tema() != null){
                temaCreado.setNombre_tema(ok.getNombre_tema());
            }
            if(ok.getDescripcion_tema() != null){
                temaCreado.setDescripcion_tema(ok.getDescripcion_tema());
            }
            if(ok.getEstado() != null){
                temaCreado.setEstado(ok.getEstado());
            }
            temaCreado = temaRepository.updateTema(temaCreado, id_tema);
            return new ResponseEntity<>(gson.toJson(temaCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/tema/{id_tema}")
    ResponseEntity<String> deleteTema(@PathVariable Long id_tema) {
        if (temaRepository.deleteTema(id_tema)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
