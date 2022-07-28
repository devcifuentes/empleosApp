package net.jodaci.empleosApp.service.db;

import net.jodaci.empleosApp.model.Categoria;
import net.jodaci.empleosApp.repository.CategoriasRepository;
import net.jodaci.empleosApp.service.iCategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Primary
public class CategoriasServiceJpa implements iCategoriaService {

    @Autowired
    private CategoriasRepository repoCategorias;
    @Override
    public void guardar(Categoria categoria) {
        repoCategorias.save(categoria);
    }

    @Override
    public List<Categoria> buscarTodas() {
        return repoCategorias.findAll();
    }

    @Override
    public Categoria buscarPorId(Integer idCategoria) {
        Optional<Categoria> optional = repoCategorias.findById(idCategoria);
        if (optional.isPresent()){
          return  optional.get();
        }
        return null;
    }

    @Override
    public Page<Categoria> buscarTodas(Pageable page) {
        return repoCategorias.findAll(page);
    }

    @Override
    public void eliminar(Integer idCategoria) {
        repoCategorias.deleteById(idCategoria);
    }
}
