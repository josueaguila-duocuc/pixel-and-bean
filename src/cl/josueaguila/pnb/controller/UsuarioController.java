package cl.josueaguila.pnb.controller;

import cl.josueaguila.pnb.model.Usuario;
import cl.josueaguila.pnb.service.UsuarioService;
import java.util.List;

/**
 * Controlador para operaciones de Usuario
 */
public class UsuarioController {
    
    private final UsuarioService service;
    
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }
    
    public void crear(String username, String password, 
                     String nombreCompleto, String rol) {
        service.crear(username, password, nombreCompleto, rol);
    }
    
    public void actualizar(int id, String username, String password,
                          String nombreCompleto, String rol, boolean activo) {
        service.actualizar(id, username, password, nombreCompleto, rol, activo);
    }
    
    public void eliminar(int id) {
        service.eliminar(id);
    }
    
    public List<Usuario> listarTodos() {
        return service.listarTodos();
    }
    
    public List<Usuario> listarActivos() {
        return service.listarActivos();
    }
    
    public List<Usuario> buscar(String texto) {
        return service.buscar(texto);
    }
}