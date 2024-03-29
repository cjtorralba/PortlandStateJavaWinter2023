package edu.pdx.cs410J.torral2;

import org.w3c.dom.NodeList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;


/**
 * The Flight class is used to describe a typical airline flight.
 * The Flight includes the 3-letter name of the departing airport, as well as the flight number, departure time and date.
 * It also includes information about the arriving airport, being the 3-letter code of the airport,
 * and the arrival time and date.
 *
 * @author Christian Torralba
 * @version 4.0
 * @since 1.0
 */
public class Flight extends AbstractFlight implements Comparable<Flight> {
    /**
     * Flight number of the flight.
     */
    private final int flightNumber;

    /**
     * 3-letter Airport code for departing flight.
     */
    private final String src;


    /**
     * Departure date, format MM/DD/YYYY
     * Month and day can be one or two digit, year will always be four digits.
     */
    private final String departDate;

    /**
     * Departure time, format HH:MM
     * Hour and minute may be one or two digits.
     */
    private final String departTime;


    /**
     * Proper format for Arrival date and time, using MM/dd/yy h:mm
     */
    private final Date arrivalDateAndTime;


    /**
     * Proper format for Departure date and time, using MM/dd/yy h:mm
     */
    private final Date departureDateAndTime;

    /**
     * 3-letter code for arrival airport.
     */

    private final String dest;


    /**
     * Date of arrival, format MM/DD/YYYY
     * Month and day can be one or two digit, year will always be four digits.
     */
    private final String arriveDate;

    /**
     * Arrival time, format HH:MM
     * Hour and minute may be one or two digits.
     */
    private final String arriveTime;


    /**
     * Creates a new Flight with the specified information
     *
     * @param flightNumber Flight number of flight, integer value
     * @param src          3-letter String for departing airport
     * @param departDate   Date of departure, as a String
     * @param departTime   Time of departure, as a String
     * @param dest         3-letter String for arrival airport
     * @param arriveDate   Date or arrival, as a String
     * @param arriveTime   Time or arrival, as a String
     */
    public Flight(int flightNumber, String src, String departDate, String departTime, String dest, String arriveDate, String arriveTime) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy h:mm aa");


        // Checking valid flight number
        if (flightNumber < 1 || flightNumber > 9999) {
            throw new IllegalArgumentException("Invalid flight number.");
        }

        // Checking source code
        if (AirportNames.getName(src) == null) {
            throw new IllegalArgumentException("Invalid airport source code.");
        }

        // Checking valid destination code
        if (AirportNames.getName(dest) == null) {
            throw new IllegalArgumentException("Invalid airport arrival code.");
        }


        // Checking valid Date and Time for both arrival and departure
        try {
            arrivalDateAndTime = df.parse(arriveDate + " " + arriveTime);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Incorrect arrival date or time format.");
        }

        try {
            departureDateAndTime = df.parse(departDate + " " + departTime);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Incorrect departure date or time format.");
        }


        // Ensuring that arrival date is not before departure date
        if (arrivalDateAndTime != null && arrivalDateAndTime.before(departureDateAndTime)) {
            System.out.println(arrivalDateAndTime + " " + departureDateAndTime);
            throw new IllegalArgumentException("Cannot have arrival date before departure date.");
        }


        this.flightNumber = flightNumber;

        this.src = src;
        this.dest = dest;

        this.departDate = departDate;
        this.departTime = departTime;

