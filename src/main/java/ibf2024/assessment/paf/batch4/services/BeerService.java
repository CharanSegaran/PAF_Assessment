package ibf2024.assessment.paf.batch4.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;

@Service
public class BeerService {

	// DO NOT CHANGE THE METHOD'S NAME OR THE RETURN TYPE OF THIS METHOD
	public String placeOrder(/* You can add any number parameters here */int brid, MultiValueMap<String, String> form) {
		// TODO: Task 5 
		String orderID = UUID.randomUUID().toString().substring(0,8);
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		List<List<String>> quantity = new LinkedList<>();

		for(Entry<String,List<String>> pair:form.entrySet()){
			quantity.add(pair.getValue());
		}

		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for(int i=0;i<quantity.get(0).size();i++){
			if(quantity.get(0).get(i) != ""){
				JsonObjectBuilder builder = Json.createObjectBuilder();
				builder.add("beerId", quantity.get(1).get(i));
				builder.add("quantity", quantity.get(0).get(i));
				arrayBuilder.add(builder.build());
			}
		}
		JsonArray jsonArray = arrayBuilder.build();

		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("orderID", orderID);
		builder.add("date", date.format(formatter).toString());
		builder.add("breweryID", brid);
		builder.add("orders", jsonArray);
		return builder.build().toString();
	}

}
