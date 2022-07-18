package net.jodaci.empleosApp.controller;

import net.jodaci.empleosApp.model.Perfil;
import net.jodaci.empleosApp.model.Usuario;
import net.jodaci.empleosApp.model.Vacante;
import net.jodaci.empleosApp.service.iCategoriaService;
import net.jodaci.empleosApp.service.iUsuariosService;
import net.jodaci.empleosApp.service.iVacantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private iVacantesService serviceVacantes;
    @Autowired
    private iUsuariosService serviceUsuarios;
    @Autowired
    private iCategoriaService serviceCategorias;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/index")
    public String mostrarIndex(Authentication auth, HttpSession session){

        String userName = auth.getName();
        System.out.println("El usuario ingresado es:"+userName);
        for(GrantedAuthority rol: auth.getAuthorities()){
            System.out.println("Rol: "+ rol.getAuthority());
        }

        if(session.getAttribute("usuario")== null){
            Usuario usuario = serviceUsuarios.buscarPorUsername(userName);
            usuario.setPassword(null);
            System.out.println("Usuario: "+usuario);
            session.setAttribute("usuario",usuario);
        }


        return "redirect:/";
    }

    @GetMapping("/")
    public String mostrarHome(Model model){        return "home";    }

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

    @GetMapping("/signup")
    public  String resgistrarse(Usuario usuario){
        return "/usuarios/formRegistro";
    }

    @PostMapping("/signup")
    public String guardarRegistro(Usuario usuario, RedirectAttributes attributes, BindingResult result){
        if (result.hasErrors()) {
            for (ObjectError error: result.getAllErrors()){
                System.out.println("Ocurrio un error: "+ error.getDefaultMessage());
            }
            return "/usuarios/formRegistro";
        }
        String pwPlano = usuario.getPassword();
        String pwdEncriptado = passwordEncoder.encode(pwPlano);
        usuario.setPassword(pwdEncriptado);
        usuario.setEstatus(1);
        usuario.setFechaRegistro(new Date());
        // Creamos el Perfil que le asignaremos al usuario nuevo
        Perfil perfil = new Perfil();
        perfil.setId(3); // Perfil USUARIO
        usuario.agregar(perfil);
        serviceUsuarios.guardar(usuario);
        attributes.addFlashAttribute("msg", "Registro Guardado");
        return "redirect:/usuarios/index";
    }

    @GetMapping("/search")
    public String buscar(@ModelAttribute("search") Vacante vacante,Model model){
        System.out.println("Buscando por:" + vacante);
        ExampleMatcher matcher = ExampleMatcher.matching().
                withMatcher("descripcion",ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Vacante> example = Example.of(vacante,matcher);
        List<Vacante> lista = serviceVacantes.buscarByExample(example);
        model.addAttribute("vacantes",lista);
        return "home";
    }

    @GetMapping("/login" )
    public String mostrarLogin() {
        return "formLogin";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        SecurityContextLogoutHandler logoutHandler =
                new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/";
    }

    @GetMapping("/error")
    public String error(Model model){
        return "error";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    @ModelAttribute
    public void setGenericos (Model model){
        model.addAttribute("categorias",serviceCategorias.buscarTodas());
        model.addAttribute("vacantes",serviceVacantes.buscarDestacadas());
        Vacante vacanteSearch = new Vacante();
        vacanteSearch.reset();
        model.addAttribute("search",vacanteSearch);
    }

    @GetMapping("/bcrypt/{texto}")
    @ResponseBody
    public String encriptar(@PathVariable String texto){
        return texto + " Encriptado en bycript: "+passwordEncoder.encode(texto);
    }
}
