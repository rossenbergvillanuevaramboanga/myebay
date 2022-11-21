package it.prova.myebay.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.myebay.dto.UtenteDTO;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Utente;
import it.prova.myebay.dto.AcquistoDTO;
import it.prova.myebay.service.AcquistoService;
import it.prova.myebay.service.AnnuncioService;
import it.prova.myebay.service.CategoriaService;
import it.prova.myebay.service.RuoloService;
import it.prova.myebay.service.UtenteService;

@Controller
@RequestMapping(value = "/acquisto")
public class AcquistoController {
	
	// Implementare 
	@Autowired
	private AnnuncioService annuncioService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private RuoloService ruoloService;
	@Autowired
	private UtenteService utenteService;
	@Autowired
	private AcquistoService acquistoService;
	
	
	@GetMapping("/acquisto/list")
	public String insertAnnuncio(HttpServletRequest request,
			Model model) {
		UtenteDTO utenteInSessione = (UtenteDTO) request.getSession().getAttribute("userInfo");
		Long idUtenteSessione = utenteInSessione.getId();
		
			model.addAttribute("acquisto_list_attribute",
					AcquistoDTO.createAcquistoDTOListFromModelList(acquistoService.findAllById(idUtenteSessione), true));
			
		return "acquisto/list";
	}
	
	@PostMapping("/buy")
	public String confermaAcquisto(@RequestParam(name = "idAnnuncio") Long idAnnuncio
			,@RequestParam(name = "utenteId") Long utenteId
			,Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {
		Utente utente = utenteService.caricaSingoloUtente(utenteId);
		Annuncio annuncio = annuncioService.caricaSingoloAnnuncio(idAnnuncio);
		AcquistoDTO acquistoDTO = new AcquistoDTO(annuncio.getTestoAnnuncio(), annuncio.getDateCreated(), annuncio.getPrezzo());
		
		if (utente.getCreditoResiduo() < annuncio.getPrezzo()) {
			
			redirectAttrs.addFlashAttribute("errorMessage", "Credito esaurito");
			return "redirect:/home";
		}
		Integer creditoAggiornato = utente.getCreditoResiduo() - annuncio.getPrezzo();
		utente.setCreditoResiduo(creditoAggiornato);
		utenteService.aggiorna(utente);
		
		acquistoDTO.setData(new Date());
		acquistoDTO.setUtenteAcquirente(UtenteDTO.buildUtenteDTOFromModel
				(utenteService.caricaSingoloUtente(utenteId), true));
		acquistoService.inserisciNuovo(acquistoDTO.buildAcquistoModel());
		annuncio.setAperto(false);
		annuncioService.aggiorna(annuncio);
		
		Utente utenteFromDb = utenteService.caricaSingoloUtente(utenteId);
		UtenteDTO utenteParziale = new UtenteDTO();
		utenteParziale.setNome(utenteFromDb.getNome());
		utenteParziale.setCognome(utenteFromDb.getCognome());
		utenteParziale.setId(utenteFromDb.getId());
		utenteParziale.setCreditoResiduo(utenteFromDb.getCreditoResiduo());
		request.getSession().setAttribute("userInfo", utenteParziale);
		
		return "acquisto/list";
	}
	
	@PostMapping("/loginAcquisto")
	public String loginAcquisto(HttpServletRequest request, HttpServletResponse response) {
		
		RequestCache requestCache = new HttpSessionRequestCache();
	    requestCache.saveRequest(request,response);
	    return "redirect:/login";
	}

}
