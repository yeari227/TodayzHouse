package com.th.article.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.th.article.dao.ArticleDao;
import com.th.article.vo.ArticleSearchVO;
import com.th.article.vo.ArticleVO;

import io.github.seccoding.web.pager.Pager;
import io.github.seccoding.web.pager.PagerFactory;
import io.github.seccoding.web.pager.explorer.ClassicPageExplorer;
import io.github.seccoding.web.pager.explorer.PageExplorer;

@Component
public class ArticleBizImpl implements ArticleBiz {

	@Autowired
	private ArticleDao articleDao;

	@Override
	public Map<String, Object>  insertArticle(ArticleVO articleVO) {
		return this.articleDao.insertArticle(articleVO);
	}

	@Override
	public PageExplorer selectAllArticles(ArticleSearchVO articleSearchVO) {
		int totalCount = this.articleDao.selectAllArticlesCount(articleSearchVO);
		
		Pager pager = PagerFactory.getPager(Pager.ORACLE, articleSearchVO.getPageNo()+"");
		pager.setTotalArticleCount(totalCount);
		
		PageExplorer pageExplorer = pager.makePageExplorer(ClassicPageExplorer.class, articleSearchVO);
		
		pageExplorer.setList(this.articleDao.selectAllArticles(articleSearchVO));
		
		return pageExplorer;
	}

	@Override
	public ArticleVO selectOneArticle(int boardId, String articleId) {
		Map<String, Object> param = new HashMap<>();
		param.put("boardId", boardId);
		param.put("articleId", articleId);

		return this.articleDao.selectOneArticle(param);
	}

	@Override
	public int updateArticle(ArticleVO articleVO) {
		return this.articleDao.updateArticle(articleVO);
	}

	@Override
	public int deleteOneArticle(int boardId, String articleId) {
		Map<String, Object> param = new HashMap<>();
		param.put("boardId", boardId);
		param.put("articleId", articleId);
		
		return this.articleDao.deleteOneArticle(param);
	}

	@Override
	public int updateDeleteYN(int boardId, String articleId) {
		Map<String, Object> param = new HashMap<>();
		param.put("boardId", boardId);
		param.put("articleId", articleId);
		
		return this.articleDao.updateDeleteYN(param);
	}
	
	@Override
	public int updateViewCount(int boardId, String articleId) {
		Map<String, Object> param = new HashMap<>();
		param.put("boardId", boardId);
		param.put("articleId", articleId);
		
		return this.articleDao.updateViewCount(param);
	}

	@Override
	public int updateRecommend(int boardId, String articleId, int count) {
		Map<String, Object> param = new HashMap<>();
		param.put("boardId", boardId);
		param.put("articleId", articleId);
		param.put("count", count);
		
		return this.articleDao.updateRecommed(param);
	}

	@Override
	public int updateReport(int boardId, String articleId, int count) {
		Map<String, Object> param = new HashMap<>();
		param.put("boardId", boardId);
		param.put("articleId", articleId);
		param.put("count", count);
		
		return this.articleDao.updateReport(param);
	}

	@Override
	public List<ArticleVO> selectBestArticles(int boardId) {
		return this.articleDao.selectBestArticles(boardId);
	}

	@Override
	public List<ArticleVO> selectAllArticlesByEmail(String email) {
		return this.articleDao.selectAllArticlesByEmail(email);
	}

	@Override
	public List<ArticleVO> selectArticlesByAdmin() {
		return this.articleDao.selectArticlesByAdmin();
	}

	@Override
	public int deleteArticleByAdmin(int boardId, String articleId) {
		Map<String, Object> param = new HashMap<>();
		param.put("boardId", boardId);
		param.put("articleId", articleId);
		
		return this.articleDao.deleteArticleByAdmin(param);
	}
}
