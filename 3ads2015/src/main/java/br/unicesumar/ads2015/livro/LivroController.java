package br.unicesumar.ads2015.livro;

import br.unicesumar.ads2015.cor.MapRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
    public void setLivro(@RequestParam String titulo, @RequestParam Integer ano, @RequestParam Double peso){
        Livro l = new Livro(titulo,ano,peso);
        persistence.persist(l);
    }
    
    @RequestMapping(value="/{id}", method= RequestMethod.PUT)
    public void alteraLivro(@RequestParam Long id, @RequestBody Livro l){
        persistence.createQuery("delete from Livro l where l.id = :id").setParameter("id", id).executeUpdate();
        Livro livroAlterado = new Livro(id,l.getTitulo(),l.getAno(),l.getPeso());
        persistence.persist(livroAlterado);
    }
    
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void removerLivro(@RequestParam Long id){
        persistence.createQuery("delete from Livro c where c.id = :id").setParameter("id", id).executeUpdate();
    }
}
