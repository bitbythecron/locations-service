package zacharvey.locationsws.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import zacharvey.locationsws.domain.entities.City

class CitySerializer extends JsonSerializer<City> {
    @Override
    void serialize(City city, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject()

        jsonGenerator.writeStringField('id', city.refId)
        jsonGenerator.writeStringField('name', city.name)

        jsonGenerator.writeEndObject()
    }
}
