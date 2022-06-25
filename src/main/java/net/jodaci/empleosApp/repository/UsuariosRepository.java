package net.jodaci.empleosApp.repository;

import net.jodaci.empleosApp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

}
