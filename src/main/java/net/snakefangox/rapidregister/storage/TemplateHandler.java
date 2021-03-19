package net.snakefangox.rapidregister.storage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import net.snakefangox.rapidregister.RapidRegister;

import net.minecraft.util.Identifier;

public class TemplateHandler {
	private static final String TEMPLATE_DIR = "templates/";
	public static final Map<String, String> loadedTemplates = new HashMap<>();

	public static String getProcessedTemplate(String url, Identifier identifier){
		return processTemplate(getOrLoadTemplate(url), identifier);
	}

	public static String getOrLoadTemplate(String url) {
		if (loadedTemplates.containsKey(url)) return loadedTemplates.get(url);
		InputStream template = Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE_DIR + url);
		if (template != null) {
			String sTemplate = readInputStream(template);
			loadedTemplates.put(url, sTemplate);
			return sTemplate;
		}
		return "";
	}

	// https://stackoverflow.com/questions/309424/how-do-i-read-convert-an-inputstream-into-a-string-in-java
	private static String readInputStream(InputStream inputStream) {
		try {
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			for (int length; (length = inputStream.read(buffer)) != -1; ) {
				result.write(buffer, 0, length);
			}
			return result.toString(StandardCharsets.UTF_8.name());
		} catch (IOException i) {
			RapidRegister.LOGGER.error("Could not load template: " + i.getLocalizedMessage());
			return "";
		}
	}

	public static String processTemplate(String template, Identifier identifier) {
		return template.replace("@namespace@", identifier.getNamespace())
				.replace("@path@", identifier.getPath());
	}
}
