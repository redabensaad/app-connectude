package com.redabensaad.connectude.controllers;

import com.redabensaad.connectude.controllers.dtos.requests.RequestCadastroDTO;
import com.redabensaad.connectude.controllers.dtos.responses.ResponseCadastroDTO;
import com.redabensaad.connectude.datasource.entities.CadastroEntity;
import com.redabensaad.connectude.infra.exceptions.CadastroNotFoundException;
import com.redabensaad.connectude.infra.exceptions.DocenteNotFoundException;
import com.redabensaad.connectude.infra.exceptions.PapelNotFoundException;
import com.redabensaad.connectude.services.CadastroService;
import com.redabensaad.connectude.services.LoginService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("cadastro")
public class CadastroController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private CadastroService cadastroService;


    @PostMapping()
    public ResponseEntity<?> criarCadastro(@Valid @RequestBody RequestCadastroDTO requestCadastroDTO) {
        if (loginService.loadUserByUsername(requestCadastroDTO.login()) != null) {
            return ResponseEntity.badRequest().build();

        } else {
            try {
                log.info("POST /cadastro ---> Chamada para o método.");
                CadastroEntity cadastroEntitySalvo = cadastroService.criarCadastro(requestCadastroDTO);
                ResponseCadastroDTO responseCadastroDTO = cadastroService.criarResponseCadastroDTO(cadastroEntitySalvo);
                log.info("POST /cadastro ---> Sucesso.");
                return ResponseEntity.status(HttpStatus.CREATED).body(responseCadastroDTO);
            } catch (PapelNotFoundException e) {
                log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }
    }

    @GetMapping()
    public ResponseEntity<List<ResponseCadastroDTO>> listarCadastros() {
        log.info("GET /cadastro ---> Chamada para o método.");
        List<CadastroEntity> cadastroEntityList = cadastroService.listarCadastros();
        List<ResponseCadastroDTO> responseCadastroDTOsList = cadastroService.criarResponseCadastroList(cadastroEntityList);
        log.info("GET /cadastro ---> Sucesso.");
        return ResponseEntity.ok(responseCadastroDTOsList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCadastro(@PathVariable("id") Long id) {
        try {
            log.info("DELETE /cadastro/{} ---> Chamada para o método.", id);
            cadastroService.deletarCadastro(id);
            log.info("DELETE /cadastro ---> Sucesso.");
            return ResponseEntity.noContent().build();
        } catch (CadastroNotFoundException e) {
            log.error("STATUS 404 ---> Recurso não encontrado ---> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
