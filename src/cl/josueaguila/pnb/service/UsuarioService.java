package cl.josueaguila.pnb.service;

import cl.josueaguila.pnb.model.Usuario;
import java.util.List;

/**
 * Servicio para gestión de usuarios.
 */
public interface UsuarioService {

    List<Usuario> listarTodos();
    Usuario buscarPorId(int id);
    List<Usuario> buscarPorUsername(String username);
    Usuario guardar(Usuario usuario);
    void actualizar(Usuario usuario);
    void eliminar(int id);
    void cambiarEstado(int id, boolean activo);
    Usuario autenticar(String username, String password);
}
