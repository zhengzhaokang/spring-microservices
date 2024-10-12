package com.microservices.otmp.notice.service.impl;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.notice.domain.dto.EmailReceipentDTO;
import com.microservices.otmp.notice.domain.entity.EmailReceipentDO;
import com.microservices.otmp.notice.manager.IEmailReceipentManager;
import com.microservices.otmp.notice.service.IEmailReceipentService;
import com.microservices.otmp.notice.domain.entity.EmailReceipentDO;
import com.microservices.otmp.notice.manager.IEmailReceipentManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Email ReceipentService业务层处理
 * 
 * @author lovefamily
 * @date 2022-11-10
 */
@Service
public class EmailReceipentServiceImpl implements IEmailReceipentService
{

    @Autowired
    private IEmailReceipentManager emailReceipentManager;

    /**
     * 查询Email Receipent
     * 
     * @param id Email Receipent主键
     * @return Email ReceipentDTO
     */
    @Override
    public EmailReceipentDTO selectEmailReceipentById(Long id)
    {
         EmailReceipentDO emailReceipentDO =  emailReceipentManager.selectEmailReceipentById(id);
         EmailReceipentDTO emailReceipentDTO = new EmailReceipentDTO();
         BeanUtils.copyProperties(emailReceipentDO, emailReceipentDTO);
        return emailReceipentDTO;
    }

    /**
     * 查询Email Receipent列表
     *
     * @param emailReceipent Email Receipent
     * @return Email ReceipentDTO
     */
    @Override
    public List<EmailReceipentDTO> selectEmailReceipentList(EmailReceipentDTO emailReceipent)
    {

        List<EmailReceipentDO> emailReceipentDOS =
                    emailReceipentManager.selectEmailReceipentList(emailReceipent);
        List<EmailReceipentDTO> emailReceipentList = new ArrayList<>(emailReceipentDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(emailReceipentDOS, emailReceipentList, EmailReceipentDTO.class);
        return emailReceipentList;

    }

    /**
     * 新增Email Receipent
     *
     * @param emailReceipentDTO Email Receipent
     * @param loginName
     * @return 结果
     */
    @Override
    public int insertEmailReceipent(EmailReceipentDTO emailReceipent, String loginName)
    {
        CommonUtils.stringIsEmpty(emailReceipent.getBusinessGroup(),"BusinessGroup is empty");
        CommonUtils.stringIsEmpty(emailReceipent.getJobType(),"JobType is empty");
        CommonUtils.stringIsEmpty(emailReceipent.getGeoCode(),"GeoCode is empty");
        if(StringUtils.isEmpty(emailReceipent.getEmailReceipent())){
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.EMAIL_RECIPIENT_EMPTY), DefaultErrorMessage.EMAIL_RECIPIENT_EMPTY.intValue());
        }
        emailReceipent.setCreateTime(DateUtils.getNowDate());
        emailReceipent.setCreateBy(loginName);
        emailReceipent.setUpdateTime(DateUtils.getNowDate());
        emailReceipent.setUpdateBy(loginName);
        EmailReceipentDO emailReceipentDO =new  EmailReceipentDO();
        BeanUtils.copyProperties(emailReceipent, emailReceipentDO);
        return emailReceipentManager.insertEmailReceipent(emailReceipentDO);
    }

    /**
     * 修改Email Receipent
     *
     * @param emailReceipentDTO Email Receipent
     * @param loginName
     * @return 结果
     */
    @Override
    public int updateEmailReceipent(EmailReceipentDTO emailReceipent, String loginName)
    {
        emailReceipent.setUpdateTime(DateUtils.getNowDate());
        emailReceipent.setUpdateBy(loginName);
       EmailReceipentDO emailReceipentDO =new  EmailReceipentDO();
        BeanUtils.copyProperties(emailReceipent, emailReceipentDO);
        return emailReceipentManager.updateEmailReceipent(emailReceipentDO);
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
        return emailReceipentManager.deleteEmailReceipentByIds(ids);
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
        return emailReceipentManager.deleteEmailReceipentById(id);
    }
}
