import * as React from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";

export default function ConfirmAppointmentComponent({
  open,
  setOpen,
  selectedSlot,
  selectedDate,
  selectedAvailabilityType,
  availabilityTypes,
  confirmSlot,
}) {
  const handleClose = () => {
    setOpen(false);
  };
  const handleAgree = () => {
    confirmSlot();
  };

  return (
    <div>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {availabilityTypes && selectedAvailabilityType
            ? availabilityTypes[selectedAvailabilityType].name
            : ""}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Would you like to confirm the following appointment?
            <br />
            Date:{" "}
            {selectedDate ? selectedDate.$d.toISOString().split("T")[0] : ""}
            <br />
            Time: {selectedSlot ? selectedSlot.startTime : ""} -{" "}
            {selectedSlot ? selectedSlot.endTime : ""}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Disagree</Button>
          <Button onClick={handleAgree} autoFocus>
            Agree
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
