package com.redabensaad.connectude.interfaces;

import com.redabensaad.connectude.controllers.dtos.requests.RequestAlunoDTO;
import com.redabensaad.connectude.datasource.entities.AlunoEntity;
import com.redabensaad.connectude.datasource.entities.CadastroEntity;
import com.redabensaad.connectude.datasource.entities.NotaEntity;

import java.util.List;

public interface AlunoNotaInterface {


    List<NotaEntity> buscarNotasPorIdAluno(AlunoEntity alunoEntity);

    AlunoEntity buscarAlunoPorId(Long id);

    AlunoEntity criarAluno(RequestAlunoDTO requestAlunoDTO);

    CadastroEntity buscarCadastroPorLogin(String login);
}
