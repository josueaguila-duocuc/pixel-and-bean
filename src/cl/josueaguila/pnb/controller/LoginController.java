package cl.josueaguila.pnb.controller;

import cl.josueaguila.pnb.model.Usuario;
import cl.josueaguila.pnb.service.UsuarioService;

/**
 * Controlador para operaciones de autenticación
 */
public class LoginController {
    
    private final UsuarioService usuarioService;
    
    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    /**
     * Intenta autenticar un usuario
     * @return Usuario autenticado
     * @throws RuntimeException si las credenciales son inválidas
     */
    public Usuario autenticar(String username, String password) {
        return usuarioService.autenticar(username, password);
    }
}