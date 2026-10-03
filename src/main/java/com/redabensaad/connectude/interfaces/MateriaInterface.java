package com.redabensaad.connectude.interfaces;

import com.redabensaad.connectude.controllers.dtos.requests.RequestMateriaDTO;
import com.redabensaad.connectude.controllers.dtos.responses.ResponseMateriaDTO;
import com.redabensaad.connectude.datasource.entities.MateriaEntity;

import java.util.List;

public interface MateriaInterface {

    MateriaEntity criarMateria(RequestMateriaDTO requestMateriaDTO);

    MateriaEntity criarMateriaEntity(RequestMateriaDTO requestMateriaDTO);

    ResponseMateriaDTO criarResponseMateriaDTO(MateriaEntity materiaEntitySalvo);

    List<ResponseMateriaDTO> criarResponseMateriaDTO(List<MateriaEntity> materiaEntityList);

    List<MateriaEntity> listarMaterias();

    MateriaEntity buscarMateriaPorId(Long id);

    MateriaEntity atualizarMateria(Long id, RequestMateriaDTO requestMateriaDTO);

    Void deletarMateria(Long id);

    List<MateriaEntity> listarMateriasPorCurso(Long idCurso);
}
