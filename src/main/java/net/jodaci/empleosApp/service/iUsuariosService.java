package net.jodaci.empleosApp.service;

import net.jodaci.empleosApp.model.Usuario;

import java.util.List;

public interface iUsuariosService {

    void guardar(Usuario usuario);

    void eliminar(Integer idUsuario);

    List<Usuario> buscarTodos();
    Usuario buscarPorUsername(String username);
    int bloquear(int idUsuario);
    int activar(int idUsuario);

}
