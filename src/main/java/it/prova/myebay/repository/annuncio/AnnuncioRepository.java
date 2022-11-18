package it.prova.myebay.repository.annuncio;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.myebay.model.Annuncio;

public interface AnnuncioRepository extends CrudRepository<Annuncio, Long> {
	
	@Query("from Annuncio a left join fetch a.utente left join fetch a.categorie where a.id = ?1 ")
	Optional<Annuncio> findByIdEager(Long id);

}
