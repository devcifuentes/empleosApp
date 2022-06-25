package net.jodaci.empleosApp.controller;

import net.jodaci.empleosApp.model.Vacante;
import net.jodaci.empleosApp.service.iCategoriaService;
import net.jodaci.empleosApp.service.iVacantesService;
import net.jodaci.empleosApp.util.Utileria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {

    @Value("${empleosapp.ruta.imagenes}")
    private String ruta;
    @Autowired
    private iVacantesService serviceVacantes;
    @Autowired
    private iCategoriaService serviceCategorias;
    @GetMapping("/index")
    public String index(Model model){
        List<Vacante> lista = serviceVacantes.buscarTodas();
        model.addAttribute("vacantes",lista);
        return "vacantes/listVacantes";
    }

    @GetMapping(value = "/indexPaginate")
    public String mostrarIndexPaginado(Model model, Pageable page) {
        Page<Vacante> lista = serviceVacantes.buscarTodas(page);
        model.addAttribute("vacantes", lista);
        return "vacantes/listVacantes";
    }
    @GetMapping("/create")
    public String crear(Vacante vacante,Model model){

        return "vacantes/formVacante";
    }

    @PostMapping("/save")
    public String guardar(Vacante vacante, BindingResult result,
                          @RequestParam ("archivoImagen") MultipartFile multiPart,
                          RedirectAttributes attributes){
        if (result.hasErrors()) {
            for (ObjectError error: result.getAllErrors()){
                System.out.println("Ocurrio un error: "+ error.getDefaultMessage());
            }
            return "vacantes/formVacante";
        }

        if (!multiPart.isEmpty()) {
            //String ruta = "/empleosApp/img-vacantes/"; // Linux/MAC
            //String ruta = "c:/empleosApp/img-vacantes/"; // Windows
            String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
            if (nombreImagen != null){ // La imagen si se subio
            // Procesamos la variable nombreImagen
                vacante.setImagen(nombreImagen);
            }
        }

        serviceVacantes.guardar(vacante);
        attributes.addFlashAttribute("msg", "Registro Guardado");
        return "redirect:/vacantes/index";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") int idVacante,RedirectAttributes attributes){
        serviceVacantes.eliminar(idVacante);
        attributes.addFlashAttribute("msg","La vacante fue eliminada");
        return "redirect:/vacantes/index";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") int idVacante,Model model){
        Vacante vacante = serviceVacantes.buscarporId(idVacante);
        model.addAttribute("vacante",vacante);
        return "/vacantes/formVacante";
    }

    @GetMapping("/view/{id}")
    public String verDetalle(@PathVariable("id") int idVacante, Model model){

        Vacante vacante = serviceVacantes.buscarporId(idVacante);

        System.out.println("Vacante: " + vacante);
        model.addAttribute("vacante",vacante);
        //Buscar los detalles de la vacante en la BD
        return "detalle";
    }

    @ModelAttribute
    public void setGenericos(Model model){
        model.addAttribute("categorias",serviceCategorias.buscarTodas());
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
