package com.inventario.Inventario.controller;

import com.inventario.Inventario.model.Producto;
import com.inventario.Inventario.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoService.obtenerTodos();
    }

    @PostMapping
    public Producto agregar(@RequestBody Producto producto) {
        producto.setEstado("Disponible");
        return productoService.guardar(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        producto.setId(id);
        return productoService.guardar(producto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.ok("Producto eliminado");
    }

    @PutMapping("/{id}/sumar")
    public Producto sumar(
            @PathVariable Long id,
            @RequestParam Integer cantidad,
            @RequestParam(required = false, defaultValue = "Sistema") String usuario) {
        return productoService.sumar(id, cantidad, usuario);
    }

    @PutMapping("/{id}/retirar")
    public Producto retirar(
            @PathVariable Long id,
            @RequestParam Integer cantidad,
            @RequestParam(required = false, defaultValue = "Sistema") String usuario) {
        return productoService.retirar(id, cantidad, usuario);
    }
}