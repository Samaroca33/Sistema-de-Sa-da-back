package br.com.projetosigse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TelaFullController {

    private String codigoTelaCheia = "1234";

    @GetMapping("/tela-cheia/config")
    public ResponseEntity<Map<String, String>> consultarCodigo() {
        return ResponseEntity.ok(Map.of("codigo", codigoTelaCheia));
    }

    @PutMapping("/tela-cheia/config")
    public ResponseEntity<Map<String, String>> alterarCodigo(@RequestBody Map<String, String> body) {
        String novoCodigo = body.get("codigo");
        if (novoCodigo == null || novoCodigo.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Código inválido"));
        }
        codigoTelaCheia = novoCodigo.trim();
        return ResponseEntity.ok(Map.of("codigo", codigoTelaCheia));
    }

    @PostMapping("/tela-cheia/desbloquear")
    public ResponseEntity<Map<String, String>> desbloquear(@RequestBody Map<String, String> body) {
        String codigoInformado = body.get("codigo");
        if (codigoInformado == null || codigoInformado.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("status", "erro", "mensagem", "Código obrigatório"));
        }

        if (codigoTelaCheia.equals(codigoInformado)) {
            return ResponseEntity.ok(Map.of("status", "ok", "mensagem", "Tela cheia liberada"));
        }

        return ResponseEntity.status(403).body(Map.of("status", "erro", "mensagem", "Código incorreto"));
    }
}


