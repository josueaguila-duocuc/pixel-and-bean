package cl.josueaguila.pnb.repository;

import cl.josueaguila.pnb.model.Venta;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Contrato de operaciones para acceso a datos de Venta
 */
public interface IVentaRepository {
    
    /**
     * Busca una venta por su ID
     */
    Venta buscarPorId(int id);
    
    /**
     * Lista todas las ventas
     */
    List<Venta> listarTodas();
    
    /**
     * Lista ventas por rango de fechas
     */
    List<Venta> listarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta);
    
    /**
     * Lista ventas del día actual
     */
    List<Venta> listarDelDia();
    
    /**
     * Lista ventas por usuario
     */
    List<Venta> listarPorUsuario(int usuarioId);
    
    /**
     * Guarda una nueva venta
     */
    int guardar(Venta venta);
    
    /**
     * Anula una venta
     */
    void anular(int id);
    
    /**
     * Calcula total de ventas por rango de fechas
     */
    double calcularTotalPorRango(LocalDateTime desde, LocalDateTime hasta);
    
    /**
     * Calcula total de ventas del día
     */
    double calcularTotalDelDia();
}