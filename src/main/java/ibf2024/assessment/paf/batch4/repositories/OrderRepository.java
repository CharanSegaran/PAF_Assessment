package ibf2024.assessment.paf.batch4.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	// TODO: Task 5
	public void insertNewOrder(String order){
		mongoTemplate.insert(order, "orders");
	}
}
