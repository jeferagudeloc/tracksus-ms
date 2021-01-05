package com.ven.cwt.app.entity;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.ven.cwt.app.util.dto.Header;

public class Request {

	@Id
	public String id;
	public String url;
	public String name;
	public String method;
	public String body;
	public List<Header> headers;
	public String userId;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public List<Header> getHeaders() {
		return headers;
	}
	public void setHeaders(List<Header> headers) {
		this.headers = headers;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Request() {
	}
	
	
	
	
	
	public Request(String url, String name, String method, String body, List<Header> headers, String userId) {
		this.url = url;
		this.name = name;
		this.method = method;
		this.body = body;
		this.headers = headers;
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Request [id=" + id + ", url=" + url + ", method=" + method + ", body=" + body + ", headers=" + headers
				+ "]";
	}
	
	

	
	
}
