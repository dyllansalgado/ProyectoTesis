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

    @PostMapping("/requisito/create/{id_pregunta}/{id_usuario}")
    ResponseEntity<String> createRequisito(@RequestBody String request,@PathVariable Long id_pregunta,@PathVariable Long id_usuario){
        Requisito requisitoCreado = gson.fromJson(request, Requisito.class);
        if (requisitoCreado!= null){
            requisitoCreado = requisitoRepository.createRequisito(requisitoCreado,id_pregunta,id_usuario);
            return new ResponseEntity<>(gson.toJson(requisitoCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = {"http://localhost:8080"})
    @ResponseBody
    @RequestMapping(value ="/requisito/{id_requisito}",  method =RequestMethod.PUT,consumes="application/json")
    ResponseEntity<String> updateGlosario(@RequestBody String request, @PathVariable Long id_requisito){
     Requisito ok = gson.fromJson(request,Requisito.class);
     Requisito requisitoCreado = requisitoRepository.getRequisito(id_requisito);

        if(requisitoCreado != null){
            if(ok.getNombre_requisito() != null){
                requisitoCreado.setNombre_requisito(ok.getNombre_requisito());
            }
            if(ok.getDescripcion_requisito() != null){
                requisitoCreado.setDescripcion_requisito(ok.getDescripcion_requisito());
            }
            if(ok.getEstado_requisito() != null){
                requisitoCreado.setEstado_requisito(ok.getEstado_requisito());
            }
            if(ok.getPrioridad() != null){
                requisitoCreado.setPrioridad(ok.getPrioridad());
            }
            requisitoCreado = requisitoRepository.updateRequisito(requisitoCreado, id_requisito);
            return new ResponseEntity<>(gson.toJson(requisitoCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/requisito/{id_requisito}")
    ResponseEntity<String> deleteRequisito(@PathVariable Long id_requisito) {
        if (requisitoRepository.deleteRequisito(id_requisito)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
