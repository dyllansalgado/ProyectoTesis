package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.GlosarioRepository;
import tesis.backend.models.Glosario;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class GlosarioService {
    private final GlosarioRepository glosarioRepository;
    private final Gson gson;
    
    GlosarioService(GlosarioRepository glosarioRepository){
        this.gson = new GsonBuilder().create();
        this.glosarioRepository = glosarioRepository;
    }

    @GetMapping("/glosarios/")
    ResponseEntity<String> getListGlosario() {
        List<Glosario> glosario = glosarioRepository.getListGlosario();
        return new ResponseEntity<>(gson.toJson(glosario), HttpStatus.OK);
    }

    @GetMapping("/glosario/{id_glosario}")
    ResponseEntity<String> getGlosario(@PathVariable Long id_glosario){
        Glosario glosario = glosarioRepository.getGlosario(id_glosario);
        if(glosario != null){
            return new ResponseEntity<>(gson.toJson(glosario),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/glosario/create")
    ResponseEntity<String> createGlosario(@RequestBody String request){
        Glosario glosarioCreado = gson.fromJson(request, Glosario.class);
        if (glosarioCreado!= null){
            glosarioCreado = glosarioRepository.createGlosario(glosarioCreado);
            return new ResponseEntity<>(gson.toJson(glosarioCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/glosarioReunion/{id_reunion}")
    ResponseEntity<String>getListTemaXidReunion(@PathVariable Long id_reunion) {
        List<Glosario> glosario = glosarioRepository.getListGlosarioXidReunion(id_reunion);
        return new ResponseEntity<>(gson.toJson(glosario), HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080"})
    @ResponseBody
    @RequestMapping(value ="/glosario/{id_glosario}",  method =RequestMethod.PUT,consumes="application/json")
    ResponseEntity<String> updateGlosario(@RequestBody String request, @PathVariable Long id_glosario){
     Glosario ok = gson.fromJson(request,Glosario.class);
     Glosario glosarioCreado = glosarioRepository.getGlosario(id_glosario);

        if(glosarioCreado != null){
            if(ok.getNombre_glosario() != null){
                glosarioCreado.setNombre_glosario(ok.getNombre_glosario());
            }
            if(ok.getDescripcion_glosario() != null){
                glosarioCreado.setDescripcion_glosario(ok.getDescripcion_glosario());
            }
            glosarioCreado = glosarioRepository.updateGlosario(glosarioCreado, id_glosario);
            return new ResponseEntity<>(gson.toJson(glosarioCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/glosario/{id_glosario}")
    ResponseEntity<String> deleteGlosario(@PathVariable Long id_glosario) {
        if (glosarioRepository.deleteGlosario(id_glosario)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
