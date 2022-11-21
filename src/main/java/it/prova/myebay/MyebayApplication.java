package it.prova.myebay;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.myebay.model.Categoria;
import it.prova.myebay.model.Ruolo;
import it.prova.myebay.model.Utente;
import it.prova.myebay.service.CategoriaService;
import it.prova.myebay.service.RuoloService;
import it.prova.myebay.service.UtenteService;

@SpringBootApplication
public class MyebayApplication implements CommandLineRunner{
	
	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;
	@Autowired
	private CategoriaService categoriaServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(MyebayApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", "ROLE_ADMIN"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", "ROLE_CLASSIC_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Classic User", "ROLE_CLASSIC_USER"));
		}
		
		if (categoriaServiceInstance.cercaPerDescrizioneECodice("Categoria Elettronica", "Elettronica") == null) {
			categoriaServiceInstance.inserisciNuovo(new Categoria("Categoria Elettronica", "Elettronica"));
		}
		if (categoriaServiceInstance.cercaPerDescrizioneECodice("Categoria Casa", "Casa") == null) {
			categoriaServiceInstance.inserisciNuovo(new Categoria("Categoria Casa", "Casa"));
		}
		if (categoriaServiceInstance.cercaPerDescrizioneECodice("Categoria Giochi", "Giochi") == null) {
			categoriaServiceInstance.inserisciNuovo(new Categoria("Categoria Giochi", "Giochi"));
		}
		
		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = new Utente("admin", "admin", "Mario", "Rossi", new Date(), 5000);
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN"));
			utenteServiceInstance.inserisciNuovo(admin);
			//l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(admin.getId());
		}

		if (utenteServiceInstance.findByUsername("user") == null) {
			Utente classicUser = new Utente("user", "user", "Antonio", "Verdi", new Date(), 2000);
			classicUser.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", "ROLE_CLASSIC_USER"));
			utenteServiceInstance.inserisciNuovo(classicUser);
			//l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(classicUser.getId());
		}
	}

}
