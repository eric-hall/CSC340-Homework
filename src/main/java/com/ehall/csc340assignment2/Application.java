package com.ehall.csc340assignment2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

public class Application {

	private static final RestTemplate _httpHelper = new RestTemplate();

	public static void main(String[] args) {
		catFactsTest();
		numbersFactsTest();
	}

	private static JsonNode getAndParseJsonFrom(String url) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonRaw = _httpHelper.getForObject(url, String.class);
		try {
			return mapper.readTree(jsonRaw);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void catFactsTest() {

		String url = "https://cat-fact.herokuapp.com/facts/";
		System.out.println("***********************************************************");
		System.out.printf("Test 1: Random cat facts. %s\n", url);

		JsonNode root = getAndParseJsonFrom(url);
		for (JsonNode node : root) {
			String fact = node.findValue("text").asText();
			System.out.println(fact);
		}
	}

	public static void numbersFactsTest() {

		int min = 5;
		int max = 10;

		String url = "http://numbersapi.com/" + min + ".." + max;
		System.out.println("***********************************************************");
		System.out.printf("Test 2: Random number facts [%d, %d] %s\n", min, max, url);

		JsonNode root = getAndParseJsonFrom(url);
		for (int n = min; n <= max; ++n) {
			String text = root.findValue(""+n).asText();
			System.out.printf("%d: %s\n", n, text);
		}
	}
}
