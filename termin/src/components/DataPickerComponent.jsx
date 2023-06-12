import React, { useEffect, useState } from "react";
import dayjs from "dayjs";
import TextField from "@mui/material/TextField";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { StaticDatePicker } from "@mui/x-date-pickers/StaticDatePicker";
import ConfirmAppointmentComponent from "./ConfirmAppointmentComponent";

export default function DataPickerComponent({
  selectedDate,
  setSelectedDate,
  availableSlotsAtDate,
}) {
  // const [selectedDate, setSelectedDate] = useState(dayjs());

  return (
    <div>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <StaticDatePicker
          displayStaticWrapperAs="desktop"
          value={selectedDate}
          disablePast
          shouldDisableDate={(dateParam) => {
            // console.log(dateParam.toISOString().split("T")[0]);
          }}
          onChange={(newValue) => {
            // console.log(newValue.$d.toISOString().split("T")[0]);
            setSelectedDate(newValue);
          }}
          // onViewChange={(view) => console.log(view)}
          renderInput={(params) => <TextField {...params} />}
        />
      </LocalizationProvider>
    </div>
  );
}
