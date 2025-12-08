package cl.josueaguila.pnb.controller;

import cl.josueaguila.pnb.model.Venta;
import cl.josueaguila.pnb.service.VentaService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador para operaciones de Venta
 */
public class VentaController {
    
    private final VentaService service;
    
    public VentaController(VentaService service) {
        this.service = service;
    }
    
    public int registrarVenta(int usuarioId, String usuarioNombre, double total) {
        return service.registrarVenta(usuarioId, usuarioNombre, total);
    }
    
    public void anular(int id) {
        service.anular(id);
    }
    
    public List<Venta> listarTodas() {
        return service.listarTodas();
    }
    
    public List<Venta> listarDelDia() {
        return service.listarDelDia();
    }
    
    public List<Venta> listarPorRango(LocalDateTime desde, LocalDateTime hasta) {
        return service.listarPorRango(desde, hasta);
    }
    
    public double calcularTotalDelDia() {
        return service.calcularTotalDelDia();
    }
    
    public double calcularTotalPorRango(LocalDateTime desde, LocalDateTime hasta) {
        return service.calcularTotalPorRango(desde, hasta);
    }
    
    public List<Venta> listarPorFecha(LocalDate fecha) {
        return service.listarPorFecha(fecha);
    }

    public List<Venta> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void cancelarVenta(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}