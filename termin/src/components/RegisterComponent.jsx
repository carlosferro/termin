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
import * as yup from "yup";
import { createProviderApi } from "./api/ProviderApiService";
import { useNavigate } from "react-router-dom";

export default function RegisterComponent() {
  const PasswordRegEx =
    /^.*((?=.*[!@#$%^&*()\-_=+{};:,<.>]){1})(?=.*\d)((?=.*[a-z]){1})((?=.*[A-Z]){1}).*$/;

  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const handleClickShowPassword = () => setShowPassword((show) => !show);
  const handleClickShowConfirmPassword = () =>
    setShowConfirmPassword((show) => !show);
  const handleMouseDownPassword = (event) => {
    console.log(event);
    event.preventDefault();
  };

  const navigate = useNavigate();

  const initialValue = {
    name: "",
    lastName: "",
    email: "",
    password: "",
    confirmPassword: "",
  };

  const YupValidation = yup.object().shape({
    name: yup
      .string("Enter your name")
      .min(2, "Name should be of minimum 2 characters length")
      .required("Name is required"),
    lastName: yup
      .string("Enter your last name")
      .min(2, "Last name should be of minimum 2 characters length")
      .required("Last name is required"),
    email: yup
      .string()
      .email("Enter a Vaid Email")
      .required("Email is Required"),
    password: yup
      .string()
      .required("Enter Your Password")
      .matches(PasswordRegEx, "Uppercase Lowercase Special char Required")
      .min(8, "Password Should be minimum 8 character")
      .max(50, "Too long"),
    confirmPassword: yup
      .string()
      .oneOf([yup.ref("password"), null], "Passwords must match")
      .required("Confirm Password is Required"),
  });

  const handleSubmit = (values, props) => {
    const provider = {
      name: values.name,
      lastName: values.lastName,
      email: values.email,
      password: values.password,
    };
    console.log(provider);
    createProviderApi(provider)
      .then((response) => {
        console.log(response);
        navigate("/");
      })
      .catch((error) => console.log(error));

    console.log(props);
    props.resetForm();
  };

  return (
    <Formik
      initialValues={initialValue}
      validationSchema={YupValidation}
      onSubmit={handleSubmit}
    >
      {(props) => {
        const { name, lastName, email, password, confirmPassword } =
          props.values;
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
                name="name"
                label="First Name"
                variant="standard"
                value={name}
                onChange={props.handleChange}
                onBlur={props.handleBlur}
                helperText={<ErrorMessage name="name" />}
                error={props.errors.name && props.touched.name}
                required
              />
              <TextField
                name="lastName"
                label="Last Name"
                variant="standard"
                value={lastName}
                onChange={props.handleChange}
                onBlur={props.handleBlur}
                helperText={<ErrorMessage name="lastName" />}
                error={props.errors.lastName && props.touched.lastName}
                required
              />
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
              <TextField
                name="confirmPassword"
                label="Confirm Password"
                variant="standard"
                value={confirmPassword}
                type={showConfirmPassword ? "text" : "password"}
                onChange={props.handleChange}
                onBlur={props.handleBlur}
                helperText={<ErrorMessage name="confirmPassword" />}
                error={
                  props.errors.confirmPassword && props.touched.confirmPassword
                }
                required
                InputProps={{
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        aria-label="toggle password visibility"
                        onClick={handleClickShowConfirmPassword}
                        onMouseDown={handleMouseDownPassword}
                      >
                        {showConfirmPassword ? (
                          <VisibilityOff />
                        ) : (
                          <Visibility />
                        )}
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
