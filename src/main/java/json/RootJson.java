package json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import json.ResultJson;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RootJson {
	@JsonProperty("results")
	private List<ResultJson> results = null;

	public List<ResultJson> getResults() {
		return results;
	}

	public void setResults(List<ResultJson> results) {
		this.results = results;
	}
}
