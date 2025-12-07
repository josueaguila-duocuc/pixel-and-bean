package cl.josueaguila.pnb.repository;

import cl.josueaguila.pnb.model.Producto;
import java.util.List;

/**
 * Contrato de operaciones para acceso a datos de Producto
 */
public interface IProductoRepository {
    
    /**
     * Busca un producto por su ID
     */
    Producto buscarPorId(int id);
    
    /**
     * Lista todos los productos
     */
    List<Producto> listarTodos();
    
    /**
     * Lista productos activos solamente
     */
    List<Producto> listarActivos();
    
    /**
     * Lista productos por categoría
     * @param categoria BEBIDA, SNACK, TIEMPO
     */
    List<Producto> listarPorCategoria(String categoria);
    
    /**
     * Busca productos por nombre (búsqueda parcial)
     */
    List<Producto> buscarPorNombre(String nombre);
    
    /**
     * Guarda un nuevo producto
     */
    int guardar(Producto producto);
    
    /**
     * Actualiza un producto existente
     */
    void actualizar(Producto producto);
    
    /**
     * Elimina un producto por ID
     */
    void eliminar(int id);
    
    /**
     * Cambia el estado activo/inactivo de un producto
     */
    void cambiarEstado(int id, boolean activo);
}