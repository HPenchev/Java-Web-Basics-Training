import java.util.HashMap;
import java.util.Map;

public class HttpResponseImpl implements HttpResponse {

	private int statusCode;
	byte[] content;
	Map<String, String> headers = new HashMap<>();
	
	@Override
	public int getStatusCode() {
		return this.statusCode;
	}

	@Override
	public byte[] getContent() {
		return this.content;
	}
	
	@Override
	public byte[] getBytes() {
		return this.buildResponse().getBytes();
	}

	@Override
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;

	}

	@Override
	public void setContent(byte[] content) {
		this.content = content;
	}

	@Override
	public void addHeader(String header, String value) {
		this.headers.put(header, value);
	}
	
	private String buildResponse() {
		StringBuilder response = new StringBuilder("HTTP/1.1 " + this.statusCode + System.lineSeparator());
		
		for (Map.Entry<String, String> entry : this.headers.entrySet()) {
			response.append(entry.getKey() + ": " + entry.getValue() + System.lineSeparator());
		}
		
		response.append(System.lineSeparator());
		response.append(this.content);
		
		return response.toString();
	}
}
