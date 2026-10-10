package ru.fedorov.LR2.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @NotBlank
    @Size(max = 32)
    private String uid;

    @NotBlank
    @Size(max = 32)
    private String operationUid;

    private String systemName;

    @NotBlank
    private String systemTime;
    
    private String source;

    @NotNull
    @Min(1)
    @Max(100000)
    private Integer communicationId;

    private Integer templateId;

    private Integer productCode;

    private Integer smsCode;

    public String toString() {
        return "{" + 
               "uid='" + uid + "'" +
               ", operationUid='" + operationUid + "'" +
               ", systemName='" + systemName + "'" +
               ", systemTime='" + systemTime + "'" +
               ", source='" + source + "'" +
               ", communicationId=" + communicationId +
               ", templateId=" + templateId +
               ", productCode=" + productCode +
               ", smsCode=" + smsCode + "}";

    }

}
