package it.prova.myebay.web.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.myebay.service.AnnuncioService;
import it.prova.myebay.service.CategoriaService;
import it.prova.myebay.service.RuoloService;
import it.prova.myebay.service.UtenteService;
import it.prova.myebay.dto.AnnuncioDTO;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.dto.RuoloDTO;

@Controller
@RequestMapping(value = "/annuncio")
public class AnnuncioController {
	
	//Injection of Services
	@Autowired
	private AnnuncioService annuncioService;
	@Autowired
	private UtenteService utenteService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private RuoloService ruoloService;
	
	@GetMapping
	public ModelAndView listAllAnnunci(){
		ModelAndView mv= new ModelAndView();
		mv.addObject("annunci_list_attribute",
				AnnuncioDTO.createAnnuncioDTOListFromModelList(annuncioService.listAllAnnunci(), true,true));
		mv.setViewName("annunci/list");
		return mv;
		
	}

	@GetMapping("/search")
	public String searchAnnuncio() {
		return "annuncio/search";
	}
	
	@PostMapping("/list")
	public String listAnnunci(Annuncio annuncioExample, ModelMap model) {
		model.addAttribute("annuncio_list_attribute",
				AnnuncioDTO.createAnnuncioDTOListFromModelList(annuncioService.findByExample(annuncioExample), false, false));
		return "annuncio/list";	
	}
	
	@PostMapping("/insert")
	public String create(Model model) {
		model.addAttribute("insert_annuncio_attr", new AnnuncioDTO());
		return "annuncio/insert";
	}
	
	@PostMapping("/save")
	public String saveAnnuncio(
			@Validated @ModelAttribute("insert_annuncio_attr") AnnuncioDTO annuncioDTO,
			@RequestParam(name = "utenteId") Long utenteId,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {


		if (result.hasErrors()) {
			model.addAttribute("ruoli_totali_attr", RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()));
			return "annuncio/insert";
		}
		
		annuncioDTO.setDateCreated(new Date());
		annuncioDTO.setAperto(true);
		annuncioDTO.setUtenteInserimentoId(utenteId);
		annuncioService.inserisciNuovo(annuncioDTO.buildAnnuncioModel(true,true));

		redirectAttrs.addFlashAttribute("successMessage", "Annuncio inserito correttamente");
		return "redirect:/home";
	}
	
	@GetMapping("/show/{idAnnuncio}")
	public String showAnnuncio(@PathVariable(required = true) Long idAnnuncio, Model model) {
		model.addAttribute("show_annuncio_attr",
				AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioService.caricaSingoloAnnuncioEager(idAnnuncio), true, true));
		return "showAnnuncio";
	}
	
	@GetMapping("/remove/{idAnnuncio}")
	public String deleteAnnuncio() {
		return null;
	}
	
	@PostMapping("/delete")
	public String removeAnnuncio() {
		return null;
	}
	
	@GetMapping("/edit/{idAnnuncio}")
	public String editAnnuncio() {
		return null; 
	}
	
	@PostMapping("/update")
	public String updateAnnuncio(){
		return null;
	}


}
