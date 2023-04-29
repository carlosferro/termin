package com.ferro.termin.availability;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ferro.termin.appointment.Appointment;

import jakarta.persistence.Embeddable;
import lombok.ToString;

@Embeddable
@ToString
public class TimePeriod implements Comparable<TimePeriod> {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime startTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime endTime;

	public TimePeriod() {
		super();
	}

	public TimePeriod(LocalTime startTime, LocalTime endTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	@Override
	public int compareTo(TimePeriod o) {
		return this.getStartTime().compareTo(o.getStartTime());
	}

	@Override
	public String toString() {
		return "TimePeriod [startTime=" + startTime + ", endTime=" + endTime + "]";
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public boolean overlaps(TimePeriod otherTimePeriod) {
		var startCompare = startTime.compareTo(otherTimePeriod.startTime);
		var endCompare = endTime.compareTo(otherTimePeriod.endTime);
		return ((startCompare == 0 || startCompare == 1)
				&& startTime.isBefore(otherTimePeriod.endTime))
				|| (endTime.isAfter(otherTimePeriod.startTime)
						&& (endCompare == -1 || endCompare == 0));
	}

	public boolean overlaps(Appointment appointment) {
		var start = appointment.getStartDate().toLocalTime();
		var end = appointment.getEndDate().toLocalTime();
		return overlaps(new TimePeriod(start, end));
	}
}
