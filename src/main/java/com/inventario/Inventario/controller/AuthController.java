package com.inventario.Inventario.controller;

import com.inventario.Inventario.model.Usuario;
import com.inventario.Inventario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String usuario  = body.get("usuario");
        String password = body.get("password");

        Optional<Usuario> found = usuarioRepository.findByUsuario(usuario);

        if (found.isEmpty() || !found.get().getPassword().equals(password)) {
            return ResponseEntity.status(401).body(Map.of("error", "Usuario o contraseña incorrectos"));
        }

        return ResponseEntity.ok(Map.of("status", "ok", "usuario", usuario));
    }
}