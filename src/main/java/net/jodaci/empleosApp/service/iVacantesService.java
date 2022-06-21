package net.jodaci.empleosApp.service;

import net.jodaci.empleosApp.model.Vacante;
import java.util.List;

public interface iVacantesService {
    List<Vacante> buscarTodas();
    Vacante buscarporId (Integer idVacante);
}
