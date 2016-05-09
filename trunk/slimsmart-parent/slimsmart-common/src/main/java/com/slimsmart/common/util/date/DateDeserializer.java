package com.slimsmart.common.util.date;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateDeserializer  extends JsonDeserializer<Date>{ 
	@Override  
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {  
        String value = jp.getText();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        try {  
            return dateFormat.parse(value);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return null;  
    } 
}
