import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import AppBarComponent from "./components/AppBarComponent";
import AuthenticatedRoute from "./components/AuthenticatedRoute";
import Availability from "./components/Availability";
import Home from "./components/Home";
import LoginComponent from "./components/LoginComponent";
import RegisterComponent from "./components/RegisterComponent";
import AuthProvider from "./components/security/AuthContext";
import AgendaComponent from "./components/AgendaComponent";

function App() {
  return (
    <div>
      <AuthProvider>
        <BrowserRouter>
          <AppBarComponent></AppBarComponent>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/home" element={<Home />} />
            <Route path="/availability" element={<Availability />} />

            {/* <Route
              path="/agenda"
              element={
                <AuthenticatedRoute>
                  <AgendaComponent />
                </AuthenticatedRoute>
              }
            /> */}
            <Route
              path="/agenda/:username"
              element={
                <AuthenticatedRoute>
                  <AgendaComponent />
                </AuthenticatedRoute>
              }
            />
            <Route path="/login" element={<LoginComponent />} />
            <Route path="/register" element={<RegisterComponent />} />
          </Routes>
        </BrowserRouter>
      </AuthProvider>
    </div>
  );
}

export default App;
