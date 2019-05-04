
public interface HttpResponse {
	int getStatusCode();
	
	byte[] getContent();
	
	byte[] getBytes();
	
	void setStatusCode(int statusCode);
	
	void setContent(byte[] content);
	
	void addHeader(String header, String value);
}
