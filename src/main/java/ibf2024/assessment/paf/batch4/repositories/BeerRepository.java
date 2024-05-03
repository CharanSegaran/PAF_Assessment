package ibf2024.assessment.paf.batch4.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf2024.assessment.paf.batch4.models.Beer;
import ibf2024.assessment.paf.batch4.models.Brewery;
import ibf2024.assessment.paf.batch4.models.Style;

@Repository
public class BeerRepository {

	@Autowired
	JdbcTemplate template;

	// DO NOT CHANGE THE SIGNATURE OF THIS METHOD
	public List<Style> getStyles() {
		// TODO: Task 2
		String query = """
			select s.id, s.style_name, count(beers.name) 
			as beer from styles s join beers on beers.style_id=s.id group by s.style_name,
			s.id order by beer desc, s.style_name asc;
				""";
		List<Style> result = new LinkedList<Style>();
		final SqlRowSet rs = template.queryForRowSet(query);
		while(rs.next()){
			Style style = new Style();
			style.setStyleId(rs.getInt("id"));
			style.setName(rs.getString("style_name"));
			style.setBeerCount(rs.getInt("beer"));
			result.add(style);
		}
		return result;
	}
		
	// DO NOT CHANGE THE METHOD'S NAME OR THE RETURN TYPE OF THIS METHOD
	public List<Beer> getBreweriesByBeer(/* You can add any number parameters here */ int id) {
		// TODO: Task 3
		String query = """
			select b.id as bid, b.brewery_id as bbid, b.name as bname, b.descript as bdescript,br.name as brew_name,
			s.style_name from beers b join styles s on s.id=b.style_id 
			join breweries br on br.id=b.brewery_id where s.id=? order by bname asc
				""";
		List<Beer> beers = new LinkedList<Beer>();
		final SqlRowSet rs = template.queryForRowSet(query, id);
		while(rs.next()){
			Beer beer = new Beer();
			beer.setBeerId(rs.getInt("bid"));
			beer.setBeerName(rs.getString("bname"));
			beer.setBeerDescription(rs.getString("bdescript"));
			beer.setBreweryId(rs.getInt("bbid"));
			beer.setBreweryName(rs.getString("brew_name"));
			beers.add(beer);
		}
		return beers;
	}

	// DO NOT CHANGE THE METHOD'S NAME OR THE RETURN TYPE OF THIS METHOD
	public Optional<Brewery> getBeersFromBrewery(/* You can add any number of parameters here */int bid) {
		// TODO: Task 4
		String query = """
			select br.id as brid, br.name as brname, br.descript as brdesc, br.address1 as braddress, br.phone as brphone,
			br.website as brwesbite, b.name as bname, b.descript as bdescription, b.id as bid
			from breweries br join beers b on br.id=b.brewery_id 
			where br.id=? order by b.name asc;
				""";

		final SqlRowSet rs = template.queryForRowSet(query, bid);
		Brewery brewery = new Brewery();
		if(rs.next() == false){
			return Optional.empty();
		}else{
			brewery.setBreweryId(rs.getInt("brid"));
			brewery.setAddress1(rs.getString("braddress"));
			brewery.setDescription(rs.getString("brdesc"));
			brewery.setName(rs.getString("brname"));
			brewery.setWebsite(rs.getString("brwesbite"));
			Beer beer = new Beer();
			beer.setBeerName(rs.getString("bname"));
			beer.setBeerDescription(rs.getString("bdescription"));
			brewery.addBeer(beer);
		}
		while(rs.next()){
			Beer beer = new Beer();
			beer.setBeerName(rs.getString("bname"));
			beer.setBeerDescription(rs.getString("bdescription"));
			beer.setBeerId(rs.getInt("bid"));
			brewery.addBeer(beer);
		}
		return Optional.of(brewery);
	}
}
