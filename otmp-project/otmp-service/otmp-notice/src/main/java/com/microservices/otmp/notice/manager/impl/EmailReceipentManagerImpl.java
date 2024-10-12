package com.microservices.otmp.notice.manager.impl;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.notice.domain.dto.EmailReceipentDTO;
import com.microservices.otmp.notice.domain.entity.EmailReceipentDO;
import com.microservices.otmp.notice.manager.IEmailReceipentManager;
import com.microservices.otmp.notice.mapper.EmailReceipentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Email ReceipentManager业务层处理
 * 
 * @author lovefamily
 * @date 2022-11-10
 */
@Service
public class EmailReceipentManagerImpl implements IEmailReceipentManager
{
    @Autowired
    private EmailReceipentMapper emailReceipentMapper;

    /**
     * 查询Email Receipent
     * 
     * @param id Email Receipent主键
     * @return Email ReceipentDO
     */
    @Override
    public EmailReceipentDO selectEmailReceipentById(Long id)
    {
        return emailReceipentMapper.selectEmailReceipentById(id);
    }

    /**
     * 查询Email Receipent列表
     *
     * @param emailReceipent Email Receipent
     * @return Email ReceipentDO
     */
    @Override
    public List<EmailReceipentDO> selectEmailReceipentList(EmailReceipentDTO emailReceipent)
    {
        return emailReceipentMapper.selectEmailReceipentList(emailReceipent);
    }

    /**
     * 新增Email Receipent
     *
     * @param emailReceipent Email Receipent
     * @return 结果
     */
    @Override
    public int insertEmailReceipent(EmailReceipentDO emailReceipent)
    {
        EmailReceipentDTO emailReceipentDb = new EmailReceipentDTO();
        emailReceipentDb.setJobType(emailReceipent.getJobType());
        emailReceipentDb.setBusinessGroup(emailReceipent.getBusinessGroup());
        emailReceipentDb.setGeoCode(emailReceipent.getGeoCode());
        emailReceipentDb.setDeleteFlag(false);
        List<EmailReceipentDO> emailReceipentsDb = emailReceipentMapper.selectEmailReceipentList(emailReceipentDb);
        if(!CommonUtils.isNullList(emailReceipentsDb)){
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EMAIL_RECIPIENT_CONFIG_EXSIT), DefaultErrorMessage.EMAIL_RECIPIENT_CONFIG_EXSIT.intValue());
        }
        emailReceipent.setCreateTime(DateUtils.getNowDate());
        return emailReceipentMapper.insertEmailReceipent (emailReceipent);
    }

    /**
     * 修改Email Receipent
     *
     * @param emailReceipent  Email Receipent
     * @return 结果
     */
    @Override
    public int updateEmailReceipent(EmailReceipentDO emailReceipent)
    {
        emailReceipent.setUpdateTime(DateUtils.getNowDate());
        return emailReceipentMapper.updateEmailReceipent (emailReceipent);
    }

    /**
     * 批量删除Email Receipent
     * 
     * @param ids 需要删除的Email Receipent主键
     * @return 结果
     */
    @Override
    public int deleteEmailReceipentByIds(Long[] ids)
    {
        return emailReceipentMapper.deleteEmailReceipentByIds(ids);
    }

    /**
     * 删除Email Receipent信息
     * 
     * @param id Email Receipent主键
     * @return 结果
     */
    @Override
    public int deleteEmailReceipentById(Long id)
    {
        return emailReceipentMapper.deleteEmailReceipentById(id);
    }
}
