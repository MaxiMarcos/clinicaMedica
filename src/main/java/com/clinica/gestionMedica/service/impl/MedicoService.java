package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.enums.PrestacionTiposEnum;
import com.clinica.gestionMedica.excepciones.medico.MedicoYaExisteException;
import com.clinica.gestionMedica.repository.MedicoRepository;
import com.clinica.gestionMedica.service.IMedicoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService implements IMedicoService {

    private final MedicoRepository medicoRepo;

    public MedicoService(MedicoRepository medicoRepo) {
        this.medicoRepo = medicoRepo;
    }

    @Override
    public Medico crearMedico(Medico medico) {

        Medico medicoExiste = medicoRepo.findByDni(medico.getDni());
        if (medicoExiste != null) {
            throw new MedicoYaExisteException("Ya existe un médico creado con este DNI.");
        }
        return medicoRepo.save(medico);
    }

    @Override
    public Medico editarMedico(Long id, Medico medico) {
        Medico nuevoMedico = this.traerMedico(id);
        nuevoMedico.setApellido(medico.getApellido());
        nuevoMedico.setNombre(medico.getNombre());
        nuevoMedico.setFecha_nacimiento(medico.getFecha_nacimiento());
        nuevoMedico.setDni(medico.getDni());
        nuevoMedico.setTelefono(medico.getTelefono());
        nuevoMedico.setDireccion(medico.getDireccion());
        nuevoMedico.setEmail(medico.getEmail());
        nuevoMedico.setSueldo(medico.getSueldo());
        nuevoMedico.setEspecializacion(medico.getEspecializacion());

        return medicoRepo.save(nuevoMedico);

    }

    @Override
    public Medico traerMedico(Long id) {
        return medicoRepo.findById(id).orElse(null);
    }

    @Override
    public List<Medico> traerMedicos() {
        return medicoRepo.findAll();
    }

    @Override
    public void eliminarMedico(Long id) {
        medicoRepo.deleteById(id);
    }


    @Override
    public List<Medico> buscarPorEspecialidad(PrestacionTiposEnum especializacion) {
        return medicoRepo.findByEspecializacion(especializacion);
    }

    @Override
    public List<Medico> buscarPorApellido(String apellido) {
        return medicoRepo.findByApellido(apellido);
    }

}
