package com.apap.tutorial7.controller;

import java.sql.Date;
import java.util.List;

import com.apap.tutorial7.model.FlightModel;
import com.apap.tutorial7.model.PilotModel;
import com.apap.tutorial7.rest.Setting;
import com.apap.tutorial7.service.FlightService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * FlightController
 */
@RestController
@RequestMapping("/flight")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate restFlight() {
        return new RestTemplate();
    }

    @PostMapping(value = "/add")
    private FlightModel addFlight(@RequestBody FlightModel flight, PilotModel pilot) {
        System.out.println("asdasd");
        System.out.println(pilot.getLicenseNumber());
        return flightService.addFlight(flight);
    }

    @PutMapping(value = "/update/{flightId}")
    private String updateFlight(@PathVariable("flightId") long flightId,
            @RequestParam(value = "destination", required = false) String destination,
            @RequestParam(value = "origin", required = false) String origin,
            @RequestParam(value = "date", required = false) Date date) {
        FlightModel flight = flightService.getFlightById(flightId).get();
        if (flight.equals(null)) {
            return "Couldn't find your flight";
        }

        if (!destination.equals(null))
            flight.setDestination(destination);
        if (!origin.equals(null))
            flight.setOrigin(origin);
        if (!date.equals(null))
            flight.setTime(date);
        return "flight update success";
    }

    @GetMapping(value = "/view/{flightNumber}")
    private FlightModel getFlight(@PathVariable("flightNumber") String flightNumber) {
        FlightModel flight = flightService.getFlightDetailByFlightNumber(flightNumber).get();
        return flight;
    }

    @GetMapping(value = "/all")
    private List<FlightModel> getAllFlight() {
        return flightService.getAllFlight();
    }

    @DeleteMapping(value = "/{flightId}")
    private String deleteFlight(@PathVariable("flightId") long flightId) {
        FlightModel flight = flightService.getFlightById(flightId).get();
        flightService.deleteByFlightNumber(flight.getFlightNumber());
        return "flight has been deleted";
    }

    @GetMapping(value = "/airport")
    private String getAirport(@RequestParam("key") String key) {
        String path = Setting.AMEDOUS_API_URL + "&term=" + key;
        return restTemplate.getForEntity(path, String.class).getBody();
    }

    // @RequestMapping(value = "/flight/add/{licenseNumber}", method =
    // RequestMethod.GET)
    // private String add(@PathVariable(value = "licenseNumber") String
    // licenseNumber, Model model) {
    // PilotModel pilot =
    // pilotService.getPilotDetailByLicenseNumber(licenseNumber).get();
    // pilot.setListFlight(new ArrayList<FlightModel>() {
    // private ArrayList<FlightModel> init() {
    // this.add(new FlightModel());
    // return this;
    // }
    // }.init());

    // model.addAttribute("pilot", pilot);
    // return "add-flight";
    // }

    // @RequestMapping(value = "/flight/add/{licenseNumber}", method =
    // RequestMethod.POST, params = { "addRow" })
    // private String addRow(@ModelAttribute PilotModel pilot, Model model) {
    // pilot.getListFlight().add(new FlightModel());
    // model.addAttribute("pilot", pilot);
    // return "add-flight";
    // }

    // @RequestMapping(value = "/flight/add/{licenseNumber}", method =
    // RequestMethod.POST, params = { "removeRow" })
    // public String removeRow(@ModelAttribute PilotModel pilot, Model model,
    // HttpServletRequest req) {
    // Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
    // pilot.getListFlight().remove(rowId.intValue());

    // model.addAttribute("pilot", pilot);
    // return "add-flight";
    // }

    // @RequestMapping(value = "/flight/add/{licenseNumber}", method =
    // RequestMethod.POST, params = { "save" })
    // private String addFlightSubmit(@ModelAttribute PilotModel pilot) {
    // PilotModel archive =
    // pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber()).get();
    // for (FlightModel flight : pilot.getListFlight()) {
    // if (flight != null) {
    // flight.setPilot(archive);
    // flightService.addFlight(flight);
    // }
    // }
    // return "add";
    // }

    // @RequestMapping(value = "/flight/view", method = RequestMethod.GET)
    // private @ResponseBody FlightModel view(@RequestParam(value = "flightNumber")
    // String flightNumber, Model model) {
    // FlightModel archive =
    // flightService.getFlightDetailByFlightNumber(flightNumber).get();
    // return archive;
    // }

    // @RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
    // private String delete(@ModelAttribute PilotModel pilot, Model model) {
    // for (FlightModel flight : pilot.getListFlight()) {
    // flightService.deleteByFlightNumber(flight.getFlightNumber());
    // }
    // return "delete";
    // }

    // @RequestMapping(value = "/flight/update", method = RequestMethod.GET)
    // private String update(@RequestParam(value = "flightNumber") String
    // flightNumber, Model model) {
    // FlightModel archive =
    // flightService.getFlightDetailByFlightNumber(flightNumber).get();
    // model.addAttribute("flight", archive);
    // return "update-flight";
    // }

    // @RequestMapping(value = "/flight/update", method = RequestMethod.POST)
    // private @ResponseBody FlightModel updateFlightSubmit(@ModelAttribute
    // FlightModel flight, Model model) {
    // flightService.addFlight(flight);
    // return flight;
    // }
}