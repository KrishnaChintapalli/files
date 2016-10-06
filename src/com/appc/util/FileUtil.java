package com.appc.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.appc.util.html.Html2Image;
import com.appc.util.html.renderer.ImageRenderer;
import java.io.File;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class FileUtil {
	public static String baseUrl = "http://192.168.1.70:8181/file";
	public static String uploadUrl = baseUrl + "/uploadFile";
	public static String uploadBase64Url = baseUrl + "/uploadFileBase64";
	public static String uploadFileByNetUrl = baseUrl + "/uploadFileByNetUrl";
	public static String uploadFileByHtmlPicUrl = baseUrl + "/uploadFileByHtmlPicUrl";
	public static String getFileUrl = baseUrl + "/getFile/";

	private static String pkey = "lljjddsszz";

	public static String upload(String textFileName) {
		HttpClient httpclient = new DefaultHttpClient();
		String fileId = null;
		try {
			HttpPost httppost = new HttpPost(uploadUrl);
			FileBody bin = new FileBody(new File(textFileName));
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("upload", bin);
			long t = System.currentTimeMillis();
			reqEntity.addPart("t", new StringBody(String.valueOf(t)));
			reqEntity.addPart("token", new StringBody(getUploadToken(t)));
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity resEntity = response.getEntity();
				String text = EntityUtils.toString(resEntity);
				System.out.println(text);
				JSONObject json = JSONObject.parseObject(text);
				String code = json.getString("code");

				if (("S0001".equals(code)) && (json.getJSONArray("data") != null)) {
					JSONArray jsonArray = json.getJSONArray("data");
					fileId = jsonArray.getString(0);
				} else {
					throw new RuntimeException("?????");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("upload error", e);
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {
			}
		}
		return fileId;
	}

	public static String uploadBase64(String base64str, String fileName) {
		HttpClient httpclient = new DefaultHttpClient();
		String fileId = null;
		try {
			HttpPost httppost = new HttpPost(uploadBase64Url);
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("filename", new StringBody(fileName));
			reqEntity.addPart("base64str", new StringBody(base64str));
			long t = System.currentTimeMillis();
			reqEntity.addPart("t", new StringBody(String.valueOf(t)));
			reqEntity.addPart("token", new StringBody(getUploadToken(t)));
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity resEntity = response.getEntity();
				String text = EntityUtils.toString(resEntity);
				System.out.println(text);
				JSONObject json = JSONObject.parseObject(text);
				String code = json.getString("code");

				if (("S0001".equals(code)) && (json.getJSONArray("data") != null)) {
					JSONArray jsonArray = json.getJSONArray("data");
					fileId = jsonArray.getString(0);
				} else {
					throw new RuntimeException("?????");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("upload error", e);
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {
			}
		}
		return fileId;
	}

	public static String uploadFileByNetUrl(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		String fileId = null;
		try {
			HttpPost httppost = new HttpPost(uploadFileByNetUrl);
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("url", new StringBody(url));
			long t = System.currentTimeMillis();
			reqEntity.addPart("t", new StringBody(String.valueOf(t)));
			reqEntity.addPart("token", new StringBody(getUploadToken(t)));
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity resEntity = response.getEntity();
				String text = EntityUtils.toString(resEntity);
				System.out.println(text);
				JSONObject json = JSONObject.parseObject(text);
				String code = json.getString("code");

				if (("S0001".equals(code)) && (json.getJSONArray("data") != null)) {
					JSONArray jsonArray = json.getJSONArray("data");
					fileId = jsonArray.getString(0);
				} else {
					throw new RuntimeException("?????");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("upload error", e);
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {
			}
		}
		return fileId;
	}

	public static String uploadFileByHtmlPicUrl(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		String fileId = null;
		try {
			HttpPost httppost = new HttpPost(uploadFileByHtmlPicUrl);
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("url", new StringBody(url));
			long t = System.currentTimeMillis();
			reqEntity.addPart("t", new StringBody(String.valueOf(t)));
			reqEntity.addPart("token", new StringBody(getUploadToken(t)));
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity resEntity = response.getEntity();
				String text = EntityUtils.toString(resEntity);
				System.out.println(text);
				JSONObject json = JSONObject.parseObject(text);
				String code = json.getString("code");

				if (("S0001".equals(code)) && (json.getJSONArray("data") != null)) {
					JSONArray jsonArray = json.getJSONArray("data");
					fileId = jsonArray.getString(0);
				} else {
					throw new RuntimeException("?????");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("upload error", e);
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {
			}
		}
		return fileId;
	}

	public static String getUploadToken(long time) {
		String c = time + "__" + pkey;
		String cult_token = "";
		try {
			cult_token = HMACMD5.toHmacMd5(c, HMACMD5.FILE_KEY);
		} catch (Exception e) {
		}
		return cult_token;
	}

	public static void main(String[] args) throws MalformedURLException {
		Html2Image html2Image = Html2Image.fromURL(new URL(
				"http://www.yxwzb.com/promotion/invitation_card?ticket=gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm3sUw=="));

		ImageRenderer imageRenderer = html2Image.getImageRenderer();
		imageRenderer.setAutoHeight(true);
		imageRenderer.setWidth(640);
		html2Image.getImageRenderer().saveImage("D:\\upload\\files\\2015-12-08\\aa2211.png");
	}
}