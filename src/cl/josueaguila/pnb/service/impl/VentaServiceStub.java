package cl.josueaguila.pnb.service.impl;

import cl.josueaguila.pnb.model.Venta;
import cl.josueaguila.pnb.service.VentaService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class VentaServiceStub implements VentaService {
    private List<Venta> ventas;
    private int nextId;
    
    public VentaServiceStub() {
        this.ventas = new ArrayList<>();
        this.nextId = 1;
        cargarDatosIniciales();
    }
    
    private void cargarDatosIniciales() {
        LocalDateTime ahora = LocalDateTime.now();
        
        ventas.add(new Venta(nextId++, ahora.minusHours(3), 1, "admin", 5000, "ACTIVA"));
        ventas.add(new Venta(nextId++, ahora.minusHours(2), 2, "operador", 7500, "ACTIVA"));
        ventas.add(new Venta(nextId++, ahora.minusHours(1), 1, "admin", 3000, "ACTIVA"));
        ventas.add(new Venta(nextId++, ahora.minusMinutes(30), 2, "operador", 4500, "ACTIVA"));
    }
    
    @Override
    public List<Venta> listarTodas() {
        return new ArrayList<>(ventas);
    }
    
    @Override
    public List<Venta> listarPorFecha(LocalDate fecha) {
        return ventas.stream()
            .filter(v -> v.getFechaHora().toLocalDate().equals(fecha))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Venta> listarVentasDelDia() {
        return listarPorFecha(LocalDate.now());
    }
    
    @Override
    public Venta buscarPorId(int id) {
        return ventas.stream()
            .filter(v -> v.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public Venta registrar(Venta venta) {
        venta.setId(nextId++);
        venta.setFechaHora(LocalDateTime.now());
        venta.setEstado("ACTIVA");
        ventas.add(venta);
        System.out.println("[STUB] Venta registrada: " + venta);
        return venta;
    }
    
    @Override
    public void anular(int id) {
        Venta venta = buscarPorId(id);
        if (venta != null) {
            venta.setEstado("ANULADA");
            System.out.println("[STUB] Venta anulada: " + id);
        }
    }
    
    @Override
    public double calcularTotalPorFecha(LocalDate fecha) {
        return listarPorFecha(fecha).stream()
            .filter(v -> "ACTIVA".equals(v.getEstado()))
            .mapToDouble(Venta::getTotal)
            .sum();
    }
}