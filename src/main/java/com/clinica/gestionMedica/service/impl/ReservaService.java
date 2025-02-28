package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.PrestacionDto;
import com.clinica.gestionMedica.dto.PrestacionRequestDTO;
import com.clinica.gestionMedica.dto.ReservaDto;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.entity.Reserva;
import com.clinica.gestionMedica.enums.ObraSocialEnum;
import com.clinica.gestionMedica.enums.PresenciaEnum;
import com.clinica.gestionMedica.enums.PrestacionEstadoEnum;
import com.clinica.gestionMedica.enums.ReservaEstadoEnum;
import com.clinica.gestionMedica.mapper.ReservaMapper;
import com.clinica.gestionMedica.repository.PacienteRepository;
import com.clinica.gestionMedica.repository.PrestacionRepository;
import com.clinica.gestionMedica.repository.ReservaRepository;
import com.clinica.gestionMedica.service.IReservaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService implements IReservaService {

    // CAMBIAR LLAMADOS A REPOR POR SERVICE

    private final ReservaRepository reservaRepo;
    private final PrestacionService prestacionService;
    private final PrestacionRepository prestacionRepo;
    private final ReservaMapper reservaMapper;
    private final PacienteService pacienteService;

    public ReservaService(ReservaRepository reservaRepo, PacienteService pacienteService, PrestacionService prestacionService, PrestacionRepository prestacionRepo, ReservaMapper reservaMapper) {
        this.reservaRepo = reservaRepo;
        this.prestacionService = prestacionService;
        this.prestacionRepo = prestacionRepo;
        this.reservaMapper = reservaMapper;
        this.pacienteService = pacienteService;
    }

    @Override
    public Reserva crearReserva(Reserva reserva) {

        //Paciente paciente = pacienteRepo.findByDni(reserva.getPaciente().getDni());

        return reservaRepo.save(reserva);
    }

    // puedo cambiar a List<Long> para traer todas las q ameriten
    public ReservaDto agregarPrestacionEnReserva(PrestacionRequestDTO prestacionRequestDTO) {

        // Elijo y filtro por disponibilidad y especialidad
        // Elijo prestaciones por ID
        // creo una nueva reserva y le agrego las prestaciones
        // cambio las prestaciones a NO DISPONIBLE, reserva primeramente en PAGO PENDIENTE (luego manejar cuando sucede el cambio) y guardo

        List<Prestacion> prestacionesDisponibles = prestacionService.buscarPorEspecialidadDisponibilidad(prestacionRequestDTO);
        if (prestacionesDisponibles.isEmpty()) {
            throw new IllegalArgumentException("No hay prestaciones disponibles para reservar");
        }

        Reserva reserva = new Reserva();
        Paciente paciente = pacienteService.traerPaciente(prestacionRequestDTO.getPacienteId());
        if(paciente == null){
            throw new IllegalArgumentException("El paciente no existe");
        }

        reserva.setPaciente(paciente);
        reserva.setEstadoPago(ReservaEstadoEnum.PENDIENTE);

        // se mantiene el precio de cada prestacion solo si el paciente no tiene obra social
        if(paciente.getObraSocial() != ObraSocialEnum.NINGUNA){
            for(Prestacion p : prestacionesDisponibles){
                p.setPrecio(0);
            }
        }
        for (Prestacion p : prestacionesDisponibles) {
            p.setEstado(PrestacionEstadoEnum.NODISPONIBLE);
            p.setReserva(reserva);
        }

        reserva.setPrestaciones(prestacionesDisponibles);
        reservaRepo.save(reserva);

        prestacionRepo.saveAll(prestacionesDisponibles);
        ReservaDto reservaDto = reservaMapper.conversionADto(reserva);

        return reservaDto;

        // cambiar precio total reserva
        // agregar tipos de descuentos
    }

    @Override
    public Reserva editarReserva(Long id, Reserva reserva) {
        Reserva nuevaReserva = this.traerReserva(id);
        nuevaReserva.setEstadoPago(reserva.getEstadoPago());
        nuevaReserva.setPaciente(reserva.getPaciente());
        nuevaReserva.setPrecioTotal(reserva.getPrecioTotal());
        nuevaReserva.setEstadoPresencia(reserva.getEstadoPresencia());
        nuevaReserva.setPrestaciones(reserva.getPrestaciones());

        return reservaRepo.save(nuevaReserva);
    }

    @Override
    public Reserva traerReserva(Long id) {
        return reservaRepo.findById(id).orElse(null);
    }

   // @Override
   // public List<ReservaDto> traerHistorialPaciente(String dni) {

     //   List<Reserva> historial = reservaRepo.findByPaciente_Dni(dni);
     //   return reservaMapper.conversionAListaDto(historial);
   // }

    @Override
    public List<Reserva> traerReservas() {
        return reservaRepo.findAll();
    }

    @Override
    public void eliminarReserva(Long id) {
        reservaRepo.deleteById(id);
    }
}
