package cl.josueaguila.pnb.service;

import cl.josueaguila.pnb.model.Producto;
import cl.josueaguila.pnb.repository.IProductoRepository;
import java.util.List;
/**
 * Servicio de lógica de negocio para Producto
 */
public class ProductoService {
    
    private final IProductoRepository repository;
    
    public ProductoService(IProductoRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Crea un nuevo producto
     */
    public void crear(String nombre, String categoria, String tipo, double precio) {
        // Validaciones
        validarDatosProducto(nombre, categoria, tipo, precio);
        
        // Crear producto
        Producto producto = new Producto();
        producto.setNombre(nombre.trim());
        producto.setCategoria(categoria);
        producto.setTipo(tipo);
        producto.setPrecio(precio);
        producto.setActivo(true);
        
        repository.guardar(producto);
    }
    
    /**
     * Actualiza un producto existente
     */
    public void actualizar(int id, String nombre, String categoria, 
                          String tipo, double precio, boolean activo) {
        // Validaciones
        validarDatosProducto(nombre, categoria, tipo, precio);
        
        // Buscar producto
        Producto producto = repository.buscarPorId(id);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }
        
        // Actualizar
        producto.setNombre(nombre.trim());
        producto.setCategoria(categoria);
        producto.setTipo(tipo);
        producto.setPrecio(precio);
        producto.setActivo(activo);
        
        repository.actualizar(producto);
    }
    
    /**
     * Elimina un producto
     */
    public void eliminar(int id) {
        Producto producto = repository.buscarPorId(id);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }
        
        repository.eliminar(id);
    }
    
    /**
     * Cambia el estado de un producto
     */
    public void cambiarEstado(int id) {
        Producto producto = repository.buscarPorId(id);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }
        
        repository.cambiarEstado(id, !producto.isActivo());
    }
    
    /**
     * Lista todos los productos
     */
    public List<Producto> listarTodos() {
        return repository.listarTodos();
    }
    
    /**
     * Lista productos activos
     */
    public List<Producto> listarActivos() {
        return repository.listarActivos();
    }
    
    /**
     * Lista productos por categoría
     */
    public List<Producto> listarPorCategoria(String categoria) {
        return repository.listarPorCategoria(categoria);
    }
    
    /**
     * Busca productos por nombre
     */
    public List<Producto> buscarPorNombre(String nombre) {
        return repository.buscarPorNombre(nombre);
    }
    
    /**
     * Validaciones de producto
     */
    private void validarDatosProducto(String nombre, String categoria, 
                                      String tipo, double precio) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }
        if (!categoria.equals("BEBIDA") && !categoria.equals("SNACK") && 
            !categoria.equals("TIEMPO")) {
            throw new IllegalArgumentException(
                "Categoría inválida. Debe ser BEBIDA, SNACK o TIEMPO");
        }
        
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo es obligatorio");
        }
        
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
    }
}