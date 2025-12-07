package cl.josueaguila.pnb.app;

import cl.josueaguila.pnb.controller.*;
import cl.josueaguila.pnb.repository.*;
import cl.josueaguila.pnb.repository.mock.*;
import cl.josueaguila.pnb.service.*;

/**
 * Contenedor de Inversión de Control (IoC) manual
 * Responsable de:
 * - Crear todas las instancias de repositorios, servicios y controladores
 * - Inyectar dependencias mediante constructores
 * - Proporcionar acceso centralizado a los componentes
 */
public class ApplicationContext {
    
    // Singleton
    private static ApplicationContext instance;
    
    // ============= REPOSITORIOS =============
    private IUsuarioRepository usuarioRepository;
    private IProductoRepository productoRepository;
    private IVentaRepository ventaRepository;
    
    // ============= SERVICIOS =============
    private UsuarioService usuarioService;
    private ProductoService productoService;
    private VentaService ventaService;
    
    // ============= CONTROLADORES =============
    private LoginController loginController;
    private UsuarioController usuarioController;
    private ProductoController productoController;
    private VentaController ventaController;
    
    /**
     * Constructor privado (Singleton)
     */
    private ApplicationContext() {
        inicializar();
    }
    
    /**
     * Obtiene la instancia única del contexto
     */
    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }
    
    /**
     * Inicializa todos los componentes con sus dependencias
     */
    private void inicializar() {
        System.out.println("🔧 Inicializando ApplicationContext...");
        
        // 1. Crear repositorios (sin dependencias)
        inicializarRepositorios();
        
        // 2. Crear servicios (inyectando repositorios)
        inicializarServicios();
        
        // 3. Crear controladores (inyectando servicios)
        inicializarControllers();
        
        System.out.println("✅ ApplicationContext inicializado correctamente");
    }
    
    /**
     * Paso 1: Inicializar repositorios
     * Por ahora usamos implementaciones Mock
     * En la Clase 4, cambiaremos a implementaciones JDBC
     */
    private void inicializarRepositorios() {
        System.out.println("  📦 Creando repositorios Mock...");
        
        // Crear instancias Mock
        usuarioRepository = new UsuarioRepositoryMock();
        productoRepository = new ProductoRepositoryMock();
        ventaRepository = new VentaRepositoryMock();
        
        System.out.println("  ✓ Repositorios creados");
    }
    
    /**
     * Paso 2: Inicializar servicios con inyección de dependencias
     */
    private void inicializarServicios() {
        System.out.println("  💼 Creando servicios e inyectando repositorios...");
        
        // Inyectar repositorios en servicios (DI por constructor)
        usuarioService = new UsuarioService(usuarioRepository);
        productoService = new ProductoService(productoRepository);
        ventaService = new VentaService(ventaRepository);
        
        System.out.println("  ✓ Servicios creados");
    }
    
    /**
     * Paso 3: Inicializar controladores con inyección de dependencias
     */
    private void inicializarControllers() {
        System.out.println("  🎮 Creando controladores e inyectando servicios...");
        
        // Inyectar servicios en controladores (DI por constructor)
        loginController = new LoginController(usuarioService);
        usuarioController = new UsuarioController(usuarioService);
        productoController = new ProductoController(productoService);
        ventaController = new VentaController(ventaService);
        
        System.out.println("  ✓ Controladores creados");
    }
    
    // ============= GETTERS PÚBLICOS =============
    
    /**
     * Obtiene el controlador de login
     */
    public LoginController getLoginController() {
        return loginController;
    }
    
    /**
     * Obtiene el controlador de usuarios
     */
    public UsuarioController getUsuarioController() {
        return usuarioController;
    }
    
    /**
     * Obtiene el controlador de productos
     */
    public ProductoController getProductoController() {
        return productoController;
    }
    
    /**
     * Obtiene el controlador de ventas
     */
    public VentaController getVentaController() {
        return ventaController;
    }
    
    /**
     * Reinicia el contexto (útil para testing)
     */
    public static void reset() {
        instance = null;
    }
}