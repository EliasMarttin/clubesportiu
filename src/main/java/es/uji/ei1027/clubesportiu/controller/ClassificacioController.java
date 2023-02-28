package es.uji.ei1027.clubesportiu.controller;


import es.uji.ei1027.clubesportiu.dao.ClassificacioDao;
import es.uji.ei1027.clubesportiu.dao.ProvaDao;
import es.uji.ei1027.clubesportiu.model.Classificacio;
import es.uji.ei1027.clubesportiu.model.Nadador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/classificacio")
public class ClassificacioController {
    private ClassificacioDao classificacioDao;
    @Autowired

    public void setClassificacioDao (ClassificacioDao classificacioDao) {this.classificacioDao = classificacioDao;}

    @RequestMapping("/list")
    public String listClassificacio(Model model) {
        model.addAttribute("classificacions",classificacioDao.getClassificacions());
        return "classificacio/list";

    }
    // ADD ZONE
    @RequestMapping(value="/add")
    public String addClassificacio(Model model) {
        model.addAttribute("classificacio", new Classificacio());
        return "classificacio/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("classificacio") Classificacio classificacio,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "classificacio/add";
        classificacioDao.addClassificacio(classificacio);
        return "redirect:/classificacio/list";
    }
    //UPDATE ZONE

    @RequestMapping(value="/update/{nom}", method = RequestMethod.GET)
    public String editClassificacio(Model model, @PathVariable String nom) {
        model.addAttribute("classificacio", classificacioDao.getClassificacio(nom));
        return "nadador/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("nadador") Nadador nadador,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "nadador/update";
        nadadorDao.updateNadador(nadador);
        return "redirect:list";
    }

    //DELETE ZONE
    @RequestMapping(value="/delete/{nom}")
    public String processDelete(@PathVariable String nom) {
        nadadorDao.deleteNadadorByName(nom);
        return "redirect:../list";
    }
}
