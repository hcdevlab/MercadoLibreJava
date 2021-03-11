package main_package;

import com.fasterxml.jackson.databind.ObjectMapper;
import json.CategoryJson;
import json.ResultJson;
import json.RootJson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainClass {
	/*
		Ejecutamos el método "main" para realizar las consultas
		y guardar la información obtenida en el archivo "log.txt".
		Podemos modificar aquí el "site" y el "sellerId".
	*/
	public static void main(String[] args) {
		String site = "MLA";
		String sellerId = "179571326";
		executeScript(site, sellerId);
	}

	public static void executeScript(String site, String sellerId) {
		try {
			String url = "https://api.mercadolibre.com/sites/" + site + "/search?seller_id=" + sellerId;
			URI jsonUrl = new URI(url);
			Document document = Jsoup.connect(jsonUrl.toString()).get();
			String json = document.body().text();
			int firstCurlyBracket = json.indexOf("{");
			int lastCurlyBracket = json.lastIndexOf("}");
			String jsonSliced = json.substring(firstCurlyBracket, lastCurlyBracket + 1);

			List<TargetObject> targetObjectList = new ArrayList<>();

			ObjectMapper objectMapper = new ObjectMapper();

			RootJson rootJson = objectMapper.readValue(jsonSliced, RootJson.class);

			for (ResultJson resultJson : rootJson.getResults()) {
				String id = resultJson.getId();
				String categoryId = resultJson.getCategoryId();
				String category = getCategoryName(categoryId);
				String name = resultJson.getTitle();

				TargetObject targetObject = new TargetObject(id, categoryId, category, name);
				targetObjectList.add(targetObject);
				System.out.println("Id: " + id + " | " +
													 "Nombre: " + name + " | " +
													 "CategoryId: " + categoryId + " | " +
													 "CategoryJson: " + category);
			}

			StringBuilder sb = new StringBuilder();
			for (TargetObject targetObject : targetObjectList) {
				sb.append(targetObject.getObjectId() + " || " +
									targetObject.getProductName() + " || " +
									targetObject.getCategoryId() + " || " +
									targetObject.getCategoryName() + "\n");
			}

			ByteArrayInputStream stream = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
			Files.write(Paths.get("log.txt"), stream.readAllBytes());

		} catch (MalformedURLException e) {
			System.out.println("Entra en MalformedURLException: " + e.getMessage());
			e.printStackTrace();
		} catch (ProtocolException e) {
			System.out.println("Entra en ProtocolException: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Entra en IOException: " + e.getMessage());
			e.printStackTrace();
		} catch (URISyntaxException e) {
			System.out.println("Entra en URISyntaxException: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static String getCategoryName(String categoryId) {
		String categoryName = "";
		try {
			String url = "https://api.mercadolibre.com/categories/" + categoryId;
			URI jsonUrl = new URI(url);

			Document document = Jsoup.connect(jsonUrl.toString()).get();

			String json = document.body().text();
			int firstCurlyBracket = json.indexOf("{");
			int lastCurlyBracket = json.lastIndexOf("}");
			String jsonSliced = json.substring(firstCurlyBracket, lastCurlyBracket + 1);

			ObjectMapper objectMapper = new ObjectMapper();

			CategoryJson categoryJson = objectMapper.readValue(jsonSliced, CategoryJson.class);
			categoryName = categoryJson.getName();
		} catch (MalformedURLException e) {
			System.out.println("Entra en MalformedURLException: " + e.getMessage());
			e.printStackTrace();
		} catch (ProtocolException e) {
			System.out.println("Entra en ProtocolException: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Entra en IOException: " + e.getMessage());
			e.printStackTrace();
		} catch (URISyntaxException e) {
			System.out.println("Entra en URISyntaxException: " + e.getMessage());
			e.printStackTrace();
		}
		return categoryName;
	}
}
