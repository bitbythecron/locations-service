package zacharvey.locationsws.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import zacharvey.locationsws.domain.entities.Country

class CountrySerializer extends JsonSerializer<Country> {
    @Override
    void serialize(Country country, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject()

        jsonGenerator.writeNumberField('id', country.id)
        jsonGenerator.writeStringField('name', country.name)
        jsonGenerator.writeStringField('code', country.code)

        jsonGenerator.writeEndObject()
    }
}
