import { apiClient } from "./ApiClient";

export const retriveAvailableSlotsAtDate = (email, appointmentTypeId, date) =>
  apiClient.get(`/appointment/available-slots/${email}/${appointmentTypeId}/${date}`);
