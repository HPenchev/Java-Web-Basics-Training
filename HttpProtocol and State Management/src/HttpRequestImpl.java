import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class HttpRequestImpl implements HttpRequest {
	private HashMap<String, String> headers;
	private HashMap<String, String> bodyParameters;
	private String method;
	private String requestUrl;
	private Collection<HttpCookie> cookies;
	
	public HttpRequestImpl(String request) {
		this.headers = new HashMap<>();
		this.bodyParameters = new HashMap<>();
		this.cookies = new HashSet<>();
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
	
	public Collection<HttpCookie> getCookies() {
		return cookies;
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
		
		setCookies(splitRequest);
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
	
	private void setCookies(String[] requestLines) {
		boolean isCookie = false;
		for (int i = 0; i < requestLines.length; i++) {
			String requestLine = requestLines[i];
			if (!isCookie && requestLine.startsWith("Cookie:")) {
				isCookie = true;
			} else if (isCookie && (requestLine.contains(":") || requestLine.isEmpty())){
				break;
			}
			
			if (isCookie) {
				addCookies(requestLine);
			}
		}
	}
	
	private void addCookies(String cookieLine) {
		String[] cookies = cookieLine.split("\\s+");
		
		for (int i = 0; i < cookies.length; i++) {
			String cookie = cookies[i];
			if ("Cookie:".equalsIgnoreCase(cookie)) {
				continue;
			}
			
			String[] cookieParts = cookies[i].split("=");
			
			String cookieValue = cookieParts[1];
			if (cookieValue.endsWith(";")) {
				cookieValue = cookieValue.substring(0, cookieValue.length() - 1);
			}
			
			this.cookies.add(new HttpCookie(cookieParts[0], cookieValue));
		}
	}
}
