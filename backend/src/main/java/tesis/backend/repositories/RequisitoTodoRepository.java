package tesis.backend.repositories;
import tesis.backend.models.RequisitoTodo;
import java.util.List;

public interface RequisitoTodoRepository {
    public List<RequisitoTodo> getListRequisitosTodo(Long id_tema);
}