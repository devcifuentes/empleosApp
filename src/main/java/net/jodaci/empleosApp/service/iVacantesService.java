package net.jodaci.empleosApp.service;

import net.jodaci.empleosApp.model.Vacante;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface iVacantesService {
    List<Vacante> buscarTodas();
    Vacante buscarporId (Integer idVacante);

    void guardar(Vacante vacante);

    List<Vacante> buscarDestacadas();

    void eliminar(Integer idVacante);

    List<Vacante> buscarByExample(Example<Vacante> example);

    Page<Vacante> buscarTodas(Pageable page);
}
