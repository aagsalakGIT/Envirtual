package ElasticSearch;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;

public class ObjectMapper {

	ObjectMapper mapper = new ObjectMapper();
	ProductDetails user = mapper.readValue(new File("user.json"), ProductDetails.class);
}
