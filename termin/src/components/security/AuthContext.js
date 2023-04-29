import { createContext, useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiClient } from "../api/ApiClient";
import { executeJwtAuthenticationService } from "../api/AuthenticationApiService";

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export default function AuthProvider({ children }) {
  const [email, setEmail] = useState(localStorage.getItem("email"));
  const [token, setToken] = useState(localStorage.getItem("jwtToken"));
  const [isAuthenticated, setIsAuthenticated] = useState(
    token && email ? true : false
  );
  const navigate = useNavigate;
  async function login(email, password) {
    try {
      const response = await executeJwtAuthenticationService(email, password);

      console.log(response);
      if (response.status === 200) {
        const jwtToken = "Bearer " + response.data.token;

        setIsAuthenticated(true);
        setEmail(email);
        setToken(jwtToken);

        localStorage.setItem("jwtToken", jwtToken);
        localStorage.setItem("email", email);

        apiClient.interceptors.request.use((config) => {
          console.log("intercepting and adding a token");
          config.headers.Authorization = jwtToken;
          return config;
        });
        return true;
      } else {
        logout();
        return false;
      }
    } catch (error) {
      logout();
      return false;
    }
  }

  function logout() {
    setIsAuthenticated(false);
    setEmail(null);
    setToken(null);

    localStorage.removeItem("jwtToken");
    localStorage.removeItem("email");

    navigate("/");
  }

  return (
    <AuthContext.Provider
      value={{
        isAuthenticated,
        setIsAuthenticated,
        setEmail,
        setToken,
        login,
        logout,
        email,
        token,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}
