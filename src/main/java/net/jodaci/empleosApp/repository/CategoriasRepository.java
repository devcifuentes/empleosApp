package net.jodaci.empleosApp.repository;


import net.jodaci.empleosApp.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CategoriasRepository extends JpaRepository<Categoria,Integer> {

}
