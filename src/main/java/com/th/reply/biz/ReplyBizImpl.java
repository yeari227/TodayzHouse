package com.th.reply.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.th.reply.dao.ReplyDao;
import com.th.reply.vo.ReplyVO;

@Component
public class ReplyBizImpl implements ReplyBiz {

	@Autowired
	private ReplyDao replyDao;

	@Override
	public int insertOneReply(ReplyVO replyVO) {
		return this.replyDao.insertOneReply(replyVO);
	}

	@Override
	public List<ReplyVO> selectAllReplies(int boardId, String articleId) {
		Map<String, Object> param = new HashMap<>();
		param.put("boardId", boardId);
		param.put("articleId", articleId);
		
		return this.replyDao.selectAllReplies(param);
	}
}