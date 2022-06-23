package net.jodaci.empleosApp.controller;

import net.jodaci.empleosApp.model.Categoria;
import net.jodaci.empleosApp.model.Vacante;
import net.jodaci.empleosApp.service.iCategoriaService;
import net.jodaci.empleosApp.service.iVacantesService;
import net.jodaci.empleosApp.util.Utileria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
    @GetMapping("/create")
    public String crear(Vacante vacante,Model model){

        model.addAttribute("categorias",serviceCategorias.buscarTodas());
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
    /*
    @PostMapping("/save")
    public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion,
                          @RequestParam("estatus") String estatus, @RequestParam("fecha") String fecha, @RequestParam("destacado") int destacado,
                          @RequestParam("salario") double salario, @RequestParam("detalles") String detalles) {
        System.out.println("Nombre Vacante: " + nombre);
        System.out.println("Descripcion: " + descripcion);
        System.out.println("Estatus: " + estatus);
        System.out.println("Fecha Publicación: " + fecha);
        System.out.println("Destacado: " + destacado);
        System.out.println("Salario Ofrecido: " + salario);
        System.out.println("detalles: " + detalles);
        return "vacantes/listVacantes";
    }
    */

    @GetMapping("/delete")
    public String eliminar(@RequestParam("id") int idVacante,Model model){
        System.out.println("Borrando vacante con id: "+ idVacante);
        model.addAttribute("id",idVacante);
        return "mensaje";
    }

    @GetMapping("/view/{id}")
    public String verDetalle(@PathVariable("id") int idVacante, Model model){

        Vacante vacante = serviceVacantes.buscarporId(idVacante);

        System.out.println("Vacante: " + vacante);
        model.addAttribute("vacante",vacante);

        //Buscar los detalles de la vacante en la BD
        return "detalle";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
