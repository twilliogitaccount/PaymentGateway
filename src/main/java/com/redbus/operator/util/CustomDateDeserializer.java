package com.redbus.operator.util;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException{
        String date = jsonParser.getText();
        try{
            SimpleDateFormat dateFormat=new SimpleDateFormat(DATE_FORMAT);
            return dateFormat.parse(date);
        }catch (ParseException e){
            throw new RuntimeException(e);
        }

    }
}
