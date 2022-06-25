package net.jodaci.empleosApp.service.db;

import net.jodaci.empleosApp.model.Vacante;
import net.jodaci.empleosApp.repository.VacantesRepository;
import net.jodaci.empleosApp.service.iVacantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class VacantesServiceJpa implements iVacantesService {

    @Autowired
    private VacantesRepository repoVacantes;
    @Override
    public List<Vacante> buscarTodas() {
        return repoVacantes.findAll();
    }

    @Override
    public Vacante buscarporId(Integer idVacante) {
        Optional<Vacante> optional = repoVacantes.findById(idVacante);
        if (optional.isPresent()){
            return  optional.get();
        }
        return null;
    }

    @Override
    public void guardar(Vacante vacante) {

        repoVacantes.save(vacante);
    }

    @Override
    public List<Vacante> buscarDestacadas() {
        return repoVacantes.findByDestacadoAndEstatusOrderByIdDesc(1,"Aprobada");
    }

    @Override
    public void eliminar(Integer idVacante) {
        repoVacantes.deleteById(idVacante);
    }

    @Override
    public List<Vacante> buscarByExample(Example<Vacante> example) {
        return repoVacantes.findAll(example);
    }

    @Override
    public Page<Vacante> buscarTodas(Pageable page) {
        return repoVacantes.findAll(page);
    }
}
