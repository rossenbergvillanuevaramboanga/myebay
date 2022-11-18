package it.prova.myebay.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "acquisto")
public class Acquisto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "descrizione")
	private String descrizione;
	@Column(name = "dateCreated")
	private Date dateCreated;
	@Column(name = "prezzo")
	private Integer prezzo;
	
	@ManyToOne
	@JoinColumn(name="utente_id", nullable=false)
	private Utente utenteAcquisto;
	
	public Acquisto() {
		// TODO Auto-generated constructor stub
	}

	public Acquisto(Long id, String descrizione, Date dateCreated, Integer prezzo, Utente utenteAcquisto) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.dateCreated = dateCreated;
		this.prezzo = prezzo;
		this.utenteAcquisto = utenteAcquisto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Integer getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Integer prezzo) {
		this.prezzo = prezzo;
	}

	public Utente getUtenteAcquisto() {
		return utenteAcquisto;
	}

	public void setUtenteAcquisto(Utente utenteAcquisto) {
		this.utenteAcquisto = utenteAcquisto;
	}
	
	
	
	
	

}
