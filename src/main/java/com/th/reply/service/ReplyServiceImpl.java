package com.th.reply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.th.reply.biz.ReplyBiz;
import com.th.reply.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyBiz replyBiz;

	@Override
	public boolean createOneReply(ReplyVO replyVO) {
		return this.replyBiz.insertOneReply(replyVO) > 0;
	}

	@Override
	public boolean deleteOneReply(int boardId, String articleId, String replyId) {
		return this.replyBiz.deleteOneReply(boardId, articleId, replyId) > 0;
	}

	@Override
	public boolean modifyOneReply(ReplyVO replyVO) {
		return this.replyBiz.updateOneReply(replyVO) > 0;
	}

	@Override
	public boolean updateDeleteYn(int boardId, String articleId, String replyId) {
		return this.replyBiz.updateDeleteYn(boardId, articleId, replyId) > 0;
	}
}
