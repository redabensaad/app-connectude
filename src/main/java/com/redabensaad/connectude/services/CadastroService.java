package com.redabensaad.connectude.services;

import com.redabensaad.connectude.controllers.dtos.requests.RequestCadastroDTO;
import com.redabensaad.connectude.controllers.dtos.responses.ResponseCadastroDTO;
import com.redabensaad.connectude.datasource.entities.CadastroEntity;
import com.redabensaad.connectude.datasource.entities.PapelEntity;
import com.redabensaad.connectude.datasource.repositories.CadastroRepository;
import com.redabensaad.connectude.infra.exceptions.CursoNotFoundException;
import com.redabensaad.connectude.infra.exceptions.PapelNotFoundException;
import com.redabensaad.connectude.infra.exceptions.CadastroNotFoundException;
import com.redabensaad.connectude.interfaces.CadastroInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CadastroService implements CadastroInterface {

    private final CadastroRepository cadastroRepository;
    private final PapelService papelService;

    @Autowired
    public CadastroService(CadastroRepository cadastroRepository, PapelService papelService) {
        this.cadastroRepository = cadastroRepository;
        this.papelService = papelService;
    }

    @Override
    public CadastroEntity criarCadastro(RequestCadastroDTO requestCadastroDTO) {

        Optional<PapelEntity> optionalPapelEntity = papelService.buscarPapelPorId(requestCadastroDTO.idPapel());
        PapelEntity papelEntity = optionalPapelEntity.orElseThrow(
                () -> new PapelNotFoundException("Número de papel inválido: " + requestCadastroDTO.idPapel())
        );
        CadastroEntity cadastroEntityEncrypted = criarEntidadeCadastroEncrypted(requestCadastroDTO);

        return cadastroRepository.save(cadastroEntityEncrypted);
    }

    CadastroEntity criarEntidadeCadastroEncrypted(RequestCadastroDTO requestCadastroDTO) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(requestCadastroDTO.password());
        return new CadastroEntity(requestCadastroDTO.nome(), requestCadastroDTO.login(), encryptedPassword, requestCadastroDTO.idPapel());
    }

    public ResponseCadastroDTO criarResponseCadastroDTO(CadastroEntity cadastroEntitySalvo) {
        return new ResponseCadastroDTO(cadastroEntitySalvo.getId(), cadastroEntitySalvo.getNome(), cadastroEntitySalvo.getLogin(), cadastroEntitySalvo.getIdPapel());
    }

    public List<CadastroEntity> listarCadastros() {
        return cadastroRepository.findAll();
    }

    public List<ResponseCadastroDTO> criarResponseCadastroList(List<CadastroEntity> cadastroEntityList) {
        return cadastroEntityList.stream()
                .map(user -> new ResponseCadastroDTO(user.getId(), user.getNome(), user.getLogin(), user.getIdPapel()))
                .collect(Collectors.toList());    }

    @Override
    public Void deletarCadastro(Long id) {
        cadastroRepository.findById(id)
                .orElseThrow(() -> new CadastroNotFoundException("Id do Cadastro não encontrado para deletar: " + id));
        cadastroRepository.deleteById(id);
        return null;
    }

    public CadastroEntity buscarCadastroPorId(Long id) {
        return cadastroRepository.findById(id).orElseThrow(
                () -> new CadastroNotFoundException("Id do Cadastro não encontrado: " + id));
    }

}
