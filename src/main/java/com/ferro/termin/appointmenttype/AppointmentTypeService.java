package com.ferro.termin.appointmenttype;

import org.springframework.stereotype.Service;

@Service
public class AppointmentTypeService {
    private AppointmentTypeRepository appointmentTypeRepository;

    public AppointmentTypeService(AppointmentTypeRepository appointmentTypeRepository) {
        this.appointmentTypeRepository = appointmentTypeRepository;
    }

    public AppointmentType findAppointmentTypeAppointmentTypeById(int id) {
        var appointmentType = appointmentTypeRepository.findById(id).orElse(null);
        if (appointmentType == null) {
            throw new AppointmentTypeNotFoundException("id: " + id);

        }
        return appointmentType;
    }
}
