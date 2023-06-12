package com.ferro.termin.appointment;

import com.ferro.termin.appointmenttype.AppointmentTypeNotFoundException;
import com.ferro.termin.appointmenttype.AppointmentTypeRepository;
import com.ferro.termin.appointmenttype.AppointmentTypeService;
import com.ferro.termin.availability.TimePeriod;
import com.ferro.termin.user.UserService;
import com.ferro.termin.user.customer.Customer;
import com.ferro.termin.user.provider.Provider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin
public class AppointmentResource {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentService appointmentService;
    private final AppointmentTypeRepository appointmentTypeRepository;
    private final UserService userService;
    private final AppointmentTypeService appointmentTypeService;

    public AppointmentResource(AppointmentRepository appointmentRepository,
                               AppointmentService appointmentService,
                               AppointmentTypeRepository appointmentTypeRepository, UserService userService, AppointmentTypeService appointmentTypeService) {
        super();
        this.appointmentRepository = appointmentRepository;
        this.appointmentService = appointmentService;
        this.appointmentTypeRepository = appointmentTypeRepository;
        this.userService = userService;
        this.appointmentTypeService = appointmentTypeService;
    }

    @GetMapping("/appointment/{email}")
    public List<Appointment> retriveAppointments(@PathVariable String email) {
        var provider = userService.findProviderByEmail(email);
        var appointments = appointmentRepository.findByProviderEquals((Provider) provider);
        return appointments;
    }

    @PostMapping("/appointment/{providerEmail}")
    public void createAppointment(@PathVariable String providerEmail, @RequestParam String customerEmail,
                                  @RequestParam LocalDateTime startDate, @RequestParam int appointmentTypeId) {
        var provider = userService.findProviderByEmail(providerEmail);
        var customer = userService.findCustomerByEmail(customerEmail);
        var appointmentType = appointmentTypeService.findAppointmentTypeAppointmentTypeById(appointmentTypeId);
        var appointment = new Appointment(startDate, AppointmentStatus.SCHEDULED, (Provider) provider, (Customer) customer, appointmentType);
        appointmentRepository.save(appointment);
    }

    @GetMapping("/appointment/available-slots/{email}/{appointmentTypeId}/{date}")
    public List<TimePeriod> retriveAvailableSlotsAtDate(@PathVariable String email,
                                                        @PathVariable int appointmentTypeId,
                                                        @PathVariable LocalDate date) {
        var provider = (Provider) userService.findProviderByEmail(email);
        var appointmentType = appointmentTypeRepository.findById(appointmentTypeId).orElse(null);
        if (appointmentType == null)
            throw new AppointmentTypeNotFoundException("appointmentTypeId: " + appointmentTypeId);
        return appointmentService.getSlotsAtDay(provider, appointmentType, date);
    }
}
