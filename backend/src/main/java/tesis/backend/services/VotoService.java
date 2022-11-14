package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.VotoRepository;
import tesis.backend.models.Voto;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class VotoService {
    private final VotoRepository votoRepository;
    private final Gson gson;

    VotoService(VotoRepository votoRepository){
        this.gson = new GsonBuilder().create();
        this.votoRepository = votoRepository;
    }

    @GetMapping("/votos/")
    ResponseEntity<String> getListVoto(){
        List<Voto> voto = votoRepository.getListVoto();
        return new ResponseEntity<>(gson.toJson(voto),HttpStatus.OK);
    }

    @GetMapping("/voto/{id_voto}")
    ResponseEntity<String> getVoto(@PathVariable Long id_voto){
        Voto voto = votoRepository.getVoto(id_voto);
        if(voto != null){
            return new ResponseEntity<>(gson.toJson(voto),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/votos/{id_pregunta}")
    Long countVoto2(@PathVariable Long id_pregunta){
        Long votos = votoRepository.countVoto2(id_pregunta);
        return votos;
    }

    @PostMapping("/voto/create")
    ResponseEntity<String> createVoto(@RequestBody String request){
        Voto votoCreado = gson.fromJson(request, Voto.class);
        if (votoCreado != null){
            votoCreado = votoRepository.createVoto(votoCreado);
            if(votoCreado== null){
                return new ResponseEntity<>(gson.toJson(false),HttpStatus.OK);
            }
            return new ResponseEntity<>(gson.toJson(votoCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(gson.toJson(false),HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080"})
    @ResponseBody
    @RequestMapping(value ="/voto/{id_voto}",  method =RequestMethod.PUT,consumes="application/json")
    ResponseEntity<String> updateVoto(@RequestBody String request, @PathVariable Long id_voto){
     Voto ok = gson.fromJson(request,Voto.class);
     Voto votoCreado = votoRepository.getVoto(id_voto);

        if(votoCreado != null){
            if(ok.getTipo_voto() != null){
                votoCreado.setTipo_voto(ok.getTipo_voto());
            }
            votoCreado = votoRepository.updateVoto(votoCreado, id_voto);
            return new ResponseEntity<>(gson.toJson(votoCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/voto/{id_voto}")
    ResponseEntity<String> deleteVoto(@PathVariable Long id_voto){
        if(votoRepository.deleteVoto(id_voto)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
