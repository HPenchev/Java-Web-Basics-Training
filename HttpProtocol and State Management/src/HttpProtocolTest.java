import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpProtocolTest {

	public static void main(String[] args) throws IOException {
		String request = "GET /url HTTP/1.1\r\n" + 
				"Host: localhost:8000\r\n" + 
				"Cookie: lang=en; theme=dark; " + 
				"locale=bg_EN; " + 
				"JSession=58vzsdfg";
		
		HttpRequest requestObj = new HttpRequestImpl(request);
		
		System.out.println(getCookies(request));
	}

	public static String getResult(String request){
		String[] splitRequest = request.split(System.lineSeparator());
		
		Set<String> validUrls = getValidUrls(splitRequest[0]);
		Map<String, String> headers = getHeaders(splitRequest);
		
		String[] lineSplit = splitRequest[1].split("\\s+");
		String method = lineSplit[0];
		String requestType = lineSplit[2];
		
		String body = null;
		String lastLine = splitRequest[splitRequest.length - 1];
		
		if (!lastLine.isEmpty() && lastLine.contains("&")) {
			body = lastLine;
		}
		String responseHeaders = getResponseHeaders(headers);
		
		if (!validUrls.contains(lineSplit[1])) {
			return getNotFoundResponse(requestType, responseHeaders);
		}
		
		if (!headers.containsKey("Authorization")) {
			return getUnauthorizedResponse(requestType, responseHeaders);
		}
		
		if ("POST".equals(method) && body == null) {
			return getMalformedRequestResponse(requestType, responseHeaders);
		}
		
		return getOKRequestResponse(requestType, 
				responseHeaders, 
				body,
				decodeName(headers.get("Authorization")));
	}
	
	private static Set<String> getValidUrls(String urls) {
		return new HashSet<>(Arrays.asList(urls.split("\\s+")));
	}
	
	private static Map<String, String> getHeaders(String[] splitRequest) {
		Map<String, String> result = new HashMap<>();
		
		for (int i = 2; i < splitRequest.length; i++) {
			String requestLine = splitRequest[i];
			
			if (requestLine.isEmpty()) {
				break;
			}
			
			String[] splitLine = requestLine.split(":", 2);			
			result.put(splitLine[0].trim(), splitLine[1].trim());
		}
		
		return result;
	}
	
	private static String getResponseHeaders(Map<String, String> headers) {
		String result = "";
		
		if (headers.containsKey("Date")) {
			result += "Date: " + headers.get("Date");
		}
		
		if (headers.containsKey("Host")) {
			if (!result.isEmpty()) {
				result += System.lineSeparator();
			}
			
			result += "Host: " + headers.get("Host");
		}
		
		if (headers.containsKey("Content-Type")) {
			if (!result.isEmpty()) {
				result += System.lineSeparator();
			}
			
			result += "Content-Type: " + headers.get("Content-Type");
		}
		
		return result;
	}
	
	private static String getNotFoundResponse(String requestType, String headers) {
		return requestType + 
				" 404 Not Found" + 
				System.lineSeparator() + 
				headers +
				System.lineSeparator() +
				System.lineSeparator() + 
				"The requested functionality was not found.";
	}
	
	private static String getUnauthorizedResponse(String requestType, String headers) {
		return requestType + 
				" 401 Unauthorized" + 
				System.lineSeparator() + 
				headers +
				System.lineSeparator() +
				System.lineSeparator() + 
				"You are not authorized to access the requested functionality.";
	}
	
	private static String getMalformedRequestResponse(String requestType, String headers) {
		return requestType + 
				" 400 Bad Request" + 
				System.lineSeparator() + 
				headers +
				System.lineSeparator() +
				System.lineSeparator() + 
				"There was an error with the requested functionality due to malformed request.";
	}
	
	private static String getOKRequestResponse(String requestType, String headers, String body, String requestorName) {
		String responseBody = "Greetings " + requestorName + "!";
		
		if (body != null) {
			responseBody += parseParameters(body);
		}
		
		return requestType + 
				" 200 OK" + 
				System.lineSeparator() + 
				headers +
				System.lineSeparator() +
				System.lineSeparator() +				
				responseBody;
		
	}
	
	private static String parseParameters(String body) {
		String[] split = body.split("&");
		StringBuilder result = new StringBuilder(" You have successfully created " + split[0].split("=")[1] + " with");
		
		for (int i = 1; i < split.length; i ++) {
			String[] parameterSplit = split[i].split("=");
			result.append(" " + parameterSplit[0] + " - " + parameterSplit[1]);
			if (i == split.length - 1) {
				result.append('.');
			} else  {
				result.append(',');
			}
		}
		
		return result.toString();
	}
	
	private static String decodeName(String authorization) {
		return new String(Base64.getDecoder().decode(authorization.trim().split("\\s+")[1]));	
	}
	
	private static String getCookies(String request) {
		String[] requestLines = request.split(System.lineSeparator());
		boolean isCookie = false;
		StringBuilder response = new StringBuilder();
		for (int i = 0; i < requestLines.length; i++) {
			String requestLine = requestLines[i];
			if (!isCookie && requestLine.startsWith("Cookie:")) {
				isCookie = true;
			} else if (isCookie && (requestLine.contains(":") || requestLine.isEmpty())){
				break;
			}
			
			if (isCookie) {
				appendCookies(response, requestLines[i]);
			}
		}
		
		return response.toString().trim();
	}
	
	private static void appendCookies(StringBuilder response, String cookieLine) {
		String[] cookies = cookieLine.split("\\s+");
		
		for (int i = 0; i < cookies.length; i++) {
			String cookie = cookies[i];
			if ("Cookie:".equalsIgnoreCase(cookie)) {
				continue;
			}
			
			String[] cookieParts = cookies[i].split("=");
			response.append(cookieParts[0] + " <-> ");
			
			String cookieValue = cookieParts[1];
			if (cookieValue.endsWith(";")) {
				cookieValue = cookieValue.substring(0, cookieValue.length() - 1);
			}
			
			response.append(cookieValue + System.lineSeparator());
		}
	}
}
