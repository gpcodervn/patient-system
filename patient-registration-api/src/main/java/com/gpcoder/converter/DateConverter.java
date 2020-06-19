package com.gpcoder.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The helper class for custom Jackson convert Date to Json and vice versa
 */
@Slf4j
public class DateConverter {

	private static final ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.<SimpleDateFormat>withInitial(() -> {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		return sdf;
	});

	private DateConverter() {
		super();
	}

	public static class Serialize extends JsonSerializer<Date> {

		@Override
		public void serialize(Date value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
			if (value == null) {
				jgen.writeNull();
			} else {
				jgen.writeString(sdf.get().format(value));
			}
		}
	}

	public static class Deserialize extends JsonDeserializer<Date> {

		@SneakyThrows
		@Override
		public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
			String dateAsString = jp.getText();
			if (StringUtils.isBlank(dateAsString)) {
				return null;
			} else {
				try {
					return new Date(sdf.get().parse(dateAsString).getTime());
				} catch (ParseException e) {
					log.debug("Can not parse date " + dateAsString);
					return null;
				}
			}
		}
	}
}