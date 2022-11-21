package it.prova.myebay.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "utente")
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "nome")
	private String nome;
	@Column(name = "cognome")
	private String cognome;
	@Column(name = "dateCreated")
	private Date dateCreated;
	
	//Variazione
	@Column(name = "creditoResiduo")
	private Integer creditoResiduo; 

	// se non uso questa annotation viene gestito come un intero
	@Enumerated(EnumType.STRING)
	private StatoUtente stato;

	@ManyToMany
	@JoinTable(name = "utente_ruolo", joinColumns = @JoinColumn(name = "utente_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ruolo_id", referencedColumnName = "ID"))
	private Set<Ruolo> ruoli = new HashSet<>(0);
	
	//Variazione
	//Molteplicità minima 0
	@OneToMany(mappedBy = "utenteInserimento")
	private Set<Annuncio> annunci = new HashSet<>(0);
	
	//Variazione
	//Molteplicità minima 0
	@OneToMany(mappedBy = "utenteAcquirente")
	private Set<Acquisto> acquisti = new HashSet<>(0);

	public Utente() {
	}
	
	public Utente(Long id) {
		super();
		this.id = id;
	}

	public Utente(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Utente(String username, String password, String nome, String cognome, Date dateCreated) {
		this.username = username;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.dateCreated = dateCreated;
	}

	public Utente(Long id, String username, String password, String nome, String cognome, Date dateCreated,
			StatoUtente stato) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.dateCreated = dateCreated;
		this.stato = stato;
	}

	public Utente(String username, String password, String nome, String cognome, Date dateCreated, int creditoResiduo) {
		// TODO Auto-generated constructor stub
		this.username= username;
		this.password=password;
		this.nome=nome;
		this.cognome=cognome;
		this.dateCreated=dateCreated;
		this.creditoResiduo=creditoResiduo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Ruolo> getRuoli() {
		return ruoli;
	}

	public void setRuoli(Set<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public StatoUtente getStato() {
		return stato;
	}

	public void setStato(StatoUtente stato) {
		this.stato = stato;
	}
	
	public Integer getCreditoResiduo() {
		return creditoResiduo;
	}

	public void setCreditoResiduo(Integer creditoResiduo) {
		this.creditoResiduo = creditoResiduo;
	}
	
	public Set<Annuncio> getAnnunci() {
		return annunci;
	}

	public void setAnnunci(Set<Annuncio> annunci) {
		this.annunci = annunci;
	}

	public Set<Acquisto> getAcquist() {
		return acquisti;
	}

	public void setAcquist(Set<Acquisto> acquisti) {
		this.acquisti = acquisti;
	}

	public boolean isAdmin() {
		for (Ruolo ruoloItem : ruoli) {
			if (ruoloItem.getCodice().equals(Ruolo.ROLE_ADMIN))
				return true;
		}
		return false;
	}

	public boolean isAttivo() {
		return this.stato != null && this.stato.equals(StatoUtente.ATTIVO);
	}

	public boolean isDisabilitato() {
		return this.stato != null && this.stato.equals(StatoUtente.DISABILITATO);
	}

}
