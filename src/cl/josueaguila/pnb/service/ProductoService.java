package cl.josueaguila.pnb.service;

import cl.josueaguila.pnb.model.Producto;
import java.util.List;

/**
 * Servicio para gestión de productos.
 */
public interface ProductoService {

    List<Producto> listarTodos();
    List<Producto> listarActivos();
    Producto buscarPorId(int id);
    List<Producto> buscarPorNombre(String nombre);
    List<Producto> filtrarPorCategoria(String categoria);
    Producto guardar(Producto producto);
    void actualizar(Producto producto);
    void eliminar(int id);
    void cambiarEstado(int id, boolean activo);
}
