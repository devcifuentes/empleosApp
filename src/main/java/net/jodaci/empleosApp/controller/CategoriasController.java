package net.jodaci.empleosApp.controller;

import net.jodaci.empleosApp.model.Categoria;
import net.jodaci.empleosApp.model.Vacante;
import net.jodaci.empleosApp.service.iCategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {

    @Autowired
    private iCategoriaService serviceCategorias;

    // @GetMapping("/index")
    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String mostrarIndex(Model model) {

        List<Categoria> lista = serviceCategorias.buscarTodas();
        model.addAttribute("categorias",lista);
        return "categorias/listCategorias";
    }
    // @GetMapping("/create")
    @RequestMapping(value="/create", method=RequestMethod.GET)
    public String crear() {
        return "categorias/formCategorias";
    }
    // @PostMapping("/save")
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public String guardar(Categoria categoria, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            for (ObjectError error: result.getAllErrors()){
                System.out.println("Ocurrio un error: "+ error.getDefaultMessage());
            }
            return "categorias/formCategorias";
        }
        serviceCategorias.guardar(categoria);
        attributes.addFlashAttribute("msg", "Registro Guardado");
        return "redirect:index";
    }


}
