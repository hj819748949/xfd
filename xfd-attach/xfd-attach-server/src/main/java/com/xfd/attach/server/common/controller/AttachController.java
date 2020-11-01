package com.xfd.attach.server.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xfd.attach.client.req.AttachUploadReq;
import com.xfd.attach.client.rpc.AttachClient;
import com.xfd.attach.client.vo.AttachInfoVO;
import com.xfd.common.dynamic.controller.AbstractController;
import com.xfd.comon.statics.entity.Msg;
import com.xfd.comon.statics.utils.CommonUtil;
import com.xfd.comon.statics.utils.PathUtil;
import com.xfd.comon.statics.utils.StreamUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


/**
 * 附件Controller
 * @author xfd
 *
 * 2020年10月31日
 */
@Slf4j
@Api("附件")
@Controller
@Scope("prototype")
@RequestMapping("/attach")
@CrossOrigin(origins={ "*" })
public class AttachController extends AbstractController
{
	/** 附件服务 */
	@Reference
	private AttachClient attachClient;
	
	@ApiOperation("通用单文件上传")
	@RequestMapping("/upload")
	@ResponseBody
	public Msg upload(MultipartFile file)
	{
		try 
		{
			if (CommonUtil.isEmpty(file)) 
			{
				log.error("上传的文件为空, 请检查.");
				return Msg.getError("上传的文件为空, 请检查");
			}
			AttachInfoVO attachInfo = doUpload(CommonUtil.getUid(), file);
			// 处理路径
			attachInfo.setStoragePath(PathUtil.eraseHttpSeparator(attachInfo.getStoragePath()) + "?id=" + attachInfo.getId());
			return Msg.getSuccess(attachInfo);
		} catch (Exception e) 
		{
			log.error(e.getMessage(), e);
			return Msg.getError("上传失败.");
		}
	}
	
	@ApiOperation("通用多文件上传")
	@RequestMapping("/mupload")
	@ResponseBody
	public Msg mupload(MultipartFile[] file) 
	{
		try 
		{
			if (CommonUtil.isEmpty(file)) 
			{
				log.error("上传的文件为空, 请检查.");
				return Msg.getError("上传的文件为空, 请检查");
			}
			String groupId = CommonUtil.getUid();
			List<AttachInfoVO> attachInfoList = new ArrayList<AttachInfoVO>();
			for (MultipartFile f : file) 
			{
				AttachInfoVO attachInfo = doUpload(groupId, f);
				attachInfoList.add(attachInfo);
			}
			// 执行遍历处理
			attachInfoList.forEach(attachInfo -> {
				attachInfo.setStoragePath(PathUtil.eraseHttpSeparator(attachInfo.getStoragePath()) + "?id=" + attachInfo.getId());
			});
			// 处理结果集
			return Msg.getSuccess(attachInfoList);
		} catch (Exception e) 
		{
			log.error(e.getMessage(), e);
			return Msg.getError("上传失败.");
		}
	}
	
	/**
	 * 执行附件下载
	 * @param id
	 */
	@RequestMapping("/download")
	public void download(@RequestParam("id") Long id) 
	{
		try 
		{
			AttachInfoVO attachInfoVO = attachClient.findAttachInfoById(id);
			byte[] fileData = attachClient.findAttachData(id);
			super.download(fileData, attachInfoVO.getFileName());
		} catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 执行单个文件上传
	 * @param groupId
	 * @param file
	 * @return
	 * @throws Exception
	 */
	protected AttachInfoVO doUpload(String groupId, MultipartFile file)
			throws Exception 
	{
		AttachUploadReq req = new AttachUploadReq();
		req.setFileName(file.getOriginalFilename());
		req.setMimeType(file.getContentType());
		req.setFileSize(file.getSize());
		req.setGroupId(groupId);
		req.setFileData(StreamUtil.getStreamBytes(file.getInputStream()));
		return attachClient.upload(req);
	}
}
