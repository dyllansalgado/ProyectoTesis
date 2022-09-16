package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.RolRepository;
import tesis.backend.models.Rol;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class RolService {
    private final RolRepository rolRepository;
    private final Gson gson;

    RolService(RolRepository rolRepository){
        this.gson = new GsonBuilder().create();
        this.rolRepository = rolRepository;
    }

    @GetMapping("/roles/")
    ResponseEntity<String> getListRol(){
        List<Rol> rol = rolRepository.getListRol();
        return new ResponseEntity<>(gson.toJson(rol),HttpStatus.OK);
    }

    @GetMapping("/rol/{id_rol}")
    ResponseEntity<String> getRol(@PathVariable Long id_rol){
        Rol rol = rolRepository.getRol(id_rol);
        if(rol != null){
            return new ResponseEntity<>(gson.toJson(rol),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/rol/create")
    ResponseEntity<String> createRol(@RequestBody String request){
        Rol rolCreado = gson.fromJson(request, Rol.class);
        if (rolCreado != null){
            rolCreado = rolRepository.createRol(rolCreado);
            return new ResponseEntity<>(gson.toJson(rolCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = {"http://localhost:8080"})
    @ResponseBody
    @RequestMapping(value ="/rol/{id_rol}",  method =RequestMethod.PUT,consumes="application/json")
    ResponseEntity<String> updateRol(@RequestBody String request, @PathVariable Long id_rol){
     Rol ok = gson.fromJson(request,Rol.class);
     Rol rolCreado = rolRepository.getRol(id_rol);

        if(rolCreado != null){
            if(ok.getTipo_rol() != null){
                rolCreado.setTipo_rol(ok.getTipo_rol());
            }

            rolCreado = rolRepository.updateRol(rolCreado, id_rol);
            return new ResponseEntity<>(gson.toJson(rolCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/rol/{id_rol}")
    ResponseEntity<String> deleteRol(@PathVariable Long id_rol){
        if(rolRepository.deleteRol(id_rol)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
