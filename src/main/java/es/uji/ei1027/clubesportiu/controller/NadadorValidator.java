package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.NadadorDao;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.clubesportiu.model.Nadador;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;

public class NadadorValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Nadador.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Nadador nadador = (Nadador) obj;
        if (nadador.getNom().trim().equals(""))
            errors.rejectValue("nom", "obligatori",
                    "Cal introduir un valor");

        // Afegeix ací la validació per a Edat > 15 anys
        if(nadador.getEdat() < 15)
            errors.rejectValue("edat", "valor incorrecte",
                    "La edad tiene que ser mayor de 15");

        //Validación de Género
        List<String> valors = Arrays.asList("Femeni", "Masculi");
        if (!valors.contains(nadador.getGenere()))
            errors.rejectValue("genere", "valor incorrecte",
                    "Deu ser: Femeni o Masculi");


    }
    NadadorDao nadadorDao;
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("nadador") Nadador nadador,
                                   BindingResult bindingResult) {
        NadadorValidator nadadorValidator = new NadadorValidator();
        nadadorValidator.validate(nadador, bindingResult);
        if (bindingResult.hasErrors())
        return "nadador/add";
        nadadorDao.addNadador(nadador);
        return "redirect:list";
    }
}

