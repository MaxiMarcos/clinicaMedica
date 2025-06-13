package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.MedicoRequestDto;
import com.clinica.gestionMedica.dto.MedicoResponseDto;
import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import com.clinica.gestionMedica.excepciones.medico.MedicoNoEncontradoException;
import com.clinica.gestionMedica.excepciones.medico.MedicoYaExisteException;
import com.clinica.gestionMedica.mapper.MedicoMapper;
import com.clinica.gestionMedica.repository.MedicoRepository;
import com.clinica.gestionMedica.service.IMedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicoService implements IMedicoService {

    private final MedicoRepository medicoRepo;
    private final MedicoMapper medicoMapper;

    @Override
    public MedicoResponseDto crearMedico(MedicoRequestDto medicoRequest) {

        Medico medicoExiste = medicoRepo.findByDni(medicoRequest.getDni());
        if (medicoExiste != null) {
            throw new MedicoYaExisteException("Ya existe un médico creado con este DNI.");
        }
        Medico medico = medicoMapper.convertirRequestAMedico(medicoRequest);
        medicoRepo.save(medico);

        return medicoMapper.convertirMedicoAResponse(medico);
    }

    @Override
    public MedicoResponseDto editarMedico(Long id, MedicoRequestDto medicoRequest) {
        Medico medico = medicoRepo.findById(id)
                .orElseThrow(MedicoNoEncontradoException::new);

        if (medicoRequest.getApellido() != null) medico.setApellido(medicoRequest.getApellido());
        if (medicoRequest.getNombre() != null) medico.setNombre(medicoRequest.getNombre());
        if (medicoRequest.getFecha_nacimiento() != null) medico.setFecha_nacimiento(medicoRequest.getFecha_nacimiento());
        if (medicoRequest.getDni() != null) medico.setDni(medicoRequest.getDni());
        if (medicoRequest.getTelefono() != null) medico.setTelefono(medicoRequest.getTelefono());
        if (medicoRequest.getDireccion() != null) medico.setDireccion(medicoRequest.getDireccion());
        if (medicoRequest.getEmail() != null) medico.setEmail(medicoRequest.getEmail());
        if (medicoRequest.getSueldo() != null) medico.setSueldo(medicoRequest.getSueldo());
        if (medicoRequest.getEspecializacion() != null) medico.setEspecializacion(medicoRequest.getEspecializacion());

        medicoRepo.save(medico);
        return medicoMapper.convertirMedicoAResponse(medico);
    }

    @Override
    public MedicoResponseDto traerMedico(Long id) {
        Medico medico = medicoRepo.findById(id)
                .orElseThrow(MedicoNoEncontradoException::new);
        return medicoMapper.convertirMedicoAResponse(medico);
    }

    @Override
    public List<MedicoResponseDto> traerMedicos() {
        return medicoMapper.convertirAMedicosResponse(medicoRepo.findAll());
    }

    @Override
    public void eliminarMedico(Long id) {
        Medico medico = medicoRepo.findById(id)
                .orElseThrow(MedicoNoEncontradoException::new);
        medicoRepo.deleteById(medico.getId());
    }


    @Override
    public List<MedicoResponseDto> buscarPorEspecialidad(PrestacionTiposEnum especializacion) {
        List<Medico> medicos = medicoRepo.findByEspecializacion(especializacion);
        return medicoMapper.convertirAMedicosResponse(medicos);
    }

    @Override
    public List<MedicoResponseDto> buscarPorApellido(String apellido) {
        List<Medico> medicos = medicoRepo.findByApellido(apellido);
        return medicoMapper.convertirAMedicosResponse(medicos);
    }

}
