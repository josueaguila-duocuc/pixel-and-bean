package cl.josueaguila.pnb.service;

import cl.josueaguila.pnb.model.Venta;
import java.time.LocalDate;
import java.util.List;


public interface VentaService {

    List<Venta> listarTodas();
    List<Venta> listarPorFecha(LocalDate fecha);
    List<Venta> listarVentasDelDia();
    Venta buscarPorId(int id);
    Venta registrar(Venta venta);
    void anular(int id);
    double calcularTotalPorFecha(LocalDate fecha);
}
