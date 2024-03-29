package com.fujitsu.api.mappers;

import com.fujitsu.api.dtos.RegionalBaseFeeDto;
import com.fujitsu.api.entities.RegionalBaseFee;
import com.fujitsu.api.mappers.common.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegionalBaseFeeMapper extends EntityMapper<RegionalBaseFee, RegionalBaseFeeDto> {
}
