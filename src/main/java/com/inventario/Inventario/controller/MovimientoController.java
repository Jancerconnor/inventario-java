package com.inventario.Inventario.controller;

import com.inventario.Inventario.model.Movimiento;
import com.inventario.Inventario.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MovimientoController {

    private final MovimientoRepository movimientoRepository;

    @GetMapping
    public List<Movimiento> obtenerTodos() {
        return movimientoRepository.findAllByOrderByFechaDesc();
    }

    @DeleteMapping
    public void eliminarTodos() {
        movimientoRepository.deleteAll();
    }
}