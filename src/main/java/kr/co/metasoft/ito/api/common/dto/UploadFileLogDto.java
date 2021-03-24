package kr.co.metasoft.ito.api.common.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileLogDto {
	
	private String uploadType;
	
	private Integer uploadFileOrder;
	
	private String originFileNm;
	
	private String uploadDat;
	
	private String createdDate;
}
