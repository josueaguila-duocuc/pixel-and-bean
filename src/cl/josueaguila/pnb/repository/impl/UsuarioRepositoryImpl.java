package cl.josueaguila.pnb.repository.impl;

import cl.josueaguila.pnb.model.Usuario;
import cl.josueaguila.pnb.repository.IUsuarioRepository;
import cl.josueaguila.pnb.util.DatabaseConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de UsuarioRepository con JDBC.
 * 
 * @author Tu Nombre
 */
public class UsuarioRepositoryImpl implements IUsuarioRepository {
    
    // Consultas SQL como constantes (evita typos y facilita mantenimiento)
    private static final String SQL_SELECT_ALL = 
        "SELECT id, username, password, nombre_completo, rol, activo FROM usuario";
    
    private static final String SQL_SELECT_BY_ID = 
        SQL_SELECT_ALL + " WHERE id = ?";
    
    private static final String SQL_SELECT_BY_USERNAME = 
        SQL_SELECT_ALL + " WHERE username = ?";
    
    private static final String SQL_SELECT_ACTIVOS = 
        SQL_SELECT_ALL + " WHERE activo = TRUE";
    
    private static final String SQL_AUTENTICAR = 
        SQL_SELECT_ALL + " WHERE username = ? AND password = ? AND activo = TRUE";
    
    private static final String SQL_INSERT = 
        "INSERT INTO usuario (username, password, nombre_completo, rol, activo) VALUES (?, ?, ?, ?, ?)";
    
    private static final String SQL_UPDATE = 
        "UPDATE usuario SET username = ?, password = ?, nombre_completo = ?, rol = ?, activo = ? WHERE id = ?";
    
    private static final String SQL_DELETE = 
        "DELETE FROM usuario WHERE id = ?";
    
    private static final String SQL_UPDATE_ESTADO = 
        "UPDATE usuario SET activo = ? WHERE id = ?";
    
    /**
     * Mapea un ResultSet a un objeto Usuario.
     */
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));
        usuario.setNombreCompleto(rs.getString("nombre_completo"));
        usuario.setrol(rol.valueOf(rs.getString("rol")));  // Convertir String a Enum
        usuario.setActivo(rs.getBoolean("activo"));
        return usuario;
    }
    
    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_USERNAME)) {
            
            ps.setString(1, username);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearUsuario(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por username: " + username);
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    @Override
    public Optional<Usuario> autenticar(String username, String password) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_AUTENTICAR)) {
            
            ps.setString(1, username);
            ps.setString(2, password); // TODO: En producción, hashear password
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearUsuario(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al autenticar usuario: " + username);
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Usuario> listarActivos() {
        List<Usuario> usuarios = new ArrayList<>();
        
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ACTIVOS);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios activos");
            e.printStackTrace();
        }
        
        return usuarios;
    }
    
    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar todos los usuarios");
            e.printStackTrace();
        }
        
        return usuarios;
    }
    
    @Override
    public Optional<Usuario> buscarPorId(int id) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearUsuario(rs));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por ID: " + id);
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    @Override
    public Usuario crear(Usuario usuario) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword()); // TODO: Hashear
            ps.setString(3, usuario.getNombreCompleto());
            ps.setString(4, usuario.getRol().name());  // Convertir Enum a String
            ps.setBoolean(5, usuario.isActivo());
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                // Obtener el ID generado
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setId(rs.getInt(1));
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al crear usuario: " + usuario.getUsername());
            e.printStackTrace();
            throw new RuntimeException("Error al crear usuario", e);
        }
        
        return usuario;
    }
    
    @Override
    public void actualizar(Usuario usuario) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword()); // TODO: Hashear si cambió
            ps.setString(3, usuario.getNombreCompleto());
            ps.setString(4, usuario.getRol().name());  // Convertir Enum a String
            ps.setBoolean(5, usuario.isActivo());
            ps.setInt(6, usuario.getId());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario ID: " + usuario.getId());
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar usuario", e);
        }
    }
    
    @Override
    public void eliminar(int id) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario ID: " + id);
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar usuario", e);
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
            System.err.println("Error al desactivar usuario ID: " + id);
            e.printStackTrace();
            throw new RuntimeException("Error al desactivar usuario", e);
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
            System.err.println("Error al activar usuario ID: " + id);
            e.printStackTrace();
            throw new RuntimeException("Error al activar usuario", e);
        }
    }
}