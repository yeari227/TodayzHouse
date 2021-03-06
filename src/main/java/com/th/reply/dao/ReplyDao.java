package com.th.reply.dao;

import java.util.List;
import java.util.Map;

import com.th.reply.vo.ReplyVO;

public interface ReplyDao {

	public int insertOneReply(ReplyVO replyVO);
	
	public List<ReplyVO> selectAllReplies(Map<String, Object> param);
	
	public int deleteAllReplies(Map<String, Object> param);
	
	public int deleteOneReply(Map<String, Object> param);
	public int updateDeleteYn(Map<String, Object> param);
	
	public int updateOneReply(ReplyVO replyVO);
}
