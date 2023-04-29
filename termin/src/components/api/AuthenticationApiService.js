import { apiClient } from "./ApiClient";

export const executeJwtAuthenticationService = (email, password) => 
    apiClient.post(`/authenticate`, {
    email,
    password,
  })
  ;
