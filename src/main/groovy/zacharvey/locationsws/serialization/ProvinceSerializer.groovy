package zacharvey.locationsws.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import zacharvey.locationsws.domain.entities.Province

class ProvinceSerializer extends JsonSerializer<Province> {
    @Override
    void serialize(Province province, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject()

        jsonGenerator.writeStringField('id', province.refId)
        jsonGenerator.writeStringField('name', province.name)
        jsonGenerator.writeStringField('code', province.code)

        jsonGenerator.writeEndObject()
    }
}
