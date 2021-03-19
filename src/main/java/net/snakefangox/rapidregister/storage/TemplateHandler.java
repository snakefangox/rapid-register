package net.snakefangox.rapidregister.storage;

import net.minecraft.util.Identifier;
import net.snakefangox.rapidregister.RapidRegister;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TemplateHandler {
	private static final String TEMPLATE_DIR = "templates/";
	private static final Map<String, String> loadedTemplates = new HashMap<>();
	private static final Set<String> missingTemplates = new HashSet<>();

	public static String getProcessedTemplate(String url, Identifier identifier) {
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

	public static boolean doesTemplateExist(String url) {
		if (loadedTemplates.containsKey(url)) return true;
		if (missingTemplates.contains(url)) return false;
		String template = getOrLoadTemplate(url);
		boolean missing = template.isEmpty();
		if (missing) missingTemplates.add(template);
		return !missing;
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
