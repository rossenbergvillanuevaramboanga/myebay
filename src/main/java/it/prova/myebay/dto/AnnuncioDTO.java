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
	
	private UtenteDTO utenteInserimento;
	
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
	
	public AnnuncioDTO(Long id2, String testoAnnuncio2, Integer prezzo2, Date dateCreated2, Boolean aperto2) {
		// TODO Auto-generated constructor stub
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


	public UtenteDTO getUtenteInserimento() {
		return utenteInserimento;
	}

	public void setUtenteInserimento(UtenteDTO utenteInserimento) {
		this.utenteInserimento = utenteInserimento;
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
		
		Annuncio result = new Annuncio(this.id, this.testoAnnuncio, this.prezzo, this.dateCreated, this.aperto, this.utenteInserimento.buildUtenteModel(true));
		
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

	public static AnnuncioDTO buildAnnuncioDTOFromModel(Annuncio annuncioModel, boolean includeUtente,
			boolean includeCategorie) {
		// TODO Auto-generated method stub
		
		AnnuncioDTO result = new AnnuncioDTO(annuncioModel.getId(), annuncioModel.getTestoAnnuncio(), annuncioModel.getPrezzo(),
				annuncioModel.getDateCreated(), annuncioModel.getAperto());
		
		if (includeUtente)
			result.setUtenteInserimento(UtenteDTO.buildUtenteDTOFromModel(annuncioModel.getUtenteInserimento(), true));
		
		if (annuncioModel.getCategorie() != null && includeCategorie && !annuncioModel.getCategorie().isEmpty())
			result.categorieIds = annuncioModel.getCategorie().stream().map(r -> r.getId()).collect(Collectors.toList())
					.toArray(new Long[] {});	

		return result;
	}

	

}
