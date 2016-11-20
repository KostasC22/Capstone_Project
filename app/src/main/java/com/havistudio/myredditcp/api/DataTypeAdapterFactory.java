package com.havistudio.myredditcp.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DataTypeAdapterFactory extends CustomizedTypeAdapterFactory<Data> {
	public DataTypeAdapterFactory() {
		super(Data.class);
	}

	@Override
	protected void beforeWrite(Data source, JsonElement toSerialize) {
		JsonObject custom = toSerialize.getAsJsonObject().get("replies").getAsJsonObject();
		custom.add("replies", new JsonObject());
	}

	@Override
	protected void afterRead(JsonElement deserialized) {
		JsonObject custom = deserialized.getAsJsonObject();
		if(custom.has("replies")){
			if(custom.get("replies").isJsonPrimitive()){
				
				custom.remove("replies");
			} else {
				
			}			
		}
		if(custom.has("edited")){
			if(custom.get("edited").isJsonPrimitive()){
				custom.remove("edited");
			}
		}
	}
}
