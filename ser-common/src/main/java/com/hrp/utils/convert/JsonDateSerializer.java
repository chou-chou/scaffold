package com.hrp.utils.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JsonDateSerializer
 * 日期转换器
 * @author KVLT
 * @date 2017-04-07.
 */
public class JsonDateSerializer extends JsonSerializer<Date> {
    private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        String value = dateFormat.format(date);
        gen.writeString(value);
    }
}