        this.arriveDate = arriveDate;
        this.arriveTime = arriveTime;
    }


    public String getFlightAsTextFileString() {
        return this.flightNumber + "|" + this.src + "|" + this.departDate + "|" + this.departTime + "|" + this.dest + "|" + this.arriveDate + "|" + this.arriveTime;
    }

    /**
     * @return The flight number for current flight
     */
    @Override
    public int getNumber() {
        return this.flightNumber;
    }

    /**
     * @return The airport code for the source airport
     */
    @Override
    public String getSource() {
        return this.src;
    }

    @Override
    public Date getArrival() {
        return this.arrivalDateAndTime;
    }


    @Override
    public Date getDeparture() {
        return this.departureDateAndTime;
    }

    /**
     * @return The departure date and time
     */
    @Override
    public String getDepartureString() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US).format(this.departureDateAndTime);
    }

    /**
     * @return Returns the airport code for the destination airport
     */
    @Override
    public String getDestination() {
        return this.dest;
    }


    /**
     * Returns the arrival date and time in String form with MM/dd/yy h:mm:ss format
     *
     * @return String The arrival date and time formatted together
     */
    @Override
    public String getArrivalString() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US).format(this.arrivalDateAndTime);
    }


    /**
     * This function compares two flights, flights with source codes that come first in the alphabet
     * have a higher value, if both flights have the same source, the flights with a earlier flight time
     * have higher priority.
     *
     * @param flight The flight we are comparing to.
     * @return int Value 0 if flights are equal
     */
    @Override
    public int compareTo(Flight flight) {
        int result = this.src.compareTo(flight.src);
        if (result == 0) { // The sources are equal
            return this.departureDateAndTime.compareTo(flight.departureDateAndTime);
        }
        return result;
    }

    static public Flight parseNodeXML(NodeList xmlFlightNodeInfo) {

        int flightNumber = 0;
        String src = "";
        String departTimeHour = "";
        String departTimeMinute = "";
        String departDateDay = "";
        String departDateMonth = "";
        String departDateYear = "";

        String dest = "";
        String arriveTimeHour = "";
        String arriveTimeMinute = "";
        String arriveDateDay = "";
        String arriveDateMonth = "";
        String arriveDateYear = "";

        for (int i = 0; i < xmlFlightNodeInfo.getLength(); ++i) {
            switch (xmlFlightNodeInfo.item(i).getNodeName()) {
                case "number":
                    flightNumber = Integer.parseInt(xmlFlightNodeInfo.item(i).getTextContent());
                    break;
                case "src":
                    src = xmlFlightNodeInfo.item(i).getTextContent();
                    break;
                case "depart":
                    NodeList departNodes = xmlFlightNodeInfo.item(i).getChildNodes();
                    for (int j = 0; j < departNodes.getLength(); ++j) {
                        if (departNodes.item(j) != null) {
                            switch (departNodes.item(j).getNodeName()) {
                                case "date":
                                    departDateDay = departNodes.item(j).getAttributes().getNamedItem("day").getTextContent();
                                    departDateMonth = departNodes.item(j).getAttributes().getNamedItem("month").getTextContent();
                                    departDateYear = departNodes.item(j).getAttributes().getNamedItem("year").getTextContent();
                                    break;
                                case "time":
                                    departTimeHour = departNodes.item(j).getAttributes().getNamedItem("hour").getTextContent();
                                    departTimeMinute = departNodes.item(j).getAttributes().getNamedItem("minute").getTextContent();
                                    if(departTimeMinute.equals("0")) {
                                        departTimeMinute += "0";
                                    }
                                    break;
                            }
                        }
                    }
                case "dest":
                    dest = xmlFlightNodeInfo.item(i).getTextContent();
                    break;
                case "arrive":
                    NodeList arriveNodes = xmlFlightNodeInfo.item(i).getChildNodes();
                    for (int j = 0; j < arriveNodes.getLength(); ++j) {
                        if (arriveNodes.item(j) != null) {
                            switch (arriveNodes.item(j).getNodeName()) {
                                case "date":
                                    arriveDateDay = arriveNodes.item(j).getAttributes().getNamedItem("day").getTextContent();
                                    arriveDateMonth = arriveNodes.item(j).getAttributes().getNamedItem("month").getTextContent();
                                    arriveDateYear = arriveNodes.item(j).getAttributes().getNamedItem("year").getTextContent();
                                    break;
                                case "time":
                                    arriveTimeHour = arriveNodes.item(j).getAttributes().getNamedItem("hour").getTextContent();
                                    arriveTimeMinute = arriveNodes.item(j).getAttributes().getNamedItem("minute").getTextContent();
                                    if(arriveTimeMinute.equals("0")) {
                                        arriveTimeMinute += "0";
                                    }
                                    break;
                            }
                        }
                    }
                    break;
            }
        }

        int parsedArriveTimeHour = Integer.parseInt(arriveTimeHour);
        System.err.println("Parsed arrive time hour: " + parsedArriveTimeHour);
        int parsedDepartTimeHour = Integer.parseInt(departTimeHour);
        System.err.println("Parsed Depart Time Hour " + parsedDepartTimeHour);

        String arriveDateString = arriveDateMonth + "/" + arriveDateDay + "/" + arriveDateYear;
        String arriveTimeString = (parsedArriveTimeHour > 12 ? parsedArriveTimeHour - 12 : parsedArriveTimeHour) + ":" + arriveTimeMinute + (parsedArriveTimeHour > 12 ? " pm" : " am");

        String departDateString = departDateMonth + "/" + departDateDay + "/" + departDateYear;
        String departTimeString = (parsedDepartTimeHour > 12 ? parsedDepartTimeHour - 12 : parsedDepartTimeHour) + ":" + departTimeMinute + (parsedDepartTimeHour > 12 ? " pm" : " am");

        System.err.println("Flight: " + flightNumber);
        System.err.println("ArriveDateString: " + arriveDateString);
        System.err.println("ArriveTimeString: " + arriveTimeString);
        System.err.println("DepartDateString: " + departDateString);
        System.err.println("DepartTimeString: " + departTimeString);
        System.err.println("\n");

        return new Flight(flightNumber, src, departDateString, departTimeString, dest, arriveDateString, arriveTimeString);
    }


}
