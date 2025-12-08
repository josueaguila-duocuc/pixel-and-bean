package cl.josueaguila.pnb.service;

import cl.josueaguila.pnb.model.Venta;
import cl.josueaguila.pnb.repository.IVentaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de lógica de negocio para Venta
 */
public class VentaService {
    
    private final IVentaRepository repository;
    
    public VentaService(IVentaRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Registra una nueva venta
     */
    public int registrarVenta(int usuarioId, String usuarioNombre, double total) {
        // Validaciones
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("Usuario inválido");
        }
        if (total <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a 0");
        }
        
        // Crear venta
        Venta venta = new Venta();
        venta.setFechaHora(LocalDateTime.now());
        venta.setUsuarioId(usuarioId);
        venta.setUsuarioNombre(usuarioNombre);
        venta.setTotal(total);
        venta.setEstado("ACTIVA");
        
        return repository.guardar(venta);
    }
    
    /**
     * Anula una venta
     */
    public void anular(int id) {
        Venta venta = repository.buscarPorId(id);
        if (venta == null) {
            throw new RuntimeException("Venta no encontrada");
        }
        
        if ("ANULADA".equals(venta.getEstado())) {
            throw new RuntimeException("La venta ya está anulada");
        }
        
        repository.anular(id);
    }
    
    /**
     * Lista todas las ventas
     */
    public List<Venta> listarTodas() {
        return repository.listarTodas();
    }
    
    /**
     * Lista ventas del día
     */
    public List<Venta> listarDelDia() {
        return repository.listarDelDia();
    }
    
    /**
     * Lista ventas por rango de fechas
     */
    public List<Venta> listarPorRango(LocalDateTime desde, LocalDateTime hasta) {
        return repository.listarPorRangoFechas(desde, hasta);
    }
    
    /**
     * Calcula total del día
     */
    public double calcularTotalDelDia() {
        return repository.calcularTotalDelDia();
    }
    
    /**
     * Calcula total por rango
     */
    public double calcularTotalPorRango(LocalDateTime desde, LocalDateTime hasta) {
        return repository.calcularTotalPorRango(desde, hasta);
    }

    public List<Venta> listarPorFecha(LocalDate fecha) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Venta> listarVentasDelDia() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void registrar(Venta nueva) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}