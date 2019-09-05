package com.jcarias.git.converters;

import com.jcarias.git.CommitInfo;
import org.json.JSONArray;

import java.util.Collection;

public class CommitsInfoToJsonArray implements Converter<Collection<CommitInfo>, JSONArray> {
	@Override
	public JSONArray convert(Collection<CommitInfo> commits) {
		JSONArray array = new JSONArray();
		if (commits != null || !commits.isEmpty()) {
			CommitInfoJSONConverter converter = new CommitInfoJSONConverter();
			for (CommitInfo commit : commits) {
				array.put(converter.convert(commit));
			}
		}
		return array;
	}
}
