import { apiClient } from "./ApiClient";

export const retrieveAppointmentTypesForEmail = (email) =>
  apiClient.get(`/appointment-type/${email}`);
