import { useState } from "react";
import { FormControl, MenuItem, Select } from "@mui/material";

export default function AvailabilityForm() {
  const [periods, setPeriods] = useState([
    { start: "08:00", end: "" },
    { start: "", end: "" },
    { start: "", end: "" },
    // add more periods as needed
  ]);

  const handleChange = (event, periodIndex, field) => {
    const newPeriods = [...periods];
    newPeriods[periodIndex][field] = event.target.value;
    setPeriods(newPeriods);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // send the periods data to the server
  };

  return (
    <form onSubmit={handleSubmit}>
      {periods.map((period, index) => (
        <div key={index}>
          <FormControl>
            <Select
              value={period.start}
              onChange={(event) => handleChange(event, index, "start")}
            >
              <MenuItem value="08:00">08:00</MenuItem>
              <MenuItem value="09:00">09:00</MenuItem>
              {/* add more time options */}
            </Select>
          </FormControl>
          <FormControl>
            <Select
              value={period.end}
              onChange={(event) => handleChange(event, index, "end")}
            >
              <MenuItem value="12:00">12:00</MenuItem>
              <MenuItem value="13:00">13:00</MenuItem>
              {/* add more time options */}
            </Select>
          </FormControl>
        </div>
      ))}
      <button type="submit">Save</button>
    </form>
  );
}
