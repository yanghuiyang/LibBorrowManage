package com.yang.bishe.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.yang.bishe.entity.Book;

public class RetrieveDocumentByURL {
	public RetrieveDocumentByURL(String url) throws ClientProtocolException, IOException{
	    DefaultHttpClient client = new DefaultHttpClient();
	    HttpGet get = new HttpGet(url);
	    HttpResponse response = client.execute(get);
	        HttpEntity entity = response.getEntity();
	        InputStream is = entity.getContent();
	        Book book = new BookXMLParser(is).getBook();
	        System.out.println("title:->" + book.getBookName());
	       // System.out.println("summary:->"+ book.getSummary());
	        System.out.println("price:-->" + book.getPrice());
	        System.out.println("author:-->" + book.getAuthor());
	       // System.out.println("ImagePath:-->" + book.getImagePath());
	  }
	  public static void main(String[] args) throws ClientProtocolException, IOException {
	   new RetrieveDocumentByURL("http://api.douban.com/book/subject/isbn/9787308083256");
	  }
}
