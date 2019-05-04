import java.util.HashMap;

public class HttpRequestImpl implements HttpRequest {
	private HashMap<String, String> headers;
	private HashMap<String, String> bodyParameters;
	private String method;
	private String requestUrl;
	
	public HttpRequestImpl(String request) {
		this.headers = new HashMap<>();
		this.bodyParameters = new HashMap<>();
		this.parseRequest(request);
	}

	@Override
	public HashMap<String, String> getHeaders() {
		return this.headers;
	}

	@Override
	public HashMap<String, String> getBodyParameters() {
		return this.bodyParameters;
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	@Override
	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String getRequestUrl() {
		return this.requestUrl;
	}

	@Override
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	@Override
	public void addHeader(String header, String value) {
		this.headers.put(header, value);

	}

	@Override
	public void addBodyParameter(String parameter, String value) {
		this.bodyParameters.put(parameter, value);

	}

	@Override
	public boolean isResource() {
		return this.requestUrl.contains(".");
	}
	
	private void parseRequest(String request){
		String[] splitRequest = request.split(System.lineSeparator());
		
		String[] firstLineSplit = splitRequest[0].split("\\s+");
		this.method = firstLineSplit[0];
		this.requestUrl = firstLineSplit[1];
		
		this.parseHeaders(splitRequest);
		
		String lastLine = splitRequest[splitRequest.length - 1];
		
		if (!lastLine.isEmpty() && lastLine.contains("&")) {
			setParameters(lastLine);
		}
	}
	
	private void parseHeaders (String[] splitRequest) {
		for (int i = 1; i < splitRequest.length; i++) {
			String requestLine = splitRequest[i];
			
			if (requestLine.isEmpty()) {
				break;
			}
			
			String[] splitLine = requestLine.split(":", 2);			
			this.headers.put(splitLine[0].trim(), splitLine[1].trim());
		}
	}
	
	private void setParameters(String body) {
		String[] split = body.split("&");
		
		for (int i = 0; i < split.length; i ++) {
			String[] parameterSplit = split[i].split("=");
			this.bodyParameters.put(parameterSplit[0], parameterSplit[1]);
		}
			
	}
}
