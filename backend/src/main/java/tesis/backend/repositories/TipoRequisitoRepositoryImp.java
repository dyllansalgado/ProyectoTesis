package tesis.backend.repositories;
import tesis.backend.models.TipoRequisito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;


@Repository
public class TipoRequisitoRepositoryImp implements TipoRequisitoRepository  {
    
    @Autowired
    private Sql2o sql2o;

    @Override
    public Long countTipo_Requisito(){
        String query = "select count(*) from tipo_requisito";
        Connection conn = sql2o.open();
        Long resultado = (Long) conn.createQuery(query,true).executeAndFetchFirst(Long.class);
        return resultado + 1; 
    }

    @Override
    public TipoRequisito getTipo_Requisito(Long id_tipo_requisito){
        String query = "select * from tipo_requisito where id_tipo_requisito = :id_tipo_requisito and deleted = false";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).addParameter("id_tipo_requisito",id_tipo_requisito).executeAndFetchFirst(TipoRequisito.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public  List<TipoRequisito> getListTipo_Requisitos() {
        String query = "select * from tipo_requisito";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(TipoRequisito.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
