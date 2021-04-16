package kr.co.metasoft.ito.api.common.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.ContactDto;
import kr.co.metasoft.ito.api.common.dto.ContactParamDto;
import kr.co.metasoft.ito.api.common.dto.NoticeDto;
import kr.co.metasoft.ito.api.common.dto.NoticeParamDto;
import kr.co.metasoft.ito.common.util.PageRequest;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class HomepageRepository {

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public void createConnection() {

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mariadb://meta-soft.co.kr:3306/metasofthomepage", "metasofthomepage", "ms20170901!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.debug("failed to load jdbc driver ");
        } catch (SQLException e) {
            e.printStackTrace();
            log.debug("failed to connect into mariadb");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Integer selectNoticeListCount(NoticeParamDto noticeParamDro) {
        int count = 0;

        createConnection();

        try {
            pstmt = conn.prepareStatement("select count(*) AS count from tb_notice");

            rs = pstmt.executeQuery();

            rs.next();
            count = rs.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) {
                    rs.close(); // 선택 사항
                }

                if(pstmt != null) {
                    pstmt.close(); // 선택사항이지만 호출 추천
                }

                if(conn != null) {
                    conn.close(); // 필수 사항
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    public List<NoticeDto> selectNoticeList(NoticeParamDto noticeParamDro, PageRequest pageRequest) {

        List<NoticeDto> noticeDtoList = new ArrayList<>();
        createConnection();
        try {
            pstmt = conn.prepareStatement("select * from tb_notice");

            rs = pstmt.executeQuery();

            while(rs.next()) {
                NoticeDto noticeDto = new NoticeDto();
                noticeDto.setContent(rs.getString("content"));
                noticeDto.setCreatedDate(Instant.ofEpochMilli(rs.getDate("created_date").getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
                noticeDto.setIdx(rs.getInt("idx"));
                noticeDto.setLastModifiedDate(Instant.ofEpochMilli(rs.getDate("last_modified_date").getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
                noticeDto.setTitle(rs.getString("title"));
                noticeDtoList.add(noticeDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) {
                    rs.close(); // 선택 사항
                }

                if(pstmt != null) {
                    pstmt.close(); // 선택사항이지만 호출 추천
                }

                if(conn != null) {
                    conn.close(); // 필수 사항
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return noticeDtoList;
    }

    public Integer selectContactListCount(ContactParamDto contactParamDro) {
        int count = 0;

        createConnection();

        try {
            pstmt = conn.prepareStatement("select count(*) AS count from tb_contact");

            rs = pstmt.executeQuery();

            rs.next();
            count = rs.getInt("count");
            log.info(Integer.toString(count));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) {
                    rs.close(); // 선택 사항
                }

                if(pstmt != null) {
                    pstmt.close(); // 선택사항이지만 호출 추천
                }

                if(conn != null) {
                    conn.close(); // 필수 사항
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    public List<ContactDto> selectContactList(ContactParamDto contactParamDro, PageRequest pageRequest) {

        List<ContactDto> contactDtoList = new ArrayList<>();
        createConnection();
        try {
            pstmt = conn.prepareStatement("select * from tb_contact");

            rs = pstmt.executeQuery();

            while(rs.next()) {
                ContactDto contactDto = new ContactDto();
                contactDto.setContent(rs.getString("content"));
                contactDto.setCreatedDate(Instant.ofEpochMilli(rs.getDate("created_date").getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
                contactDto.setIdx(rs.getInt("idx"));
                contactDto.setLastModifiedDate(Instant.ofEpochMilli(rs.getDate("last_modified_date").getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
                contactDto.setTitle(rs.getString("title"));
                contactDto.setCompany(rs.getString("company"));
                contactDto.setContactType(rs.getString("contact_type"));
                contactDto.setEmail(rs.getString("email"));
                contactDto.setName(rs.getString("name"));
                contactDto.setPhone(rs.getString("phone"));
                contactDtoList.add(contactDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) {
                    rs.close(); // 선택 사항
                }

                if(pstmt != null) {
                    pstmt.close(); // 선택사항이지만 호출 추천
                }

                if(conn != null) {
                    conn.close(); // 필수 사항
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return contactDtoList;
    }

}
