package kr.co.metasoft.ito.api.common.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

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

    @Transactional
    public NoticeDto selectNotice(Integer id) {
        NoticeDto noticeDto = new NoticeDto();
        createConnection();
        try {
            pstmt = conn.prepareStatement("select * from tb_notice where idx = " + id + " LIMIT 1");

            rs = pstmt.executeQuery();

            rs.next();
            noticeDto.setContent(rs.getString("content"));
            noticeDto.setCreatedDate(Instant.ofEpochMilli(rs.getDate("created_date").getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
            noticeDto.setIdx(rs.getInt("idx"));
            noticeDto.setLastModifiedDate(Instant.ofEpochMilli(rs.getDate("last_modified_date").getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
            noticeDto.setTitle(rs.getString("title"));
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
        return noticeDto;
    }

    @Transactional
    public Integer saveNoticeList(List<NoticeDto> noticeList) {
        NoticeDto noticeDto = new NoticeDto();
        int row = 0;
        createConnection();
        try {
            String sql = "insert into tb_notice (title, content, created_date, last_modified_date) values (?, ?, ?, ?)";
            for(int i = 0; i < noticeList.size(); i++) {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, noticeDto.getTitle()); //1 5 9
                pstmt.setString(2, noticeDto.getContent()); //2 6 10
                pstmt.setString(3, LocalDateTime.now().toString()); //3 7 11
                pstmt.setString(4, LocalDateTime.now().toString()); //4 8 12
                row += pstmt.executeUpdate();
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
        return row;
    }

    @Transactional
    public Integer saveNotice(NoticeDto noticeDto) {
        int row = 0;
        createConnection();
        try {
            String sql = "insert into tb_notice (title, content, created_date, last_modified_date) values ";
            sql += "(?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, noticeDto.getTitle()); //1 5 9
            pstmt.setString(2, noticeDto.getContent()); //2 6 10
            pstmt.setString(3, LocalDateTime.now().toString()); //3 7 11
            pstmt.setString(4, LocalDateTime.now().toString()); //4 8 12
            row = pstmt.executeUpdate();
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
        return row;
    }

    @Transactional
    public Integer modifyNoticeList(List<NoticeDto> noticeList) {
        NoticeDto noticeDto = new NoticeDto();
        int row = 0;
        createConnection();
        try {
            String sql = "update tb_notice set title=?, content=?, last_modified_date=? where idx = ?";
            for(int i = 0; i < noticeList.size(); i++) {
                noticeDto = noticeList.get(i);
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, noticeDto.getTitle());
                pstmt.setString(2, noticeDto.getContent());
                pstmt.setString(3, LocalDateTime.now().toString());
                pstmt.setInt(4, noticeDto.getIdx());
                row += pstmt.executeUpdate();
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
        return row;
    }

    @Transactional
    public Integer modifiyNotice(NoticeDto noticeDto) {
        int row = 0, index = 1;
        createConnection();
        try {
            String sql = "update tb_notice set title=?, content=?, last_modified_date=? where idx = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(index++, noticeDto.getTitle());
            pstmt.setString(index++, noticeDto.getContent());
            pstmt.setString(index++, LocalDateTime.now().toString());
            pstmt.setInt(index++, noticeDto.getIdx());
            row += pstmt.executeUpdate();
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
        return row;
    }

    @Transactional
    public Integer deleteNoticeList(List<NoticeDto> noticeList) {
        NoticeDto noticeDto = new NoticeDto();
        int row = 0;
        createConnection();
        try {
            String sql = "delete from tb_notice where idx = ?";
            for(int i = 0; i < noticeList.size(); i++) {
                noticeDto = noticeList.get(i);
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, noticeDto.getIdx());
                row += pstmt.executeUpdate();
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
        return row;
    }

    @Transactional
    public Integer deleteNotice(NoticeDto noticeDto) {
        int row = 0;
        createConnection();
        try {
            String sql = "delete from tb_notice where idx = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, noticeDto.getIdx());
            row += pstmt.executeUpdate();
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
        return row;
    }

    @Transactional
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

    @Transactional
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

    @Transactional
    public ContactDto selectContact(Integer id) {
        ContactDto contactDto = new ContactDto();
        createConnection();
        try {
            pstmt = conn.prepareStatement("select * from tb_contact where idx = " + id + " LIMIT 1");

            rs = pstmt.executeQuery();

            rs.next();
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
        return contactDto;
    }

    @Transactional
    public Integer saveContactList(List<ContactDto> contactList) {
        ContactDto contactDto = new ContactDto();
        int row = 0;
        createConnection();
        try {
            String sql = "insert into tb_contact (title, content, company, name, phone, email, contact_type, created_date, last_modified_date) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            for(int i = 0; i < contactList.size(); i++) {
                contactDto = contactList.get(i);
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, contactDto.getTitle()); //1 5 9
                pstmt.setString(2, contactDto.getContent()); //2 6 10
                pstmt.setString(3, contactDto.getCompany()); //2 6 10
                pstmt.setString(4, contactDto.getName()); //2 6 10
                pstmt.setString(5, contactDto.getPhone()); //2 6 10
                pstmt.setString(6, contactDto.getEmail()); //2 6 10
                pstmt.setString(7, contactDto.getContactType()); //2 6 10
                pstmt.setString(8, LocalDateTime.now().toString()); //3 7 11
                pstmt.setString(91, LocalDateTime.now().toString()); //4 8 12
                row += pstmt.executeUpdate();
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
        return row;
    }

    @Transactional
    public Integer saveContact(ContactDto contactDto) {
        int row = 0;
        createConnection();
        try {
            String sql = "insert into tb_contact (title, content, company, name, phone, email, contact_type, created_date, last_modified_date) values ";
            sql += "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, contactDto.getTitle()); //1 5 9
            pstmt.setString(2, contactDto.getContent()); //2 6 10
            pstmt.setString(3, contactDto.getCompany()); //2 6 10
            pstmt.setString(4, contactDto.getName()); //2 6 10
            pstmt.setString(5, contactDto.getPhone()); //2 6 10
            pstmt.setString(6, contactDto.getEmail()); //2 6 10
            pstmt.setString(7, contactDto.getContactType()); //2 6 10
            pstmt.setString(8, LocalDateTime.now().toString()); //3 7 11
            pstmt.setString(9, LocalDateTime.now().toString()); //4 8 12
            row = pstmt.executeUpdate();
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
        return row;
    }

    @Transactional
    public Integer modifyContactList(List<ContactDto> contactList) {
        ContactDto contactDto = new ContactDto();
        int row = 0;
        createConnection();
        try {
            String sql = "update tb_contact set title=?, content=?, company=?, name=?, phone=?, email=?, contact_type=?, last_modified_date=? where idx = ?";
            for(int i = 0; i < contactList.size(); i++) {
                contactDto = contactList.get(i);
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, contactDto.getTitle());
                pstmt.setString(2, contactDto.getContent());
                pstmt.setString(3, contactDto.getCompany()); //2 6 10
                pstmt.setString(4, contactDto.getName()); //2 6 10
                pstmt.setString(5, contactDto.getPhone()); //2 6 10
                pstmt.setString(6, contactDto.getEmail()); //2 6 10
                pstmt.setString(7, contactDto.getContactType()); //2 6 10
                pstmt.setString(8, LocalDateTime.now().toString());
                pstmt.setInt(9, contactDto.getIdx());
                row += pstmt.executeUpdate();
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
        return row;
    }

    @Transactional
    public Integer modifiyContact(ContactDto contactDto) {
        int row = 0;
        createConnection();
        try {
            String sql = "update tb_contact set title=?, content=?, company=?, name=?, phone=?, email=?, contact_type=?, last_modified_date=? where idx = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, contactDto.getTitle());
            pstmt.setString(2, contactDto.getContent());
            pstmt.setString(3, contactDto.getCompany()); //2 6 10
            pstmt.setString(4, contactDto.getName()); //2 6 10
            pstmt.setString(5, contactDto.getPhone()); //2 6 10
            pstmt.setString(6, contactDto.getEmail()); //2 6 10
            pstmt.setString(7, contactDto.getContactType()); //2 6 10
            pstmt.setString(8, LocalDateTime.now().toString());
            pstmt.setInt(9, contactDto.getIdx());
            row += pstmt.executeUpdate();
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
        return row;
    }

    @Transactional
    public Integer deleteContactList(List<ContactDto> contactList) {
        ContactDto contactDto = new ContactDto();
        int row = 0;
        createConnection();
        try {
            String sql = "delete from tb_contact where idx = ?";
            for(int i = 0; i < contactList.size(); i++) {
                contactDto = contactList.get(i);
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, contactDto.getIdx());
                row += pstmt.executeUpdate();
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
        return row;
    }

    @Transactional
    public Integer deleteContact(ContactDto contactDto) {
        int row = 0;
        createConnection();
        try {
            String sql = "delete from tb_contact where idx = ?";
            pstmt.setInt(1, contactDto.getIdx());
            pstmt = conn.prepareStatement(sql);
            row += pstmt.executeUpdate();
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
        return row;
    }

}
