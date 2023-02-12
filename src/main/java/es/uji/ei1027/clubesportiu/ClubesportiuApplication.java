package es.uji.ei1027.clubesportiu;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class ClubesportiuApplication implements CommandLineRunner {

	private static final Logger log = Logger.getLogger(ClubesportiuApplication.class.getName());

	public static void main(String[] args) {
		// Auto-configura l'aplicació
		new SpringApplicationBuilder(ClubesportiuApplication.class).run(args);
	}

	// Funció principal
	public void run(String... strings) throws Exception {


		//Insertar los datos de Ariadna
		log.info("Inserta una nova nadadora");
		jdbcTemplate.update(
				"INSERT INTO Nadador VALUES(?, ?, ?, ?, ?)",
				"Ariadna Edo", "XX1242", "Espanya", null, "Femení");
		log.info("I comprova que s'haja inserit correctament");
		mostraNadador("Ariadna Edo");

		//Ejercicio 2

		mostraNadador("Gemma Mengual");

		//Ejercicio 3

		//Actualización de los datos.
		log.info("Actualitza l'edat de la nadadora Ariadna Edo a 21 anys");
		jdbcTemplate.update("UPDATE Nadador SET edat = 21 WHERE nom = 'Ariadna Edo'");
		log.info("I comprova que s'haja modificat correctament");
		mostraNadador("Ariadna Edo");
		//Borrado de datos.
		log.info("Esborra la nadadora Ariadna Edo");
		jdbcTemplate.update("DELETE FROM Nadador WHERE nom = 'Ariadna Edo'");
		log.info("I comprova que s'haja esborrat correctament");
		mostraNadador("Ariadna Edo");

		//Ejercicio 4
		provaNadadorDao();

	}

	private void mostraNadador(String nomNadadora) {
		try {
			log.info("Vamos a ver si existe la nadadora con nombre: "+ nomNadadora);
			Nadador n1= jdbcTemplate.queryForObject(
					"SELECT * FROM Nadador WHERE nom =?",
					new NadadorRowMapper(),
					nomNadadora);
			log.info(n1.toString());
		}
		catch(EmptyResultDataAccessException e) {
			log.info("La nadadora no existe en la base de datos");
		}
	}

	// Demana a Spring que ens proporcione una instància de NadadorDao
	// mitjanjant injecció de dependencies
	@Autowired
	NadadorDao nadadorDao;
	public void provaNadadorDao() {
		log.info("Provant NadadorDao");
		log.info("Tots els nadadors");

		for (Nadador n: nadadorDao.getNadadors()) {
			log.info(n.toString());
		}

		log.info("Dades de Gemma Mengual");
		Nadador n = nadadorDao.getNadador("Gemma Mengual");
		log.info(n.toString());

		Nadador aEdo = new Nadador();
		aEdo.setNom("Ariadna Edo");
		aEdo.setEdat(21);
		log.info("Nou: Ariadna Edo");
		nadadorDao.addNadador(aEdo);
		log.info(nadadorDao.getNadador("Ariadna Edo").toString());

		log.info("Actualitzat: Ariadna Edo");
		aEdo.setPais("Espanya");
		aEdo.setGenere("Femení");
		nadadorDao.updateNadador(aEdo);
		log.info(nadadorDao.getNadador("Ariadna Edo").toString());

		log.info("Esborrat: Ariadna Edo");
		nadadorDao.deleteNadador(aEdo);
		if (nadadorDao.getNadador("Ariadna Edo") == null) {
			log.info("Esborrada correctament");
		}
	}

	// Plantilla per a executar operacions sobre la connexió
	private JdbcTemplate jdbcTemplate;

	// Crea el jdbcTemplate a partir del DataSource que hem configurat
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
