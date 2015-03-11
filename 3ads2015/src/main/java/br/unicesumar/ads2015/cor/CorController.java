package br.unicesumar.ads2015.cor;

import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cores")
@Transactional
public class CorController {
    
    @Autowired
    private EntityManager em;
    
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    @RequestMapping(value="/query/byIdDesc", method = RequestMethod.GET)
    public List<Map<String, Object>> getCoresByIdDesc() {
        return jdbcTemplate.query("select id, nome from cor order by id desc", new MapSqlParameterSource(), new MapRowMapper());
    }

    @RequestMapping(value="/query/byNomeContaining", method = RequestMethod.GET)
    public List<Map<String, Object>> getCoresByNomeContaining(@RequestParam String parteDoNome) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("xxxx", '%'+parteDoNome.toLowerCase()+'%');
        return jdbcTemplate.query("select id, nome from cor where lower(nome) like :xxxx order by id desc", params, new MapRowMapper());
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getCores() {
        return jdbcTemplate.query("select id, nome from cor order by id asc", new MapSqlParameterSource(), new MapRowMapper());
    }
    
//    @RequestMapping(method = RequestMethod.GET)
//    public List<Cor> getCores() {
//        return em.createQuery("select c from Cor c").getResultList();
//    }
    @RequestMapping(method = RequestMethod.PUT)
    public void alterarCor(@RequestParam Long id, @RequestBody String nome) {
        Cor corRecuperada = em.find(Cor.class, id);
        corRecuperada.setNome(nome);
        em.persist(corRecuperada);
    }
    @RequestMapping(method = RequestMethod.DELETE)
    public void excluirCor(@RequestParam Long id) {
        em.createQuery("delete from Cor c where c.id = :id").setParameter("id", id).executeUpdate();
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public void criarCor(@RequestParam Long id, @RequestParam String nome) {
        Cor nova = new Cor(id, nome);
        em.persist(nova);
    }
    
}
