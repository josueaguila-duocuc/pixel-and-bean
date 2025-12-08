package cl.josueaguila.pnb.repository.impl;

import cl.josueaguila.pnb.model.Producto;
import cl.josueaguila.pnb.repository.IProductoRepository;
import cl.josueaguila.pnb.util.DatabaseConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de ProductoRepository con JDBC.
 */
public class ProductoRepositoryImpl implements IProductoRepository {
    
    private static final String SQL_SELECT_ALL = 
        "SELECT id, nombre, categoria, tipo, descripcion, precio, activo FROM producto";
    
    private static final String SQL_SELECT_BY_ID = 
        SQL_SELECT_ALL + " WHERE id = ?";
    
    private static final String SQL_SELECT_ACTIVOS = 
        SQL_SELECT_ALL + " WHERE activo = TRUE ORDER BY categoria, nombre";
    
    private static final String SQL_SELECT_BY_CATEGORIA = 
        SQL_SELECT_ALL + " WHERE categoria = ? AND activo = TRUE ORDER BY nombre";
    
    private static final String SQL_SELECT_BY_NOMBRE = 
        SQL_SELECT_ALL + " WHERE nombre LIKE ? ORDER BY nombre";
    
    private static final String SQL_INSERT = 
        "INSERT INTO producto (nombre, categoria, tipo, descripcion, precio, activo) VALUES (?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_UPDATE = 
        "UPDATE producto SET nombre = ?, categoria = ?, tipo = ?, descripcion = ?, precio = ?, activo = ? WHERE id = ?";
    
    private static final String SQL_DELETE = 
        "DELETE FROM producto WHERE id = ?";
    
    private static final String SQL_UPDATE_ESTADO = 
        "UPDATE producto SET activo = ? WHERE id = ?";
    
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getInt("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setCategoria(rs.getString("categoria"));
        producto.setTipo(rs.getString("tipo"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setPrecio(rs.getDouble("precio"));  // BigDecimal para precisión monetaria
        producto.setActivo(rs.getBoolean("activo"));
        return producto;
    }
    
    @Override
    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL + " ORDER BY categoria, nombre");
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar todos los productos");
            e.printStackTrace();
        }
        
        return productos;
    }
    
    @Override
    public List<Producto> listarActivos() {
        List<Producto> productos = new ArrayList<>();
        
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ACTIVOS);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar productos activos");
            e.printStackTrace();
        }
        
        return productos;
    }
    
    @Override
    public List<Producto> buscarPorCategoria(String categoria) {
        List<Producto> productos = new ArrayList<>();
        
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_CATEGORIA)) {
            
            ps.setString(1, categoria);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productos.add(mapearProducto(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar productos por categoría: " + categoria);
            e.printStackTrace();
        }
        
        return productos;
    }
    
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productos = new ArrayList<>();
        
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_NOMBRE)) {
            
            ps.setString(1, "%" + nombre + "%"); // LIKE %nombre%
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productos.add(mapearProducto(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar productos por nombre: " + nombre);
            e.printStackTrace();
        }
        
        return productos;
    }
    
    @Override
    public Optional<Producto> buscarPorId(int id) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearProducto(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar producto por ID: " + id);
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    @Override
    public Producto crear(Producto producto) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getCategoria());
            ps.setString(3, producto.getTipo());
            ps.setString(4, producto.getDescripcion());
            ps.setDouble(5, producto.getPrecio());  // BigDecimal para precisión monetaria
            ps.setBoolean(6, producto.isActivo());
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        producto.setId(rs.getInt(1));
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al crear producto: " + producto.getNombre());
            e.printStackTrace();
            throw new RuntimeException("Error al crear producto", e);
        }
        
        return producto;
    }
    
    @Override
    public void actualizar(Producto producto) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getCategoria());
            ps.setString(3, producto.getTipo());
            ps.setString(4, producto.getDescripcion());
            ps.setDouble(5, producto.getPrecio());  // BigDecimal para precisión monetaria
            ps.setBoolean(6, producto.isActivo());
            ps.setInt(7, producto.getId());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto ID: " + producto.getId());
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar producto", e);
        }
    }
    
    @Override
    public void eliminar(int id) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto ID: " + id);
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar producto", e);
        }
    }
    
    @Override
    public void desactivar(int id) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_ESTADO)) {
            
            ps.setBoolean(1, false);
            ps.setInt(2, id);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al desactivar producto ID: " + id);
            e.printStackTrace();
            throw new RuntimeException("Error al desactivar producto", e);
        }
    }
    
    @Override
    public void activar(int id) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_ESTADO)) {
            
            ps.setBoolean(1, true);
            ps.setInt(2, id);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al activar producto ID: " + id);
            e.printStackTrace();
            throw new RuntimeException("Error al activar producto", e);
        }
    }
}