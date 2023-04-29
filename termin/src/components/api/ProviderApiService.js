import { apiClient } from "./ApiClient";

export const createProviderApi = (provider) =>
  apiClient.post("/providers", provider);
