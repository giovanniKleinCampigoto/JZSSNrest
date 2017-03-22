package zombierest.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.giovanni.zombierest.model.Inventory;
import com.giovanni.zombierest.model.Survivor;
import com.giovanni.zombierest.resources.SurvivorResource;
import com.giovanni.zombierest.services.SurvivorService;
import com.giovanni.zombierest.util.JSONUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;

import jersey.repackaged.com.google.common.collect.Lists;

public class SurvivorResourceTest extends JerseyTest {

	/**
	 * Application standard configuration
	 */
	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(SurvivorResource.class);
	}

	/**
	 * Tests the POST request using a clinet
	 * 
	 * @throws JSONException
	 *             bad json
	 * @throws FileNotFoundException
	 *             self explanatory
	 * @throws IOException
	 *             something went wrong with I/O
	 * @throws ParseException
	 *             bad json
	 */
	@Test
	public void testCreate() throws JSONException, FileNotFoundException, IOException, ParseException {

		String path = "//home//giovanni//workspace//git//JZSSNrest//zombierest//json//test//survivorsTestCreate.json";

		JSONParser parser = new JSONParser();

		String json = parser.parse(new FileReader(path)).toString();

		Response output = target("/survivors").request().post(Entity.entity(json, MediaType.APPLICATION_JSON));

		assertEquals("Should return status 200", 200, output.getStatus());
		assertNotNull("Should return notification", output.getEntity());

		output = target("/survivors/" + String.valueOf(getSurvivorsList().get(0).getIdsurvivor())).request().delete();

	}

	/**
	 * Create a survivor,fetch it and delete it
	 * 
	 * @throws FileNotFoundException
	 *             self explanatory
	 * @throws IOException
	 *             something went wrong with I/O
	 * @throws ParseException
	 *             bad json
	 */
	@Test
	public void testFetchAll() throws FileNotFoundException, IOException, ParseException {
		String path = "//home//giovanni//workspace//git//JZSSNrest//zombierest//json//test//survivorsTestCreate.json";

		JSONParser parser = new JSONParser();

		String json = parser.parse(new FileReader(path)).toString();

		Response output = target("/survivors").request().post(Entity.entity(json, MediaType.APPLICATION_JSON));

		Response response = target("/survivors").request().get();
		assertEquals(200, response.getStatus());
		assertNotNull("Should return list", response.getEntity());

		output = target("/survivors/" + String.valueOf(getSurvivorsList().get(0).getIdsurvivor())).request().delete();

	}

	/**
	 * Creates a survivor, fetches it and delete it
	 * 
	 * @throws FileNotFoundException
	 *             self explanatory
	 * @throws IOException
	 *             something went wrong with I/O
	 * @throws ParseException
	 *             bad json
	 * 
	 */
	@Test
	public void testFetchById() throws FileNotFoundException, IOException, ParseException {

		String path = "//home//giovanni//workspace//git//JZSSNrest//zombierest//json//test//survivorsTestCreate.json";

		JSONParser parser = new JSONParser();

		String json = parser.parse(new FileReader(path)).toString();

		Response output = target("/survivors").request().post(Entity.entity(json, MediaType.APPLICATION_JSON));

		List<Survivor> list = getSurvivorsList();

		Response response = target("/survivors/" + String.valueOf(list.get(0).getIdsurvivor())).request().get();
		assertEquals(200, response.getStatus());
		assertNotNull("Should return notification", response.getEntity());

		output = target("/survivors/" + String.valueOf(getSurvivorsList().get(0).getIdsurvivor())).request().delete();
	}

	/**
	 * Creates a survivor, updates it and deletes it
	 * 
	 * @throws FileNotFoundException
	 *             self explanatory
	 * @throws IOException
	 *             something went wrong with I/O
	 * @throws ParseException
	 *             bad json
	 */
	@Test
	public void testUpdate() throws FileNotFoundException, IOException, ParseException {
		String pathCreate = "//home//giovanni//workspace//git//JZSSNrest//zombierest//json//test//survivorsTestCreate.json";

		JSONParser parser = new JSONParser();

		String jsonCreate = parser.parse(new FileReader(pathCreate)).toString();

		Response output = target("/survivors").request().post(Entity.entity(jsonCreate, MediaType.APPLICATION_JSON));

		String path = "//home//giovanni//workspace//git//JZSSNrest//zombierest//json//test//survivorsTestUpdate.json";

		String json = parser.parse(new FileReader(path)).toString();

		output = target("/survivors/" + String.valueOf(getSurvivorsList().get(0).getIdsurvivor())).request()
				.put(Entity.entity(json, MediaType.APPLICATION_JSON));
		assertEquals("Should return status 200", 200, output.getStatus());

		output = target("/survivors/" + String.valueOf(getSurvivorsList().get(0).getIdsurvivor())).request().delete();

	}

	/**
	 * Creates a survivor and deletes it
	 * 
	 * @throws FileNotFoundException
	 *             self explanatory
	 * @throws IOException
	 *             something went wrong with I/O
	 * @throws ParseException
	 *             bad json
	 */
	@Test
	public void testDelete() throws FileNotFoundException, IOException, ParseException {

		String pathCreate = "//home//giovanni//workspace//git//JZSSNrest//zombierest//json//test//survivorsTestCreate.json";

		JSONParser parser = new JSONParser();

		String jsonCreate = parser.parse(new FileReader(pathCreate)).toString();

		Response output = target("/survivors").request().post(Entity.entity(jsonCreate, MediaType.APPLICATION_JSON));

		output = target("/survivors/" + String.valueOf(getSurvivorsList().get(0).getIdsurvivor())).request().delete();
		assertEquals("Should return status 204", 204, output.getStatus());
	}

	/**
	 * Test the report infected function doing a PUT request in the endpoint
	 * Creates 4 survivors and make them report the last one, see if their inventory gets deleted
	 * @throws FileNotFoundException
	 *             self explanatory
	 * @throws IOException
	 *             something went wrong with I/O
	 * @throws ParseException
	 *             bad json
	 * 
	 * @throws UnsupportedEncodingException
	 *             wrong encoding for FileReader
	 * 
	 * @throws JSONException
	 *             bad json
	 */
	@Test
	public void testInfected()
			throws FileNotFoundException, UnsupportedEncodingException, IOException, ParseException, JSONException {
		JSONUtil ju = new JSONUtil();

		// Path to a empty file just because a put request can't be empty
		String path = "//home//giovanni//workspace//git//JZSSNrest//zombierest//json//test//putMethodInfected.json";
		JSONParser parser = new JSONParser();
		// Parses empty file...
		String json = parser.parse(new FileReader(path)).toString();

		// Create a list with all the survivors from the request
		List<Survivor> survivors = new ArrayList<>();

		String pathCreate = "//home//giovanni//workspace//git//JZSSNrest//zombierest//json//test//survivorsTestInfection.json";
		// Parses the json array from the file for creation of the survivors
		JSONArray jsonArray = ju.parseJsonArray(pathCreate);

		// Generates all survivors in a array from the survivors json file
		for (int i = 0; i < jsonArray.length(); i++) {
			Response output = target("/survivors").request()
					.post(Entity.entity(jsonArray.get(i).toString(), MediaType.APPLICATION_JSON));
		}

		// Make a put request to the endpoint
		// /survivors/survivorId/reportinfected/infectedId
		for (int i = 0; i < survivors.size() - 1; i++) {
			Response output = target("/survivors/" + String.valueOf(survivors.get(i).getIdsurvivor())
					+ "/reportinfected/" + String.valueOf(survivors.get(survivors.size() - 1).getIdsurvivor()))
							.request().put(Entity.entity(json, MediaType.APPLICATION_JSON));
			assertEquals("Should return status 200", 200, output.getStatus());
		}

		survivors.addAll(getSurvivorsList());

		// Deletes all the survivors from the request...
		for (int i = 0; i < survivors.size(); i++) {
			Response output = target("/survivors/" + String.valueOf(survivors.get(i).getIdsurvivor())).request()
					.delete();
		}

	}

	/**
	 * Creates 2 survivors and make them trade with a PUT request in the endpoint
	* @throws FileNotFoundException
	 *             self explanatory
	 * @throws IOException
	 *             something went wrong with I/O
	 * @throws ParseException
	 *             bad json
	 * 
	 * @throws UnsupportedEncodingException
	 *             wrong encoding for FileReader
	 * 
	 * @throws JSONException
	 *             bad json
	 */
	@Test
	public void testTrade()
			throws FileNotFoundException, UnsupportedEncodingException, IOException, ParseException, JSONException {
		JSONUtil ju = new JSONUtil();

		// Path to a empty file just because a put request can't be empty
		String pathPut = "//home//giovanni//workspace//git//JZSSNrest//zombierest//json//test//tradeSetup.json";
		String pathCreate = "//home//giovanni//workspace//git//JZSSNrest//zombierest//json//test//testTrade.json";
		JSONParser parser = new JSONParser();
		// Parses trade setup...
		String json = parser.parse(new FileReader(pathPut)).toString();

		// Create a list with all the survivors from the request
		List<Survivor> survivors = new ArrayList<>();

		// Parses the json array from the file for creation of the survivors
		JSONArray jsonArray = ju.parseJsonArray(pathCreate);

		// Generates all survivors in a array from the survivors json file
		for (int i = 0; i < jsonArray.length(); i++) {
			Response output = target("/survivors").request()
					.post(Entity.entity(jsonArray.get(i).toString(), MediaType.APPLICATION_JSON));
		}
		
		survivors.addAll(getSurvivorsList());
	
		// Make a put request to the endpoint
		// /survivors/survivorId/trade/traderId with the trade setup...
		Response output = target("/survivors/" + String.valueOf(survivors.get(0).getIdsurvivor()) + "/trade/"
				+ String.valueOf(survivors.get(1).getIdsurvivor())).request()
						.put(Entity.entity(json, MediaType.APPLICATION_JSON));

		assertEquals("Should return status 200", 200, output.getStatus());

		survivors.addAll(getSurvivorsList());

		// Deletes all the survivors from the request...
		for (int i = 0; i < survivors.size(); i++) {
			output = target("/survivors/" + String.valueOf(survivors.get(i).getIdsurvivor())).request().delete();
		}

	}

	/**
	 * Returns survivor list from the response
	 * 
	 * @return returns survivor list from the response
	 */
	private List<Survivor> getSurvivorsList() {
		List<Survivor> list = Client.create().resource("http://localhost:9998/survivors")
				.get(new GenericType<List<Survivor>>() {
				});

		Response.ok(list).build();
		return list;
	}

	/**
	 * Writes on a file to print out the test functions (only used for debugging :D)
	 */
	public void writeTestOnFile(String test) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");

		writer.println(test);
		writer.close();
	}

}
