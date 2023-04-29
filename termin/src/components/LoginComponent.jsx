import {
  Box,
  Button,
  IconButton,
  InputAdornment,
  TextField,
} from "@mui/material";
import React, { useState } from "react";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import { ErrorMessage, Form, Formik } from "formik";
import { useAuth } from "./security/AuthContext";
import { Navigate, useNavigate } from "react-router-dom";

export default function LoginComponent() {
  const { login } = useAuth();

  const [showPassword, setShowPassword] = useState(false);
  const [showErrorMessage, setShowErrorMessage] = useState(false);
  const handleClickShowPassword = () => setShowPassword((show) => !show);
  const handleMouseDownPassword = (event) => {
    console.log(event);
    event.preventDefault();
  };
  const navigate = useNavigate();

  const initialValue = {
    email: "",
    password: "",
  };

  async function handleSubmit(values, props) {
    if (await login(values.email, values.password)) {
      setShowErrorMessage(false);
      navigate(`/`);
    } else {
      setShowErrorMessage(true);
    }
  }

  return (
    <Formik initialValues={initialValue} onSubmit={handleSubmit}>
      {(props) => {
        const { email, password } = props.values;
        return (
          <Form>
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
                alignItems: "center",
                "& > :not(style)": { m: 1, width: "25ch" },
              }}
            >
              <TextField
                name="email"
                label="Email"
                variant="standard"
                value={email}
                onChange={props.handleChange}
                onBlur={props.handleBlur}
                helperText={<ErrorMessage name="email" />}
                error={props.errors.email && props.touched.email}
                required
              />
              <TextField
                name="password"
                label="Password"
                variant="standard"
                value={password}
                type={showPassword ? "text" : "password"}
                onChange={props.handleChange}
                onBlur={props.handleBlur}
                helperText={<ErrorMessage name="password" />}
                error={props.errors.password && props.touched.password}
                required
                InputProps={{
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        aria-label="toggle password visibility"
                        onClick={handleClickShowPassword}
                        onMouseDown={handleMouseDownPassword}
                      >
                        {showPassword ? <VisibilityOff /> : <Visibility />}
                      </IconButton>
                    </InputAdornment>
                  ),
                }}
              />
              <Button
                variant="contained"
                type="submit"
                color="primary"
                fullWidth
              >
                Submit
              </Button>
            </Box>
          </Form>
        );
      }}
    </Formik>
  );
}
