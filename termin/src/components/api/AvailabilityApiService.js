import { apiClient } from "./ApiClient";

export const retrieveAllAvailabilityForEmailApi = (email) =>
  apiClient.get(`/availability/${email}`);

export const updateDayPlanForEmailApi = (email, dayIndex, dayPlan) =>
  apiClient.put(`/availability/${email}/${dayIndex}`, dayPlan);
