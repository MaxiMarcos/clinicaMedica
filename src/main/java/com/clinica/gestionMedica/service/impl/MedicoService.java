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
                .orElseThrow(() -> new MedicoNoEncontradoException("Médico no encontrado con id: " + id));

        medico.setApellido(medicoRequest.getApellido());
        medico.setNombre(medicoRequest.getNombre());
        medico.setFecha_nacimiento(medicoRequest.getFecha_nacimiento());
        medico.setDni(medicoRequest.getDni());
        medico.setTelefono(medicoRequest.getTelefono());
        medico.setDireccion(medicoRequest.getDireccion());
        medico.setEmail(medicoRequest.getEmail());
        medico.setSueldo(medicoRequest.getSueldo());
        medico.setEspecializacion(medicoRequest.getEspecializacion());

        medicoRepo.save(medico);
        return medicoMapper.convertirMedicoAResponse(medico);
    }

    @Override
    public MedicoResponseDto traerMedico(Long id) {
        Medico medico = medicoRepo.findById(id)
                .orElseThrow(() -> new MedicoNoEncontradoException("Médico no encontrado con id: " + id));
        return medicoMapper.convertirMedicoAResponse(medico);
    }

    @Override
    public List<MedicoResponseDto> traerMedicos() {
        return medicoMapper.convertirAMedicosResponse(medicoRepo.findAll());
    }

    @Override
    public void eliminarMedico(Long id) {
        Medico medico = medicoRepo.findById(id)
                .orElseThrow(() -> new MedicoNoEncontradoException("Médico no encontrado con id: " + id));
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
