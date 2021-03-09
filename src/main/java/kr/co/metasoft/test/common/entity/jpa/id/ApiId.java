package kr.co.metasoft.test.common.entity.jpa.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class ApiId implements Serializable {

    private static final long serialVersionUID = -6891187300465007195L;

    @Column (name = "url")
    private String url;

    @Column (name = "method")
    private String method;

}