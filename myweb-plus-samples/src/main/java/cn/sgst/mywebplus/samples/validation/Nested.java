package cn.sgst.mywebplus.samples.validation;

import lombok.Data;
import org.hibernate.validator.constraints.ScriptAssert;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/4/7 9:22
 */

@Data
@ScriptAssert(lang = "javascript",reportOn = "endDate",script = "_this.check(_this.startDate,_this.endDate)",message = "开始时间必须小于终止时间2")
public class Nested {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public boolean check(Date startDate,Date endDate) {
        if(startDate == null || endDate == null) {
            return true;
        }
        return startDate.before(endDate);

    }
}
