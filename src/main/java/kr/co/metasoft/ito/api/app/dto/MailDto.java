package kr.co.metasoft.ito.api.app.dto;

import java.util.List;

import com.sun.istack.ByteArrayDataSource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailDto {

    private String from;

    private List<String> toList;

    private String subject;

    private String text;

    private List<String> ccList;

    private String fileName;

    private ByteArrayDataSource fileData;

}
