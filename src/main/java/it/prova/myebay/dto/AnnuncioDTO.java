package it.prova.myebay.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;


import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;
import it.prova.myebay.model.Utente;


public class AnnuncioDTO {
	
	private Long id;
	
	@NotBlank(message = "{testoAnnuncio.nonblank}")
	private String testoAnnuncio;
	@NotBlank(message = "{prezzo.nonblank}")
	private Integer prezzo;

	private Date dateCreated;

	private Boolean aperto;
	
	private Long utenteInserimentoId;
	
	private Long[] categorieIds;
	
	public AnnuncioDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public AnnuncioDTO(Long id, String testoAnnuncio, Integer prezzo) {
		super();
		this.id = id;
		this.testoAnnuncio = testoAnnuncio;
		this.prezzo = prezzo;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTestoAnnuncio() {
		return testoAnnuncio;
	}

	public void setTestoAnnuncio(String testoAnnuncio) {
		this.testoAnnuncio = testoAnnuncio;
	}

	public Integer getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Integer prezzo) {
		this.prezzo = prezzo;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Boolean getAperto() {
		return aperto;
	}

	public void setAperto(Boolean aperto) {
		this.aperto = aperto;
	}

	
	public Long getUtenteInserimentoId() {
		return utenteInserimentoId;
	}

	public void setUtenteInserimentoId(Long utenteInserimentoId) {
		this.utenteInserimentoId = utenteInserimentoId;
	}

	public Long[] getCategorieIds() {
		return categorieIds;
	}

	public void setCategorieIds(Long[] categorieIds) {
		this.categorieIds = categorieIds;
	}
	
	public boolean isAperto() {
		return this.aperto;
	}
	
	public Annuncio buildAnnuncioModel(boolean includeUtente, boolean includeCategorie) {
		
		Annuncio result = new Annuncio(this.id, this.testoAnnuncio, this.prezzo);
		if(includeUtente && utenteInserimentoId != null)
			result.setUtenteInserimento(new Utente(id));
		
		if(includeCategorie && categorieIds != null)
			result.setCategorie(Arrays.asList(categorieIds).stream().map(id -> new Categoria(id)).collect(Collectors.toSet()));
		
		return result;
		
	}

	public static List<AnnuncioDTO> createAnnuncioDTOListFromModelList(List<Annuncio> modelListInput, boolean includeUtente, boolean includeCategorie) {
		// TODO Auto-generated method stub
		return modelListInput.stream().map( annuncioEntity -> {
			return AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioEntity, includeUtente, includeCategorie);
		}).collect(Collectors.toList())
		;
	}

	private static AnnuncioDTO buildAnnuncioDTOFromModel(Annuncio annuncioModel, boolean includeUtente,
			boolean includeCategorie) {
		// TODO Auto-generated method stub
		
		AnnuncioDTO result = new AnnuncioDTO(
				annuncioModel.getId(), 
				annuncioModel.getTestoAnnuncio(), 
				annuncioModel.getPrezzo());
		
		if(includeUtente && annuncioModel.getUtenteInserimento()!=null)
			result.utenteInserimentoId = annuncioModel.getUtenteInserimento().getId();
		
		if(includeCategorie && !annuncioModel.getCategorie().isEmpty())
			result.categorieIds = annuncioModel.getCategorie().stream().map(
					c-> c.getId()).collect(Collectors.toList()).toArray(new Long[] {});
				
		return result;
	}

	

}
