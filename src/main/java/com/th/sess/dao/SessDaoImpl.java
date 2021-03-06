package com.th.sess.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.th.member.vo.MemberVO;
import com.th.sess.vo.SessVO;

@Repository
public class SessDaoImpl extends SqlSessionDaoSupport implements SessDao {

	private Logger logger = LoggerFactory.getLogger(SessDaoImpl.class);
	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		logger.debug("Initiate MyBatis");
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public int insertMember(SessVO sessVO) {
		return this.getSqlSession().insert("SessDao.insertMember", sessVO);
	}
	
	@Override
	public int deleteMember(SessVO sessVO) {
		return this.getSqlSession().delete("SessDao.deleteMember", sessVO);
	}

	@Override
	public int selectMember(SessVO sessVO) {
		return this.getSqlSession().selectOne("SessDao.selectMember", sessVO);
	}

	@Override
	public int updateMember(MemberVO memberVO) {
		return this.getSqlSession().update("SessDao.updateMember", memberVO);
	}
}
