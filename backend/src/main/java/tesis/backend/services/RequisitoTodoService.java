package tesis.backend.services;
import org.springframework.web.bind.annotation.*;
import tesis.backend.repositories.RequisitoTodoRepository;
import tesis.backend.models.RequisitoTodo;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RestController
public class RequisitoTodoService {
    private final RequisitoTodoRepository requisitoTodoRepository;
    private final Gson gson;

    RequisitoTodoService(RequisitoTodoRepository requisitoTodoRepository){
        this.gson = new GsonBuilder().create();
        this.requisitoTodoRepository = requisitoTodoRepository;
    }
    @GetMapping("/RequisitosTodo/{id_tema}")
    ResponseEntity<String>getListListRequisitosTodo(@PathVariable Long id_tema) {
        List<RequisitoTodo> requisitoSeleccionado = requisitoTodoRepository.getListRequisitosTodo(id_tema);
        return new ResponseEntity<>(gson.toJson(requisitoSeleccionado), HttpStatus.OK);
    }

    @GetMapping("/RequisitosTodoAceptados/{id_tema}")
    ResponseEntity<String>getListRequisitosTodoAceptados(@PathVariable Long id_tema) {
        List<RequisitoTodo> requisitoSeleccionado = requisitoTodoRepository.getListRequisitosTodoAceptados(id_tema);
        return new ResponseEntity<>(gson.toJson(requisitoSeleccionado), HttpStatus.OK);
    }
}
