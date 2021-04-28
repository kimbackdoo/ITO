package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.ApprovalDto;
import kr.co.metasoft.ito.api.common.dto.ApprovalParamDto;
import kr.co.metasoft.ito.api.common.entity.ApprovalEntity;
import kr.co.metasoft.ito.common.util.PageRequest;
import kr.co.metasoft.ito.common.util.PageResponse;

@Mapper
@Repository
public interface ApprovalMapper {

    public List<ApprovalEntity> selectApprovalList(
            @Param (value = "approvalParamDto") @Valid ApprovalParamDto approvalParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);


    public Integer selectApprovalListCount(
            @Param (value = "approvalParamDto") @Valid ApprovalParamDto approvalParamDto);

    public ApprovalEntity selectApproval(
            @Param (value = "approvalParamDto") @Valid ApprovalParamDto approvalParamDto);

}
