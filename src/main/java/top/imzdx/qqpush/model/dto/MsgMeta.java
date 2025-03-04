package top.imzdx.qqpush.model.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 一般包含在消息类中的元数据，比如消息类别等
 *
 * @author Renxing
 * @tag 消息元数据类
 */
@Data
@Accessors(chain = true)
public class MsgMeta implements Serializable {
    /**
     * 消息类型，目前仅支持"qq"、"qq_group"
     *
     * @mock qq
     */
    @Pattern(regexp = "(^qq$|^qq_group$)", message = "type暂仅支持qq、qq_group")
    private String type;
    /**
     * 消息元数据，与type对应，qq：QQ号，qq_group：QQ群号
     *
     * @mock 1277489864
     */
    @NotNull(message = "消息元数据不能为空，一般填写收信人信息")
    private String data;

}
