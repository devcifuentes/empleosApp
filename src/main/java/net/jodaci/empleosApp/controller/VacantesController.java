package net.jodaci.empleosApp.controller;

import net.jodaci.empleosApp.model.Vacante;
import net.jodaci.empleosApp.service.iVacantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {

    @Autowired
    private iVacantesService serviceVacantes;

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
}
