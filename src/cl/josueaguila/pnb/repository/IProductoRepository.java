package cl.josueaguila.pnb.repository;

import cl.josueaguila.pnb.model.Producto;
import java.util.List;
import java.util.Optional;

public interface IProductoRepository {
    
    List<Producto> listarTodos();
    List<Producto> listarActivos();
    List<Producto> buscarPorCategoria(String categoria);
    List<Producto> buscarPorNombre(String nombre);
    Optional<Producto> buscarPorId(int id);
    Producto crear(Producto producto);
    void actualizar(Producto producto);
    void eliminar(int id);
    void desactivar(int id);
    void activar(int id);
}