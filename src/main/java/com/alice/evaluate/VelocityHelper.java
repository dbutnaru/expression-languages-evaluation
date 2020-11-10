package com.alice.evaluate;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

import java.io.StringWriter;
import java.util.Map;
import java.util.UUID;

public class VelocityHelper {

	private VelocityEngine velocityEngine;
	private StringResourceRepository resourceRepository;

	public VelocityHelper(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
		resourceRepository = (StringResourceRepository) velocityEngine
				.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);
	}

	public String evaluate(String templateString, Map<String, Object> keyValueParams) {
		String templateId = UUID.randomUUID().toString();
		StringWriter stringWriter = new StringWriter();
		try {
			resourceRepository.putStringResource(templateId, templateString);
			VelocityContext context = new VelocityContext(keyValueParams);
			velocityEngine.getTemplate(templateId).merge(context, stringWriter);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			resourceRepository.removeStringResource(templateId);
		}
		return stringWriter.toString();
	}
}
