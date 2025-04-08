package net.fullstack10.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

public class CommonFileUtil {

	// 파일명/확장자 리턴
	public String getFileInfo(String sType, String fileName) {
		String rtnResult = "";
		switch(sType) {
		case "FILE_NAME" :
			rtnResult = fileName.substring(0, fileName.lastIndexOf("."));
			break;
		case "FILE_EXT" :
			rtnResult = fileName.substring(fileName.lastIndexOf(".")+1);
			break;
		case "FILE_SIZE" :
			break;
		default :
		}

		return rtnResult;
	}

	// 파일명 변경
	public String fileRename(String sDir, String fileName) {
		//원본 파일 -> 확장자 추출
		String fileExt = fileName.substring(fileName.lastIndexOf("."));

		// 날짜+시간 을 이용한 임시 파일명 생성
		String now = new SimpleDateFormat("MMddHHmmssS").format(new Date());

		// 임시파일명+확장자 -> 새로운 파일명 생성
		String newFileName = now + fileExt;

		// 기존 파일명 --> 새로운 파일명으로 변경
		File oldFile = new File(sDir + File.separator + fileName);
		File newFile = new File(sDir + File.separator + newFileName);
		oldFile.renameTo(newFile);

		// 새로운 파일명으로 반환
		return newFileName;
	}

	// 파일 사이즈
	public Long getFileSize(String sDir, String fileName) {
		File file = new File(sDir + File.separator + fileName);
		return (file !=null ? file.getTotalSpace() : 0);
	}
	// 파일 업로드
	public List<String> multiFileUpload(HttpServletRequest req, String sDir) throws ServletException, IOException {
		List<String> arrFileName = new ArrayList<>();
		Collection<Part> parts = req.getParts();

		// Part 객체의 헤더값 참조 -> content-disposition 조회
		for (Part part : parts) {
			if(!part.getName().equals("files")) {
				continue;
			}
			String pHeader = part.getHeader("content-disposition");
			// --> form-data; name="attatchFile"; filename="파일명.xxx"
			System.out.println("partHeader : "+ pHeader);

			// 헤더에서 추출한 파일명 처리
			String[] pArrHeader = pHeader.split("filename=");
			String orgFileName = pArrHeader[1].trim().replace("\"", "");
			if (orgFileName.isEmpty() || orgFileName.equals("")) return null;
			part.write(sDir + File.separator + orgFileName);
			arrFileName.add(orgFileName);
		}
		return arrFileName;
	}

	// 파일 업로드
	public String fileUpload(HttpServletRequest req, String sDir)
			throws ServletException, IOException {
		// Part 객체를 이용하여 서버로 전송된 파일 정보 처리
		Part part = req.getPart("file1");

		// Part 객체의 헤더값 참조 -> content-disposition 조회
		String pHeader = part.getHeader("content-disposition");
		// --> form-data; name="attatchFile"; filename="파일명.xxx"
		System.out.println("partHeader : "+ pHeader);

		// 헤더에서 추출한 파일명 처리
		String[] pArrHeader = pHeader.split("filename=");
		String orgFileName = pArrHeader[1].trim().replace("\"", "");

		if ( !orgFileName.isEmpty() ) {
			part.write(sDir + File.separator + orgFileName);
		}
		return orgFileName;
	}

	// 파일 삭제
	public void fileDelete(HttpServletRequest req, String sDir, String fileName) {
		//String Dir = req.getServletContext().getRealPath(sDir);
		String Dir = sDir;
		File file = new File(Dir + File.separator + fileName);
		if ( file.exists() ) {
			file.delete();
		}
	}
	// 파일 다운로드
	public void fileDownload(HttpServletRequest req, HttpServletResponse res, String dir, String orgFileName, String outFileName) {
		// 실제 물리적 경로
		String realPath = req.getServletContext().getRealPath(dir);
		try {
			File file = new File(realPath, orgFileName);
			InputStream is = new FileInputStream(file);
			String userAgent = req.getHeader("User-Agent");
			if(userAgent.indexOf("WOW64")==-1) {
				outFileName = new String(outFileName.getBytes("UTF-8"), "ISO-8859-1");
			} else {
				outFileName = new String(outFileName.getBytes("KSCO5601"), "ISO-8859-1");
			}
			// 응답 헤더
			res.reset();
			res.setContentType("application/octet-stream"); //파일종류에 따라 다름
			res.setHeader("Content-Disposition", "attachment; filename=\"" + outFileName + "\"");
			res.setHeader("Content-Length", ""+file.length());
			OutputStream os = res.getOutputStream();
			byte b[] = new byte[(int)file.length()];
			int readBuffer = 0;
			while ((readBuffer=is.read(b))>0) {
				os.write(b, 0, readBuffer);
			}
			if (is!=null) {
				is.close();
			}
			if (os!=null) {
				os.close();
			}
			os.close();
		} catch(FileNotFoundException e) {
			System.out.println("FileNotFoundException발생 : " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception발생 : " + e.getMessage());
			e.printStackTrace();
		}

	}
}
