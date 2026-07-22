package com.inventario.Inventario.service;

import com.inventario.Inventario.model.Movimiento;
import com.inventario.Inventario.model.Producto;
import com.inventario.Inventario.repository.MovimientoRepository;
import com.inventario.Inventario.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final MovimientoRepository movimientoRepository;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    public Producto sumar(Long id, Integer cantidad, String usuario) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setCantidad(producto.getCantidad() + cantidad);
        actualizarEstado(producto);
        productoRepository.save(producto);

        Movimiento m = new Movimiento();
        m.setProductoNombre(producto.getNombre());
        m.setTipo("SUMA");
        m.setCantidad(cantidad);
        m.setFecha(LocalDateTime.now());
        m.setUsuario(usuario);
        movimientoRepository.save(m);

        return producto;
    }

    public Producto retirar(Long id, Integer cantidad, String usuario) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        if (producto.getCantidad() < cantidad) {
            throw new RuntimeException("No hay suficiente stock");
        }
        producto.setCantidad(producto.getCantidad() - cantidad);
        actualizarEstado(producto);
        productoRepository.save(producto);

        Movimiento m = new Movimiento();
        m.setProductoNombre(producto.getNombre());
        m.setTipo("RETIRO");
        m.setCantidad(cantidad);
        m.setFecha(LocalDateTime.now());
        m.setUsuario(usuario);
        movimientoRepository.save(m);

        return producto;
    }

    private void actualizarEstado(Producto producto) {
        if (producto.getCantidad() == 0) {
            producto.setEstado("Agotado");
        } else if (producto.getCantidad() <= producto.getMinimo()) {
            producto.setEstado("Bajo Stock");
        } else {
            producto.setEstado("Disponible");
        }
    }
}