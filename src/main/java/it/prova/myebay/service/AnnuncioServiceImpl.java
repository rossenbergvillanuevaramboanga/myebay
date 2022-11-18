package it.prova.myebay.service;

import java.util.Date;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.repository.annuncio.AnnuncioRepository;

@Service
public class AnnuncioServiceImpl implements AnnuncioService {
	
	@Autowired
	private AnnuncioRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<Annuncio> listAllAnnunci() {
		// TODO Auto-generated method stub
		return (List<Annuncio>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Annuncio caricaSingoloAnnuncio(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Override
	public Annuncio caricaSingoloAnnuncioEager(Long id) {
		// TODO Auto-generated method stub
		return repository.findByIdEager(id).orElse(null);
	}

	@Override
	public void aggiorna(Annuncio annuncioInstance) {
		// TODO Auto-generated method stub
		Annuncio annuncioReloaded = repository.findById(annuncioInstance.getId()).orElse(null);
		if(annuncioReloaded == null)
			throw new RuntimeException("Elemento non trovato");
		annuncioReloaded.setAperto(annuncioInstance.getAperto());
		annuncioReloaded.setCategorie(annuncioInstance.getCategorie());
		annuncioReloaded.setPrezzo(annuncioInstance.getPrezzo());
		annuncioReloaded.setTestoAnnuncio(annuncioInstance.getTestoAnnuncio());
		repository.save(annuncioReloaded);
	}

	@Override
	@Transactional
	public void inserisciNuovo(Annuncio annuncioInstance) {
		// TODO Auto-generated method stub
		annuncioInstance.setAperto(true);
		annuncioInstance.setDateCreated(new Date());;
		repository.save(annuncioInstance);
		
	}

	@Override
	public void rimuovi(Long idToDelete) {
		// TODO Auto-generated method stub
		repository.deleteById(idToDelete);
		
	}

	@Override
	public List<Annuncio> findByExample(Annuncio annuncio) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
