package com.th.member.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.th.common.session.Session;
import com.th.common.util.SHA256Util;
import com.th.member.dao.MemberDao;
import com.th.member.vo.MemberVO;

@Component
public class MemberBizImpl implements MemberBiz {

	@Autowired
	private MemberDao memberDao;
	
	private String getHashedPassword(String salt, String password) {
		return SHA256Util.getEncrypt(password, salt);
	}
	
	@Override
	public boolean registMember(MemberVO memberVO) {
		String salt = SHA256Util.generateSalt();
		String password = this.getHashedPassword(salt, memberVO.getPassword());

		memberVO.setPassword(password);
		memberVO.setSalt(salt);
		
		return this.memberDao.insertMember(memberVO) > 0;
	}

	@Override
	public boolean isDuplicateEmail(String email) {
		return this.memberDao.isDuplicateEmail(email) > 0;
	}
	
	@Override
	public boolean loginMember(MemberVO memberVO, HttpSession session) {
		String salt = this.memberDao.getSaltById(memberVO.getEmail());
		if(salt == null) {
			return false;
		}
		String password = this.getHashedPassword(salt, memberVO.getPassword());

		memberVO.setPassword(password);

		MemberVO loginMemberVO = this.memberDao.selectOneMember(memberVO);

		if (loginMemberVO != null) {
			session.setAttribute(Session.MEMBER, loginMemberVO);
		}
		return loginMemberVO != null;
	}
	
	@Override
	public MemberVO loginMember(MemberVO memberVO) {
		String salt = this.memberDao.getSaltById(memberVO.getEmail());
		String password = this.getHashedPassword(salt, memberVO.getPassword());
		
		memberVO.setPassword(password);
		
		MemberVO loginMemberVO = this.memberDao.selectOneMember(memberVO);
		
		return loginMemberVO;
	}

	@Override
	public int updatePoint(String email, int point) {
		Map<String, Object> param = new HashMap<>();
		param.put("email", email);
		param.put("point", point);
		
		return this.memberDao.updatePoint(param);
	}

	@Override
	public boolean isBlockUser(String email) {
		return this.memberDao.isBlockUser(email) > 0;
	}

	@Override
	public boolean unblockUser(String email) {
		return this.memberDao.unblockUser(email);
	}

	@Override
	public boolean blockUser(String email) {
		return this.memberDao.blockUser(email);
	}

	@Override
	public boolean increaseLoginFailCount(String email) {
		return this.memberDao.increaseLoginFailCount(email);
	}

	@Override
	public int updateMember(MemberVO memberVO) {
		return this.memberDao.updateMember(memberVO);
	}
	
	@Override
	public int updateChatOk(String email, int chatOk) {
		Map<String, Object> param = new HashMap<>();
		param.put("email", email);
		param.put("chatOk", chatOk);
		
		return this.memberDao.updateChatOk(param);
	}

	@Override
	public List<MemberVO> selectMembersByAdmin() {
		return this.memberDao.selectMembersByAdmin();
	}

	@Override
	public int deleteMemberByAdmin(String email) {
		return this.memberDao.deleteMemberByAdmin(email);
	}

	@Override
	public int updateDeleteArtCount(String email) {
		return this.memberDao.updateDeleteArtCount(email);
	}
}
