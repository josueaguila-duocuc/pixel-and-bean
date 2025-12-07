package cl.josueaguila.pnb.service;

import cl.josueaguila.pnb.model.Usuario;
import cl.josueaguila.pnb.repository.IUsuarioRepository;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de lógica de negocio para Usuario
 * Contiene validaciones y reglas de negocio
 */
public class UsuarioService {
    
    private final IUsuarioRepository repository;
    
    /**
     * Constructor con inyección de dependencias
     */
    public UsuarioService(IUsuarioRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Autentica un usuario
     * @throws RuntimeException si las credenciales son inválidas
     */
    public Usuario autenticar(String username, String password) {
        // Validaciones básicas
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        
        // Buscar usuario
        Usuario usuario = repository.buscarPorUsername(username.trim());
        
        if (usuario == null) {
            throw new RuntimeException("Credenciales inválidas");
        }
        
        if (!usuario.isActivo()) {
            throw new RuntimeException("Usuario inactivo. Contacta al administrador");
        }
        
        // Verificar contraseña (por ahora sin hash)
        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("Credenciales inválidas");
        }
        
        return usuario;
    }
    
    /**
     * Crea un nuevo usuario
     */
    public void crear(String username, String password, String nombreCompleto, String rol) {
        // Validaciones
        validarDatosUsuario(username, password, nombreCompleto, rol);
        
        // Verificar que no exista el username
        if (repository.existeUsername(username)) {
            throw new RuntimeException("El username '" + username + "' ya existe");
        }
        
        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(username.trim().toLowerCase());
        usuario.setPassword(password); // TODO: Hash en Clase 5
        usuario.setNombreCompleto(nombreCompleto.trim());
        usuario.setRol(rol);
        usuario.setActivo(true);
        
        repository.guardar(usuario);
    }
    
    /**
     * Actualiza un usuario existente
     */
    public void actualizar(int id, String username, String password, 
                           String nombreCompleto, String rol, boolean activo) {
        // Validaciones
        validarDatosUsuario(username, password, nombreCompleto, rol);
        
        // Buscar usuario existente
        Usuario usuario = repository.buscarPorId(id);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        
        // Verificar username único (si cambió)
        if (!usuario.getUsername().equals(username) && 
            repository.existeUsername(username)) {
            throw new RuntimeException("El username '" + username + "' ya existe");
        }
        
        // Actualizar datos
        usuario.setUsername(username.trim().toLowerCase());
        if (password != null && !password.trim().isEmpty()) {
            usuario.setPassword(password);
        }
        usuario.setNombreCompleto(nombreCompleto.trim());
        usuario.setRol(rol);
        usuario.setActivo(activo);
        
        repository.actualizar(usuario);
    }
    
    /**
     * Elimina un usuario
     * Regla de negocio: No se puede eliminar el último ADMIN activo
     */
    public void eliminar(int id) {
        Usuario usuario = repository.buscarPorId(id);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        
        // Validar que no sea el último admin activo
        if (usuario.getRol().equals("ADMIN") && usuario.isActivo()) {
            int adminsActivos = repository.contarActivosPorRol("ADMIN");
            if (adminsActivos <= 1) {
                throw new RuntimeException(
                    "No se puede eliminar el último administrador activo");
            }
        }
        
        repository.eliminar(id);
    }
    
    /**
     * Lista todos los usuarios
     */
    public List<Usuario> listarTodos() {
        return repository.listarTodos();
    }
    
    /**
     * Lista usuarios activos
     */
    public List<Usuario> listarActivos() {
        return repository.listarTodos().stream()
                .filter(Usuario::isActivo)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca usuarios por texto (username o nombre)
     */
    public List<Usuario> buscar(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return listarTodos();
        }
        
        String textoLower = texto.toLowerCase();
        return repository.listarTodos().stream()
                .filter(u -> u.getUsername().toLowerCase().contains(textoLower) ||
                            u.getNombreCompleto().toLowerCase().contains(textoLower))
                .collect(Collectors.toList());
    }
    
    /**
     * Validaciones comunes de usuario
     */
    private void validarDatosUsuario(String username, String password, 
                                     String nombreCompleto, String rol) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }
        if (username.trim().length() < 4) {
            throw new IllegalArgumentException(
                "El username debe tener al menos 4 caracteres");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException(
                "La contraseña debe tener al menos 6 caracteres");
        }
        
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio");
        }
        
        if (rol == null || rol.trim().isEmpty()) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }
        if (!rol.equals("ADMIN") && !rol.equals("OPERADOR")) {
            throw new IllegalArgumentException(
                "El rol debe ser ADMIN u OPERADOR");
        }
    }
}