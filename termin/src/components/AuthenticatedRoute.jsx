import { useAuth } from "./security/AuthContext";
import React from 'react'
import { Navigate } from "react-router-dom";

export default function AuthenticatedRoute({ children }) {
  const { isAuthenticated } = useAuth();

  if (isAuthenticated) {
    return children;
  }
  return <Navigate to="/" />;
}
