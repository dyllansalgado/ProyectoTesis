package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.ReunionRepository;
import tesis.backend.models.Reunion;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class ReunionService {
    private final ReunionRepository reunionRepository;
    private final Gson gson;
    
    ReunionService(ReunionRepository reunionRepository){
        this.gson = new GsonBuilder().create();
        this.reunionRepository = reunionRepository;
    }

    @GetMapping("/reuniones/")
    ResponseEntity<String> getListReunion() {
        List<Reunion> reunion = reunionRepository.getListReunion();
        return new ResponseEntity<>(gson.toJson(reunion), HttpStatus.OK);
    }

    @GetMapping("/reunion/{id_reunion}")
    ResponseEntity<String> getReunion(@PathVariable Long id_reunion){
        Reunion reunion = reunionRepository.getReunion(id_reunion);
        if(reunion != null){
            return new ResponseEntity<>(gson.toJson(reunion),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/reunionProyecto/{id_proyecto}")
    ResponseEntity<String>getListReunionXidProyecto(@PathVariable Long id_proyecto) {
        List<Reunion> reunion = reunionRepository.getListReunionXidProyecto(id_proyecto);
        return new ResponseEntity<>(gson.toJson(reunion), HttpStatus.OK);
    }

    @PostMapping("/reunion/create")
    ResponseEntity<String> createReunion(@RequestBody String request){
        Reunion reunionCreado = gson.fromJson(request, Reunion.class);
        if (reunionCreado!= null){
            reunionCreado = reunionRepository.createReunion(reunionCreado);
            return new ResponseEntity<>(gson.toJson(reunionCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = {"http://localhost:8080"})
    @ResponseBody
    @RequestMapping(value ="/reunion/{id_reunion}",  method =RequestMethod.PUT,consumes="application/json")
    ResponseEntity<String> updateReunion(@RequestBody String request, @PathVariable Long id_reunion){
     Reunion ok = gson.fromJson(request,Reunion.class);
     Reunion reunionCreado = reunionRepository.getReunion(id_reunion);

        if(reunionCreado != null){
            if(ok.getFecha_reunion() != null){
                reunionCreado.setFecha_reunion(ok.getFecha_reunion());
            }
            if(ok.getLugar_reunion() != null){
                reunionCreado.setLugar_reunion(ok.getLugar_reunion());
            }
            if(ok.getEstado() != null){
                reunionCreado.setEstado(ok.getEstado());
            }
            reunionCreado = reunionRepository.updateReunion(reunionCreado, id_reunion);
            return new ResponseEntity<>(gson.toJson(reunionCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/reunion/{id_reunion}")
    ResponseEntity<String> deleteReunion(@PathVariable Long id_reunion) {
        if (reunionRepository.deleteReunion(id_reunion)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
