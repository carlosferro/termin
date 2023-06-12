import { apiClient } from "./ApiClient";

export const retriveAvailableSlotsAtDate = (email, appointmentTypeId, date) =>
  apiClient.get(
    `/appointment/available-slots/${email}/${appointmentTypeId}/${date}`
  );

export const createAppointment = (
  providerEmail,
  customerEmail,
  startDate,
  appointmentTypeId
) =>
  apiClient.post(`/appointment/${providerEmail}`, {
    customerEmail,
    startDate,
    appointmentTypeId,
  });
