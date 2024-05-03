package ibf2024.assessment.paf.batch4.controllers;

import org.apache.catalina.connector.Response;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.core.model.Model;

import java.util.*;
import java.util.Map.Entry;

import ibf2024.assessment.paf.batch4.models.Beer;
import ibf2024.assessment.paf.batch4.models.Brewery;
import ibf2024.assessment.paf.batch4.models.Style;
import ibf2024.assessment.paf.batch4.repositories.BeerRepository;
import ibf2024.assessment.paf.batch4.repositories.OrderRepository;
import ibf2024.assessment.paf.batch4.services.BeerService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping
public class BeerController {

	@Autowired
	BeerRepository repo;

	@Autowired
	BeerService serv;

	@Autowired
	OrderRepository orderRepo;

	//TODO Task 2 - view 0
	@GetMapping()
	public ModelAndView landingPage() {
		ModelAndView mav = new ModelAndView("view0");
		List<Style> result = repo.getStyles();
		mav.addObject("styles", result);
		return mav;	
	}
	
	
	//TODO Task 3 - view 1
	@GetMapping("/beer/style/{id}")
	public ModelAndView getBeers(@PathVariable int id, @RequestParam(value = "styleName") String styleName) {
		ModelAndView mav = new ModelAndView("view1");
		mav.addObject("styleName", styleName);
		List<Beer> beers = repo.getBreweriesByBeer(id);
		mav.addObject("beers", beers);
		return mav;
	}
	

	//TODO Task 4 - view 2
	@GetMapping("/brewery/{bid}")
	public ModelAndView getMethodName(@PathVariable int bid, @RequestParam(value = "breweryName") String breweryName) {
		ModelAndView mav = new ModelAndView("view2");
		Brewery brewery = repo.getBeersFromBrewery(bid).orElse(new Brewery());
		mav.addObject("brewery", brewery);
		return mav;
	}
	
	
	//TODO Task 5 - view 2, place order
	@PostMapping("/brewery/{brid}/order")
	public ModelAndView placedOrder(@PathVariable int brid, @RequestBody MultiValueMap<String, String> form) {
		//TODO: process POST request
		ModelAndView mav = new ModelAndView("view3");
		String order = serv.placeOrder(brid, form);
		mav.addObject("orderID", order.substring(12,20));
		orderRepo.insertNewOrder(order);
		return mav;
	}
	
}
