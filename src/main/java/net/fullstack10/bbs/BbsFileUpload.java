package net.fullstack10.bbs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import net.fullstack10.common.CommonFileUtil;

public class BbsFileUpload {
	CommonFileUtil fUtil;
	
	public BbsFileUpload() {
		fUtil = new CommonFileUtil();
	}
	public List<Map> fileUpload(HttpServletRequest request) throws ServletException, IOException {
		String newFile = "";
		String fileExt = "";
		List<Map> files = new ArrayList<>();
		// 파일 업로드 디렉토리 설정
		String saveDir = "/Users/sinjihye/dev/java10/sssproj/sssproj/src/main/webapp/Uploads";
		String virtualDir = "/Uploads";
		
		// 파일 업로드
		List<String> orgFiles = fUtil.multiFileUpload(request, saveDir);
		System.out.println(orgFiles);
		if(orgFiles != null && !orgFiles.isEmpty()) {
			for(String orgFile : orgFiles) {
				System.out.println("orgFile:" + orgFile);
				Map<String, String> file = new HashMap<>();
				newFile = fUtil.fileRename(saveDir, orgFile);
				fileExt = fUtil.getFileInfo("FILE_EXT", orgFile);
				file.put("fileName", newFile);
				file.put("fileExt", fileExt);
				file.put("filePath", virtualDir);
				file.put("fileSize", ""+fUtil.getFileSize(saveDir, newFile));
				files.add(file);
			}
		}
		return files;
	}
}
