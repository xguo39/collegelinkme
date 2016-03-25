package scraper;

import java.io.IOException;

import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.JsonDecoder;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecord;

public final class AvroUtils {
	private AvroUtils(){}
	
	public static <T extends SpecificRecord> T readSpecificRecord(String jsonFileName, Class<T> tClass) throws IOException {
		SpecificDatumReader<T> reader = new SpecificDatumReader<>(tClass);
		JsonDecoder decoder = DecoderFactory.get().jsonDecoder(ReflectData.get().getSchema(tClass),
				ClassLoader.getSystemResourceAsStream(jsonFileName));
		return reader.read(null, decoder);
	}

}
