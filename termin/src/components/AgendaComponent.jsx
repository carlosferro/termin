import React, { useEffect, useState } from "react";
import DataPickerComponent from "./DataPickerComponent";
import { retrieveAppointmentTypesForEmail } from "./api/AppointmentTypeApiService";
import { useAuth } from "./security/AuthContext";
import dayjs from "dayjs";
import {
  Box,
  Button,
  Container,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  ToggleButton,
  ToggleButtonGroup,
} from "@mui/material";
import { Form, Formik } from "formik";
import { retriveAvailableSlotsAtDate } from "./api/AppointmentApiService";

const ITEM_HEIGHT = 30;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5,
      width: 250,
    },
  },
};

export default function AgendaComponent() {
  const [availabilityTypes, setAvailabilityTypes] = useState([]);
  const [selectedDate, setSelectedDate] = useState(dayjs());
  const [availabilityAtDate, setAvailabilityAtDate] = useState([]);
  const authContext = useAuth();
  const email = authContext.email;

  useEffect(() => {
    retrieveAppointmentTypesForEmail(email)
      .then((response) => {
        setAvailabilityTypes(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

  const initialValues = {
    availabilityType: "",
  };

  const handleChange = (e) => {
    const date = selectedDate.toISOString().split("T")[0];
    retriveAvailableSlotsAtDate(email, e.target.value, date)
      .then((response) => setAvailabilityAtDate(response.data))
      .catch((error) => console.log(error));
  };

  return (
    <>
      <Grid container justifyContent="center" spacing={2} mt={2}>
        <Grid item>
          <Box display="flex" justifyContent="center">
            <Formik initialValues={initialValues}>
              {(props) => {
                const { availabilityType } = props.values;
                return (
                  <Form>
                    <FormControl size="small">
                      <InputLabel id="select-appointmenttype-label">
                        Service
                      </InputLabel>
                      <Select
                        labelId="select-appointmenttype-label"
                        value={availabilityType}
                        label="Service"
                        id="select-appointmenttype"
                        name="availabilityType"
                        onChange={(e) => {
                          props.handleChange(e);
                          handleChange(e);
                        }}
                        onBlur={props.handleBlur}
                        MenuProps={MenuProps}
                        sx={{ minWidth: 120 }}
                      >
                        {availabilityTypes.map((item, index) => (
                          <MenuItem key={index} value={item.id}>
                            {item.name}
                          </MenuItem>
                        ))}
                      </Select>
                    </FormControl>
                  </Form>
                );
              }}
            </Formik>
          </Box>
          <DataPickerComponent
            selectedDate={selectedDate}
            setSelectedDate={setSelectedDate}
          />
        </Grid>
        <Grid item>
          <Box display="flex" flexDirection="column" justifyContent="center">
            {availabilityAtDate.map((period, index) => (
              <Button
                key={index}
                variant="outlined"
                sx={{ mb: 1 }}
                onClick={() => {
                  console.log(`${period.startTime} - ${period.endTime}`);
                }}
              >
                {period.startTime} - {period.endTime}
              </Button>
            ))}
          </Box>
        </Grid>
      </Grid>
    </>
  );
}
