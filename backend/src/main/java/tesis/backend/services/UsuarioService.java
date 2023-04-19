package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.UsuarioRepository;
import tesis.backend.models.Usuario;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final Gson gson;

    UsuarioService(UsuarioRepository usuarioRepository){
        this.gson = new GsonBuilder().create();
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/usuarios/")
    ResponseEntity<String> getListUsuario() {
        List<Usuario> usuario = usuarioRepository.getListUsuario();
        return new ResponseEntity<>(gson.toJson(usuario), HttpStatus.OK);
    }

    @GetMapping("/usuario/{id_usuario}")
    ResponseEntity<String> getUsuario(@PathVariable Long id_usuario){
        Usuario usuario = usuarioRepository.getUsuario(id_usuario);
        if(usuario != null){
            return new ResponseEntity<>(gson.toJson(usuario),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/usuario/create")
    ResponseEntity<String> createUsuario(@RequestBody String request){
        Usuario usuarioCreado = gson.fromJson(request, Usuario.class);
        if (usuarioCreado != null){
            usuarioCreado = usuarioRepository.createUsuario(usuarioCreado);
            if(usuarioCreado == null){
                return new ResponseEntity<>(gson.toJson(false),HttpStatus.OK);
            }
            return new ResponseEntity<>(gson.toJson(usuarioCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(gson.toJson(false),HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080"})
    @ResponseBody
    @RequestMapping(value ="/usuario/{id_usuario}",  method =RequestMethod.PUT,consumes="application/json")
    ResponseEntity<String> updateUsuario(@RequestBody String request, @PathVariable Long id_usuario){
     Usuario ok = gson.fromJson(request,Usuario.class);
     Usuario usuarioCreado = usuarioRepository.getUsuario(id_usuario);

        if(usuarioCreado != null){
            if(ok.getNombre_usuario() != null){
                usuarioCreado.setNombre_usuario(ok.getNombre_usuario());
            }
            if(ok.getApellido_usuario() != null){
                usuarioCreado.setApellido_usuario(ok.getApellido_usuario());
            }
            if(ok.getId_rol() != null){
                usuarioCreado.setId_rol(ok.getId_rol());
            }
            if(ok.getContrasena_usuario() != null){
                usuarioCreado.setContrasena_usuario(ok.getContrasena_usuario());
            }

            usuarioCreado = usuarioRepository.updateUsuario(usuarioCreado, id_usuario);
            return new ResponseEntity<>(gson.toJson(usuarioCreado),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/usuario/{id_usuario}")
    ResponseEntity<String> deleteUsuario(@PathVariable Long id_usuario) {
        if (usuarioRepository.deleteUsuario(id_usuario)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login/")
    @ResponseBody
    public Usuario loginUsuario(@RequestBody Usuario usuario){
        Usuario resultado =  usuarioRepository.loginUsuario(usuario);
        if (resultado == null){
            return null;
        }else{
            return resultado;
        }
    }

}
