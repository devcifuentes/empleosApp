package net.jodaci.empleosApp.service.db;

import net.jodaci.empleosApp.model.Usuario;
import net.jodaci.empleosApp.repository.UsuariosRepository;
import net.jodaci.empleosApp.service.iUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuariosServiceJpa implements iUsuariosService {
    @Autowired
    private UsuariosRepository repoUsuarios;

    @Override
    public void guardar(Usuario usuario) {
        repoUsuarios.save(usuario);
    }

    @Override
    public void eliminar(Integer idUsuario) {
        repoUsuarios.deleteById(idUsuario);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return repoUsuarios.findAll();
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return repoUsuarios.findByUsername(username);
    }

    @Transactional
    @Override
    public int bloquear(int idUsuario) {
        int rows = repoUsuarios.lock(idUsuario);
        return rows;
    }

    @Transactional
    @Override
    public int activar(int idUsuario) {
        int rows = repoUsuarios.unlock(idUsuario);
        return rows;
    }

}
