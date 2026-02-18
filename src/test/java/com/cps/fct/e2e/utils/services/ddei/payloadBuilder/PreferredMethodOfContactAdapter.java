package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import com.cps.fct.e2e.enums.PreferredMethodOfContact;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class PreferredMethodOfContactAdapter extends TypeAdapter<PreferredMethodOfContact> {

    @Override
    public void write(JsonWriter out, PreferredMethodOfContact value) throws IOException {
        out.value(value.getValue());
    }

    @Override
    public PreferredMethodOfContact read(JsonReader in) throws IOException {
        int intValue = in.nextInt();
        return PreferredMethodOfContact.fromValue(intValue);
    }

}

