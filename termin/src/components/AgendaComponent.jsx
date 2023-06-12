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
import {
  retriveAvailableSlotsAtDate,
  createAppointment,
} from "./api/AppointmentApiService";
import { useParams } from "react-router-dom";
import ConfirmAppointmentComponent from "./ConfirmAppointmentComponent";

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
  const { username } = useParams();
  const [availabilityTypes, setAvailabilityTypes] = useState([]);
  const [selectedAvailabilityType, setSelectedAvailabilityType] = useState("");
  const [selectedDate, setSelectedDate] = useState(dayjs());
  const [availabilityAtDate, setAvailabilityAtDate] = useState([]);
  const [selectedSlot, setSelectedSlot] = useState();
  const authContext = useAuth();
  const email = authContext.email;
  const [open, setOpen] = React.useState(false);

  useEffect(() => {
    retrieveAppointmentTypesForEmail(email)
      .then((response) => {
        setAvailabilityTypes(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

  useEffect(() => {
    availableSlotsAtDate();
  }, [selectedAvailabilityType, selectedDate]);

  const initialValues = {
    availabilityType: "",
  };

  const handleChange = (e) => {
    setSelectedAvailabilityType(e.target.value);
    // availableSlotsAtDate()
  };

  const availableSlotsAtDate = () => {
    if (selectedAvailabilityType != "") {
      const date = selectedDate.toISOString().split("T")[0];
      retriveAvailableSlotsAtDate(email, selectedAvailabilityType, date)
        .then((response) => setAvailabilityAtDate(response.data))
        .catch((error) => console.log(error));
    }
  };

  const confirmSlot = () => {
    console.log("test");
    console.log(email);
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
                  setSelectedSlot(availabilityAtDate[index]);
                  setOpen(true);
                  console.log(selectedSlot);
                }}
              >
                {period.startTime} - {period.endTime}
              </Button>
            ))}
          </Box>
        </Grid>
      </Grid>
      <ConfirmAppointmentComponent
        open={open}
        setOpen={setOpen}
        selectedSlot={selectedSlot}
        selectedDate={selectedDate}
        selectedAvailabilityType={selectedAvailabilityType}
        availabilityTypes={availabilityTypes}
        confirmSlot={confirmSlot}
      ></ConfirmAppointmentComponent>
    </>
  );
}
