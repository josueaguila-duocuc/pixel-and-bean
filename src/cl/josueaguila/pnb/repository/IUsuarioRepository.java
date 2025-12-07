package cl.josueaguila.pnb.repository;

import cl.josueaguila.pnb.model.Usuario;
import java.util.List;

/**
 * Contrato de operaciones para acceso a datos de Usuario
 */
public interface IUsuarioRepository {
    
    /**
     * Busca un usuario por su ID
     * @param id ID del usuario
     * @return Usuario encontrado o null
     */
    Usuario buscarPorId(int id);
    
    /**
     * Busca un usuario por su username
     * @param username Username a buscar
     * @return Usuario encontrado o null
     */
    Usuario buscarPorUsername(String username);
    
    /**
     * Lista todos los usuarios
     * @return Lista de todos los usuarios
     */
    List<Usuario> listarTodos();
    
    /**
     * Lista usuarios por rol
     * @param rol Rol a filtrar (ADMIN, OPERADOR)
     * @return Lista de usuarios con ese rol
     */
    List<Usuario> listarPorRol(String rol);
    
    /**
     * Guarda un nuevo usuario
     * @param usuario Usuario a guardar
     * @return ID generado
     */
    int guardar(Usuario usuario);
    
    /**
     * Actualiza un usuario existente
     * @param usuario Usuario con datos actualizados
     */
    void actualizar(Usuario usuario);
    
    /**
     * Elimina un usuario por ID
     * @param id ID del usuario a eliminar
     */
    void eliminar(int id);
    
    /**
     * Verifica si existe un username
     * @param username Username a verificar
     * @return true si existe, false si no
     */
    boolean existeUsername(String username);
    
    /**
     * Cuenta usuarios activos por rol
     * @param rol Rol a contar
     * @return Cantidad de usuarios activos con ese rol
     */
    int contarActivosPorRol(String rol);
}