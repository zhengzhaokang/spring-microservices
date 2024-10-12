package com.microservices.otmp.system.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * 消息通知记录对象 msg_remind
 * 
 * @author lovefamily
 * @date 2022-10-26
 */
@Data
public class MsgRemindDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** IT code */

    private String itCodes;

    private String module;

    private String msgTopic;

    private String sysCatalog;

    /** Msg Title */

    private String msgTitle;

    /** Msg Info */

    private String msgInfo;

    /*Info/Warm/Error*/
    private String msgType;

    /** Has Read 0未读 1已读*/

    private Integer hasRead;

    private Integer isUpdate;

    private Integer notReadCount;

}
