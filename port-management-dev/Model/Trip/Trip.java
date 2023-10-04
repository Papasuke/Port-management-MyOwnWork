package Model.Trip;

import java.util.Date;

public class Trip {
    private String tripId;
    private String vehicleId;
    private String beginningPortId;
    private String destinationPortId;
    private Date departureDate;
    private Date arrivalDate;
    private String status;

    public Trip(String tripId, String vehicleId, String beginningPortId, String destinationPortId, Date departureDate, Date arrivalDate, String status) {
        this.tripId = tripId;
        this.vehicleId = vehicleId;
        this.beginningPortId = beginningPortId;
        this.destinationPortId = destinationPortId;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.status = status;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getBeginningPortId() {
        return beginningPortId;
    }

    public void setBeginningPortId(String beginningPortId) {
        this.beginningPortId = beginningPortId;
    }

    public String getDestinationPortId() {
        return destinationPortId;
    }

    public void setDestinationPortId(String destinationPortId) {
        this.destinationPortId = destinationPortId;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
