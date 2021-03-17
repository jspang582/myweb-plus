package cn.sgst.mywebplus.samples.validation;

import cn.sgst.mywebplus.core.validation.constraints.EnumValue;
import cn.sgst.mywebplus.samples.serialize.HobbyEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.List;

/**
 * 复杂校验
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/16 17:04
 */
@Data
@ScriptAssert(lang = "javascript",reportOn = "endDate",script = "_this.checkParams(_this.startDate,_this.endDate)",message = "开始时间必须小于终止时间")
public class Inf {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private Integer age;

    private List<
            @EnumValue(target = HobbyEnum.class)
            String
            > hobbies;


    public  boolean checkParams(Date startDate,Date endDate) {
        if(startDate == null || endDate == null) {
            return true;
        }
        return startDate.before(endDate);

    }

    @Range(min = 1,max = 2)
    public Integer getAge(){
        return age;
    }
}
