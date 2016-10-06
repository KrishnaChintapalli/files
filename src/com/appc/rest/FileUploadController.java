package com.appc.rest;

import com.appc.file.modle.TabFile;
import com.appc.file.service.FileService;
import com.appc.file.service.UserService;
import com.appc.util.Base64FileUtil;
import com.appc.util.DateUtils;
import com.appc.util.HMACMD5;
import com.appc.util.PropertyUtil;
import com.appc.util.html.Html2Image;
import com.appc.util.html.renderer.ImageRenderer;
import com.appc.views.ResultView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@RestController
public class FileUploadController extends BaseRestController {
	private static String pkey = "llzzddsszz";
	private static long timeout_long = 1800000L;

	@Autowired
	private FileService fileService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/uploadFile", method = org.springframework.web.bind.annotation.RequestMethod.POST)
	public ResultView uploadFile(MultipartHttpServletRequest request, @RequestParam("token") String token,
			@RequestParam("t") String time) throws IllegalStateException, IOException {
		if ((StringUtils.isEmpty(token)) || (StringUtils.isEmpty(time))) {
			return error("EU008", "permission_error");
		}
		if (System.currentTimeMillis() - Long.parseLong(time) > timeout_long) {
			return error("EU009", "timeout error");
		}
		String c = time + "__" + pkey;
		try {
			String cult_token = HMACMD5.toHmacMd5(c, HMACMD5.FILE_KEY);
			if (!(token.equalsIgnoreCase(cult_token)))
				return error("EU008", "permission_error");
		} catch (Exception e) {
			return error("EU008", "permission_error");
		}
		String cult_token;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		List fileIds = new ArrayList();
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = request;
			Iterator iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile((String) iter.next());
				if (file != null) {
					String fileName = file.getOriginalFilename();
					String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
					fileName = new Date().getTime() + "." + prefix;
					String filePath = File.separatorChar + DateUtils.dateToStringYYYY_MM_DD(new Date())
							+ File.separatorChar + fileName;
					String path = PropertyUtil.getProperty("uploadFilePath") + filePath;
					File fileTemp = new File(path);
					if (!(fileTemp.getParentFile().exists())) {
						fileTemp.getParentFile().mkdirs();
					}

					file.transferTo(fileTemp);

					String fileId = saveFile(filePath, path);
					fileIds.add(fileId);
				}
			}
		}

		return success("S0001", fileIds);
	}

	@RequestMapping(value = { "/uploadFileBase64" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST })
	public ResultView uploadFile(@RequestParam("base64str") String base64str, @RequestParam("filename") String fileName,
			@RequestParam("token") String token, @RequestParam("t") String time)
			throws IllegalStateException, IOException {
		if (System.currentTimeMillis() - Long.parseLong(time) > timeout_long) {
			return error("EU009", "timeout error");
		}
		String c = time + "__" + pkey;
		try {
			String cult_token = HMACMD5.toHmacMd5(c, HMACMD5.FILE_KEY);
			if (!(token.equalsIgnoreCase(cult_token)))
				return error("EU008", "permission_error");
		} catch (Exception e) {
			return error("EU008", "permission_error");
		}
		String cult_token;
		List fileIds = new ArrayList();
		String prefix = "";
		if ((fileName != null) && (fileName.lastIndexOf(".") > 0)
				&& (fileName.lastIndexOf(".") != fileName.length() - 1)) {
			prefix = "." + fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		fileName = new Date().getTime() + prefix;
		String filePath = File.separatorChar + DateUtils.dateToStringYYYY_MM_DD(new Date()) + File.separatorChar
				+ fileName;
		String path = PropertyUtil.getProperty("uploadFilePath") + filePath;
		File fileTemp = new File(path);
		if (!(fileTemp.getParentFile().exists())) {
			fileTemp.getParentFile().mkdirs();
		}
		Base64FileUtil.base64ToImage(base64str, path);

		String fileId = saveFile(filePath, path);
		fileIds.add(fileId);

		return success("S0001", fileIds);
	}

	@RequestMapping(value = { "/uploadFileByNetUrl" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST })
	public ResultView uploadFileByNetUrl(@RequestParam("url") String url, @RequestParam("token") String token,
			@RequestParam("t") String time) throws Exception {
		if (System.currentTimeMillis() - Long.parseLong(time) > timeout_long) {
			return error("EU009", "timeout error");
		}
		String c = time + "__" + pkey;
		try {
			String cult_token = HMACMD5.toHmacMd5(c, HMACMD5.FILE_KEY);
			if (!(token.equalsIgnoreCase(cult_token)))
				return error("EU008", "permission_error");
		} catch (Exception e) {
			return error("EU008", "permission_error");
		}
		String cult_token;
		URL urlobj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) urlobj.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);

		String contentType = conn.getContentType();
		int contentLength = conn.getContentLength();

		if ((contentType.equals("text/")) || (contentLength == -1)) {
			return error("EU006", "miss para content-type");
		}

		List fileIds = new ArrayList();
		String prefix = ".jpg";
		if ((url != null) && (url.lastIndexOf(".") > 0) && (url.lastIndexOf(".") != url.length() - 1)) {
			prefix = "." + url.substring(url.lastIndexOf(".") + 1);
		}
		String fileName = new Date().getTime() + prefix;
		String filePath = File.separatorChar + DateUtils.dateToStringYYYY_MM_DD(new Date()) + File.separatorChar
				+ fileName;
		String path = PropertyUtil.getProperty("uploadFilePath") + filePath;
		File fileTemp = new File(path);
		if (!(fileTemp.getParentFile().exists())) {
			fileTemp.getParentFile().mkdirs();
		}
		InputStream inStream = conn.getInputStream();
		byte[] btImg = readInputStream(inStream);
		writeImageToDisk(btImg, path);

		String fileId = saveFile(filePath, path);
		fileIds.add(fileId);

		return success("S0001", fileIds);
	}

	@RequestMapping(value = { "/uploadFileByHtmlPicUrl" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST })
	public ResultView uploadFileByHtmlPicUrl(@RequestParam("url") String url, @RequestParam("width") String widthStr,
			@RequestParam("height") String heightStr, @RequestParam("token") String token,
			@RequestParam("t") String time) throws Exception {
		if (System.currentTimeMillis() - Long.parseLong(time) > timeout_long) {
			return error("EU009", "timeout error");
		}
		String c = time + "__" + pkey;
		try {
			String cult_token = HMACMD5.toHmacMd5(c, HMACMD5.FILE_KEY);
			if (!(token.equalsIgnoreCase(cult_token)))
				return error("EU008", "permission_error");
		} catch (Exception e) {
			return error("EU008", "permission_error");
		}
		String cult_token;
		List fileIds = new ArrayList();
		String prefix = ".png";
		String fileName = new Date().getTime() + prefix;
		String filePath = File.separatorChar + DateUtils.dateToStringYYYY_MM_DD(new Date()) + File.separatorChar
				+ fileName;
		String path = PropertyUtil.getProperty("uploadFilePath") + filePath;
		File fileTemp = new File(path);
		if (!(fileTemp.getParentFile().exists())) {
			fileTemp.getParentFile().mkdirs();
		}
		int width = 640;
		int height = 0;
		if (StringUtils.isNotEmpty(widthStr)) {
			width = Integer.parseInt(widthStr);
		}
		if (StringUtils.isNotEmpty(heightStr)) {
			height = Integer.parseInt(heightStr);
		}
		Html2Image html2Image = Html2Image.fromURL(new URL(url));

		ImageRenderer imageRenderer = html2Image.getImageRenderer();
		if (height == 0)
			imageRenderer.setAutoHeight(true);
		else {
			imageRenderer.setHeight(height);
		}
		imageRenderer.setWidth(width);
		html2Image.getImageRenderer().saveImage(path);

		String fileId = saveFile(filePath, path);
		fileIds.add(fileId);

		return success("S0001", fileIds);
	}

	private String saveFile(String path, String pathToFile) {
		TabFile tblFile = new TabFile();
		String id = UUID.randomUUID().toString();
		tblFile.setId(id);
		tblFile.setFilePath(path);
		tblFile.setType(getContentType(pathToFile));
		tblFile.setCreateTime(new Date());
		this.fileService.addFile(tblFile);
		return id;
	}

	@RequestMapping(value = { "/getFile/{id}" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public void download(HttpServletResponse response, @PathVariable("id") String fileId) throws IOException {
		FileInputStream fis = null;
		OutputStream out = response.getOutputStream();
		try {
			TabFile tfile = this.fileService.getFile(fileId);
			String path = PropertyUtil.getProperty("uploadFilePath") + tfile.getFilePath();
			File file = new File(path);
			fis = new FileInputStream(file);
			byte[] b = new byte[fis.available()];
			fis.read(b);

			response.setContentType(tfile.getType());
			out.write(b);
			out.flush();
		} catch (Exception e) {
			String simplename = e.getClass().getSimpleName();
			if ("ClientAbortException".equals(simplename))
				System.out.println("Client Abort Download..");
			else
				e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null)
				out.close();
		}
	}

	public static void writeImageToDisk(byte[] img, String file) {
		try {
			FileOutputStream fops = new FileOutputStream(file);
			fops.write(img);
			fops.flush();
			fops.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getContentType(String pathToFile) {
		Path path = Paths.get(pathToFile, new String[0]);
		String contentType = "image/png";
		try {
			contentType = Files.probeContentType(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentType;
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}
}