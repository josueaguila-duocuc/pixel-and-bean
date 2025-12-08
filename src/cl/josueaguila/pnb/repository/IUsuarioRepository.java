package cl.josueaguila.pnb.repository;

import cl.josueaguila.pnb.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository {
    
    /**
     * Busca un usuario por su username.
     * @return Optional con el usuario, o vacío si no existe
     */
    Optional<Usuario> buscarPorUsername(String username);
    
    /**
     * Autentica un usuario por username y password.
     * @return Optional con el usuario si las credenciales son válidas
     */
    Optional<Usuario> autenticar(String username, String password);
    
    /**
     * Lista todos los usuarios activos.
     */
    List<Usuario> listarActivos();
    
    /**
     * Lista todos los usuarios (activos e inactivos).
     */
    List<Usuario> listarTodos();
    
    /**
     * Busca un usuario por ID.
     */
    Optional<Usuario> buscarPorId(int id);
    
    /**
     * Crea un nuevo usuario.
     * @return El usuario creado con su ID asignado
     */
    Usuario crear(Usuario usuario);
    
    /**
     * Actualiza un usuario existente.
     */
    void actualizar(Usuario usuario);
    
    /**
     * Elimina un usuario (físicamente).
     */
    void eliminar(int id);
    
    /**
     * Desactiva un usuario (baja lógica).
     */
    void desactivar(int id);
    
    /**
     * Activa un usuario previamente desactivado.
     */
    void activar(int id);
}

