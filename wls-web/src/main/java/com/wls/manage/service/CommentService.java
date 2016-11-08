package com.wls.manage.service;

import com.github.pagehelper.Page;
import com.wls.manage.dto.CommentDTO;
import com.wls.manage.dto.PersonalCommentDTO;
import com.wls.manage.entity.CommentEntity;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Author: qiunian.sun Date: qiunian.sun(2016-04-28 20:34)
 */
public interface CommentService {

	List<CommentEntity> findLastNComment(@RequestParam int rdcID, @RequestParam int npoint);

	List<CommentDTO> findCommentsRdcID(@RequestParam int rdcID, @RequestParam int npoint);

	public void insertComment(CommentEntity comment);

	Page<PersonalCommentDTO> findCommentsUserID(int userID);
}