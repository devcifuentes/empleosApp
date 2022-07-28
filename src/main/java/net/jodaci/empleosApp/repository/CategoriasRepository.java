package net.jodaci.empleosApp.repository;


import net.jodaci.empleosApp.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


//public interface CategoriasRepository extends CrudRepository<Categoria,Integer>

public interface CategoriasRepository extends JpaRepository<Categoria,Integer> {

}
