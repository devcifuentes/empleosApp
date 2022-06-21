package net.jodaci.empleosApp.controller;

import net.jodaci.empleosApp.model.Vacante;
import net.jodaci.empleosApp.service.iVacantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private iVacantesService serviceVacantes;
    @GetMapping("/")
    public String mostrarHome(Model model){

        List<Vacante> lista = serviceVacantes.buscarTodas();
        model.addAttribute("vacantes",lista);

        return "home";
    }

    @GetMapping("/acercade")
    public String acercaDe(Model model){
        model.addAttribute("fecha",new Date());
        return "acercade";
    }

    @GetMapping("/listado")
    public String mostrarListado(Model model){
        List<String> lista = new LinkedList<String>();
        lista.add("Ingeniero de Sistemas");
        lista.add("Auxiliar de contabilidad");
        lista.add("Vendedor");
        lista.add("Arquitecto");

        model.addAttribute("empleos",lista);

        return "listado";
    }

    @GetMapping("/detalle")
    public String mostrarDetalle(Model model){
        Vacante vacante = new Vacante();
        vacante.setNombre("Ingeniero de comunicaciones");
        vacante.setDescripcion("Se solicita ingeniero para dar soporte a intranet");
        vacante.setFecha(new Date());
        vacante.setSalario(9700.45);

        model.addAttribute("vacante",vacante);

        return "detalle";
    }

    @GetMapping("/tabla")
    public String mostrarTabla(Model model){
        List<Vacante> lista = serviceVacantes.buscarTodas();
        model.addAttribute("vacantes",lista);
        return"tabla";
    }

}
