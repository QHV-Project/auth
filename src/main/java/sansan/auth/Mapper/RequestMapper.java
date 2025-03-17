package sansan.auth.Mapper;

import org.springframework.stereotype.Component;
import sansan.auth.Entity.RequestEntity;
import sansan.utility.lib.Enum.RequestStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RequestMapper {
    public RequestEntity createDefaultRequest() {
        RequestEntity request = new RequestEntity();
        request.setRequestId(UUID.randomUUID().toString());
        request.setCreateTime(LocalDateTime.now());
        request.setUpdateTime(LocalDateTime.now());
        request.setStatus(RequestStatus.INIT.name());
        return request;
    }
}
