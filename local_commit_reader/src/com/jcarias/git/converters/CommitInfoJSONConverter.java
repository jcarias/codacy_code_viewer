package com.jcarias.git.converters;

import com.jcarias.git.CommitInfo;
import org.json.JSONObject;

public class CommitInfoJSONConverter implements Converter<CommitInfo, JSONObject> {
	@Override
	public JSONObject convert(CommitInfo object) {
		if (object != null)
			return new JSONObject(object);
		else return null;
	}
}
