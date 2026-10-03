package com.redabensaad.connectude.services;

import com.redabensaad.connectude.controllers.dtos.requests.RequestNotaDTO;
import com.redabensaad.connectude.controllers.dtos.responses.ResponseNotaDTO;
import com.redabensaad.connectude.datasource.entities.*;
import com.redabensaad.connectude.datasource.repositories.*;
import com.redabensaad.connectude.infra.exceptions.AlunoNotFoundException;
import com.redabensaad.connectude.infra.exceptions.DocenteNotFoundException;
import com.redabensaad.connectude.infra.exceptions.MateriaNotFoundException;
import com.redabensaad.connectude.infra.exceptions.NotaNotFoundException;
import com.redabensaad.connectude.interfaces.AlunoNotaInterface;
import com.redabensaad.connectude.interfaces.NotaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotaService implements NotaInterface {


    private final NotaRepository notaRepository;
    private final DocenteService docenteService;
    private final MateriaService materiaService;
    private final AlunoService alunoService;

    @Autowired
    public NotaService(NotaRepository notaRepository,
                       AlunoService alunoService,
                       DocenteService docenteService,
                       MateriaService materiaService) {
        this.notaRepository = notaRepository;
        this.alunoService = alunoService;
        this.docenteService = docenteService;
        this.materiaService = materiaService;
    }

    @Override
    public NotaEntity criarNota(RequestNotaDTO requestNotaDTO) {

        AlunoEntity alunoEntity = alunoService.buscarAlunoPorId(requestNotaDTO.idAluno());
        DocenteEntity docenteEntity = docenteService.buscarDocentePorId(requestNotaDTO.idDocente());
        MateriaEntity materiaEntity = materiaService.buscarMateriaPorId(requestNotaDTO.idMateria());

        NotaEntity notaEntity = criarNotaEntity(requestNotaDTO);
        return notaRepository.save(notaEntity);
    }

    @Override
    public NotaEntity criarNotaEntity(RequestNotaDTO requestNotaDTO){
        NotaEntity notaEntity = new NotaEntity();

        notaEntity.setDataLancamento(requestNotaDTO.dataLancamento());
        notaEntity.setIdAluno(requestNotaDTO.idAluno());
        notaEntity.setIdDocente(requestNotaDTO.idDocente());
        notaEntity.setIdMateria(requestNotaDTO.idMateria());
        notaEntity.setValor(requestNotaDTO.valor());

        return notaEntity;
    }

    @Override
    public ResponseNotaDTO criarResponseNotaDTO(NotaEntity notaEntitySalvo){
        return new ResponseNotaDTO(
                notaEntitySalvo.getId(),
                notaEntitySalvo.getDataLancamento(),
                notaEntitySalvo.getIdAluno(),
                notaEntitySalvo.getIdDocente(),
                notaEntitySalvo.getIdMateria(),
                notaEntitySalvo.getValor()
        );
    }


    @Override
    public List<NotaEntity> listarNotas() {
         return notaRepository.findAll();
    }

    @Override
    public List<ResponseNotaDTO> criarResponseNotaDTO(List<NotaEntity> notaEntityList) {
        return notaEntityList.stream()
                .map(this::criarResponseNotaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotaEntity buscarNotaPorId(Long id) {
        return notaRepository.findById(id)
                .orElseThrow(() -> new NotaNotFoundException("Id da Nota não encontrado: " + id));
    }

    @Override
    public NotaEntity atualizarNota(Long id, RequestNotaDTO requestNotaDTO) {
        return notaRepository.findById(id)
                .map(nota -> {
                    nota.setDataLancamento(requestNotaDTO.dataLancamento());
                    nota.setIdAluno(requestNotaDTO.idAluno());
                    nota.setIdDocente(requestNotaDTO.idDocente());
                    nota.setIdMateria(requestNotaDTO.idMateria());
                    nota.setValor(requestNotaDTO.valor());
                    return notaRepository.save(nota);
                })
                .orElseThrow(() -> new NotaNotFoundException("Id da Nota não encontrado para atualizar: " + id));
    }

    @Override
    public Void deletarNota(Long id) {
        notaRepository.findById(id)
                .orElseThrow(() -> new NotaNotFoundException("Id da Nota não encontrado para deletar: " + id));
        notaRepository.deleteById(id);
        return null;
    }

    @Override
    public List<NotaEntity> buscarNotasPorIdAluno(Long idAluno) {
        List<NotaEntity> notaEntityList = notaRepository.findAllByIdAluno(idAluno);
        if (notaEntityList.isEmpty()) {
            throw new NotaNotFoundException("Notas do Id Aluno " + idAluno + " não lançadas.");
        }
        return notaEntityList;
    }

}
