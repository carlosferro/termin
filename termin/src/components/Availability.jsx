import React, { useEffect, useState } from "react";
import { retrieveAllAvailabilityForEmailApi } from "./api/AvailabilityApiService";
import DayPlan from "./DayPlan";
import { useAuth } from "./security/AuthContext";

export default function Availability() {
  const [dayPlans, setDayPlans] = useState([]);
  const authContext = useAuth();
  const email = authContext.email;

  useEffect(() => {
    refreshDayPlans();
  }, []);

  function refreshDayPlans() {
    retrieveAllAvailabilityForEmailApi(email)
      .then((response) => {
        console.log(response.data.dayPlans);
        setDayPlans(response.data.dayPlans);
      })
      .catch((error) => console.log(error));
  }

  return dayPlans.map((dayPlan, index) => (
    <DayPlan
      key={dayPlan.id}
      dayIndex={index}
      periods={dayPlan.workingInterval}
    />
  ));
}
