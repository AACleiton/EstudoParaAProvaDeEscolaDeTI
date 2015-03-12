package br.unicesumar.ads2015.livro;

import br.unicesumar.ads2015.cor.MapRowMapper;
import ch.qos.logback.classic.util.ContextInitializer;
import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/livros")
@Transactional
public class LivroController {
    
    @Autowired
    private EntityManager persistence;
    
    @Autowired
    private NamedParameterJdbcTemplate temp;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getLivros(){
        return temp.query("select id, titulo, peso, ano from livro order by id", new MapSqlParameterSource(), new MapRowMapper());
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public void setLivro(@RequestParam String id, @RequestParam String titulo, @RequestParam String ano, @RequestParam String peso){
        Double pesoAux = null;
        Integer anoAux = null;
        Long idAux = null;
        
        try {
            idAux = Long.parseLong(id);
            anoAux = Integer.parseInt(ano);
            pesoAux = Double.parseDouble(peso);
        } catch (Exception e) {
            System.out.println("Id, Ano ou peso estao com valores errados!!");
            e.printStackTrace();
        }
        
        
        Livro l = new Livro(idAux,titulo,anoAux,pesoAux);
        persistence.persist(l);
    }
    @RequestMapping(value="/salvar", method = RequestMethod.POST)
    public void setLivro(@RequestBody Livro l){
        Double pesoAux = null;
        Integer anoAux = null;
        Long idAux = null;
        
        try {
            idAux = l.getId();
            anoAux = l.getAno();
            pesoAux = l.getPeso();
        } catch (Exception e) {
            System.out.println("Id, Ano ou peso estao com valores errados!!");
            e.printStackTrace();
        }
        
        
        Livro livroNovo = new Livro(idAux,l.getTitulo(),anoAux,pesoAux);
        persistence.persist(livroNovo);
    }
    
    @RequestMapping(value="/{id}", method= RequestMethod.PUT)
    public void alteraLivro(@PathVariable Long id, @RequestBody Livro l){
        this.removerLivro(id);
        persistence.persist(l);
    }
    
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void removerLivro(@PathVariable Long id){
        persistence.createQuery("delete from Livro c where c.id = :id").setParameter("id", id).executeUpdate();
    }
}
