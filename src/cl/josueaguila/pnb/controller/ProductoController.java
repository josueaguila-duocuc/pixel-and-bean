package cl.josueaguila.pnb.controller;

import cl.josueaguila.pnb.model.Producto;
import cl.josueaguila.pnb.service.ProductoService;
import java.util.List;

/**
 * Controlador para operaciones de Producto
 */
public class ProductoController {
    
    private final ProductoService service;
    
    public ProductoController(ProductoService service) {
        this.service = service;
    }
    
    public void crear(String nombre, String categoria, String tipo, double precio) {
        service.crear(nombre, categoria, tipo, precio);
    }
    
    public void actualizar(int id, String nombre, String categoria,
                          String tipo, double precio, boolean activo) {
        service.actualizar(id, nombre, categoria, tipo, precio, activo);
    }
    
    public void eliminar(int id) {
        service.eliminar(id);
    }
    
    public void cambiarEstado(int id) {
        service.cambiarEstado(id);
    }
    
    public List<Producto> listarTodos() {
        return service.listarTodos();
    }
    
    public List<Producto> listarActivos() {
        return service.listarActivos();
    }
    
    public List<Producto> listarPorCategoria(String categoria) {
        return service.listarPorCategoria(categoria);
    }
    
    public List<Producto> buscarPorNombre(String nombre) {
        return service.buscarPorNombre(nombre);
    }
}