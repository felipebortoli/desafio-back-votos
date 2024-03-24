package com.votacao.sistemavotacao.infra.Utils;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class HttpUtil {

	 private HttpClient client;

	 private void getHttpClient() {
		 if (client == null) {
			 client = HttpClient.newHttpClient();
	     }
	 }
	 
	 public HttpResponse<String> requestGET(String url) throws Exception {
		 HttpRequest request = HttpRequest.newBuilder(URI.create(url))
				 .header("accept", "application/json")
				 .build();

		 getHttpClient();
		 return client.send(request, HttpResponse.BodyHandlers.ofString());
	 }

	 public HttpResponse<String> requestPOST(String url) throws Exception {
		 HttpRequest request = HttpRequest.newBuilder(URI.create(url))
				 .header("accept", "application/json")
				 .POST(HttpRequest.BodyPublishers.noBody())
				 .build();

		 getHttpClient();
		 return client.send(request, HttpResponse.BodyHandlers.ofString());
	 }
	 
	 public HttpResponse<String> requestPOST(String url, String requestBody) throws Exception {
	        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
	                .header("Content-Type", "application/json")
	                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                .build();

	        getHttpClient();
	        return client.send(request, HttpResponse.BodyHandlers.ofString());
	    }
	    
}
