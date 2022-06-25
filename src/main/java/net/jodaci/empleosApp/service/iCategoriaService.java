package net.jodaci.empleosApp.service;

import net.jodaci.empleosApp.model.Categoria;
import net.jodaci.empleosApp.model.Vacante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface iCategoriaService {
    void guardar(Categoria categoria);
    List<Categoria> buscarTodas();
    Categoria buscarPorId(Integer idCategoria);
    Page<Categoria> buscarTodas(Pageable page);
    void eliminar(Integer idCategoria);
}