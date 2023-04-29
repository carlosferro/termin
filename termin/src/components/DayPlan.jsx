import {
  Box,
  Button,
  Divider,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  Typography,
} from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import DeleteIcon from "@mui/icons-material/Delete";
import SaveIcon from "@mui/icons-material/Save";
import React, { useState } from "react";
import { Form, Formik } from "formik";
import { useAuth } from "./security/AuthContext";
import { updateDayPlanForEmailApi } from "./api/AvailabilityApiService";

const ITEM_HEIGHT = 30;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5,
      width: 50,
    },
  },
};

let timesList = [];

const daysOfWeek = [
  "Monday",
  "Tuesday",
  "Wednsday",
  "Thursday",
  "Friday",
  "Saturday",
  "Sunday",
];

for (let hour = 0; hour < 24; hour++) {
  for (let minute = 0; minute < 60; minute += 15) {
    let time = `${hour.toString().padStart(2, "0")}:${minute
      .toString()
      .padStart(2, "0")}`;
    timesList.push(time);
  }
}

export default function DayPlan({ dayIndex, periods }) {
  const authContext = useAuth();
  const email = authContext.email;

  const handleSubmit = (values, props) => {
    const dayPlan = {
      workingInterval: values.periods,
    };

    console.log(dayPlan);
    updateDayPlanForEmailApi(email, dayIndex, dayPlan)
      .then((response) => {
        console.log(response);
      })
      .catch((error) => console.log(error));

    console.log(props);
  };

  const initialValue = {
    initialPeriods: periods,
  };

  return (
    <>
      <Formik initialValues={initialValue} onSubmit={handleSubmit}>
        {(props) => {
          const { initialPeriods } = props.values;
          return (
            <Form>
              <Grid container justifyContent="center" spacing={2}>
                <Grid item sx={{ minWidth: 200 }}>
                  <Box display="flex" justifyContent="center">
                    {" "}
                    <Typography m={1.7} variant="h4" gutterBottom>
                      {daysOfWeek[dayIndex]}
                    </Typography>
                  </Box>

                  <Box display="flex" justifyContent="center">
                    <Button>
                      <AddIcon
                        onClick={() => {
                          props.setFieldValue("initialPeriods", [
                            ...initialPeriods,
                            { startTime: "15:00", endTime: "17:00" },
                          ]);
                        }}
                      />
                    </Button>
                    <Button type="submit">
                      <SaveIcon />
                    </Button>
                  </Box>
                </Grid>
                <Grid item>
                  {initialPeriods.map((period, index) => (
                    <Grid
                      key={index}
                      container
                      justifyContent="center"
                      item
                      mt={2}
                    >
                      <FormControl size="small" sx={{ mb: 1 }}>
                        <InputLabel id={`start-${index}`}>Start</InputLabel>
                        <Select
                          labelId={`start-${index}`}
                          id={`select-start-${index}`}
                          name={`periods[${index}].startTime`}
                          value={period.startTime}
                          label="Start"
                          onChange={props.handleChange}
                          onBlur={props.handleBlur}
                          MenuProps={MenuProps}
                        >
                          {timesList.map((item, index) => (
                            <MenuItem key={index} value={item}>
                              {item}
                            </MenuItem>
                          ))}
                        </Select>
                      </FormControl>
                      <FormControl size="small">
                        <InputLabel id={`end-${index}`}>End</InputLabel>
                        <Select
                          labelId={`end-${index}`}
                          id={`select-end-${index}`}
                          name={`periods[${index}].endTime`}
                          value={period.endTime}
                          label="End"
                          onChange={props.handleChange}
                          onBlur={props.handleBlur}
                          MenuProps={MenuProps}
                        >
                          {timesList.map((item, index) => (
                            <MenuItem key={index} value={item}>
                              {item}
                            </MenuItem>
                          ))}
                        </Select>
                      </FormControl>
                      <Button
                        onClick={() => {
                          const newPeriods = initialPeriods.filter(
                            (period, i) => i !== index
                          );
                          props.setFieldValue("periods", newPeriods);
                        }}
                      >
                        <DeleteIcon />
                      </Button>
                    </Grid>
                  ))}
                </Grid>
              </Grid>
            </Form>
          );
        }}
      </Formik>
    </>
  );
}
