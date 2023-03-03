package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Nadador;
import es.uji.ei1027.clubesportiu.model.Prova;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProvaDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {jdbcTemplate = new JdbcTemplate(dataSource);}
    public void addProva(Prova prova) {
        jdbcTemplate.update("INSERT INTO Prova VALUES (?, ?, ?, ?)",prova.getNom(),prova.getDescripcio(),prova.getTipus(),prova.getData());
    }

    public void deleteProva(Prova prova) {
        jdbcTemplate.update("DELETE FROM Prova WHERE nom =?",prova.getNom());
    }


    public void updateProva(Prova prova) {
        jdbcTemplate.update("UPDATE Prova SET descripcio=?,  tipus=?, data =?, WHERE nom =?",
                prova.getDescripcio(),prova.getTipus(),prova.getData());
    }

    public Prova getProva(String nomProva) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Prova WHERE nom =?",
                    new ProvaRowMapper(),
                    nomProva);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Prova> getProvas() {
        try {
            return jdbcTemplate.query("SELECT * from Prova", new ProvaRowMapper());

        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Prova>();
        }
    }

}
