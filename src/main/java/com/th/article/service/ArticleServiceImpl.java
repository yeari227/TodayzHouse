package com.th.article.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.th.article.biz.ArticleBiz;
import com.th.article.vo.ArticleSearchVO;
import com.th.article.vo.ArticleVO;
import com.th.files.biz.FilesBiz;
import com.th.link.biz.LinkBiz;
import com.th.link.vo.LinkVO;
import com.th.recommend.biz.RecommendBiz;
import com.th.reply.biz.ReplyBiz;
import com.th.reply.vo.ReplyVO;
import com.th.report.biz.ReportBiz;
import com.th.sess.biz.SessBiz;
import com.th.sess.vo.SessVO;

import io.github.seccoding.web.pager.explorer.PageExplorer;

@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ArticleBiz articleBiz;
	
	@Autowired
	private FilesBiz filesBiz;

	@Autowired
	private ReplyBiz replyBiz;
	
	@Autowired
	private RecommendBiz recommendBiz;
	
	@Autowired
	private ReportBiz reportBiz;
	
	@Autowired
	private LinkBiz linkBiz;
	
	@Autowired
	private SessBiz sessBiz;
	
	@Override
	public boolean createArticle(ArticleVO articleVO) {
		Map<String, Object> result = this.articleBiz.insertArticle(articleVO);
		int insertArticleResult = (int) result.get("insertArticleResult");
		if(articleVO.getFileVOList() != null) {
			for(int i=0; i<articleVO.getFileVOList().size(); i++) {
				articleVO.getFileVOList().get(i).setArticleId(result.get("articleId").toString());
				this.filesBiz.insertFile(articleVO.getFileVOList().get(i));
			}
		}
		return insertArticleResult > 0;
	}
	
	@Override
	public PageExplorer readAllArticles(ArticleSearchVO articleSearchVO) {
		PageExplorer articleList = this.articleBiz.selectAllArticles(articleSearchVO);
		for(Object article : articleList.getList()) {
			ArticleVO articleVO = (ArticleVO) article;
			articleVO.setReplyList(this.replyBiz.selectAllReplies(articleVO.getBoardId(), articleVO.getArticleId()));
			articleVO.setFileVOList(this.filesBiz.selectAllFiles(articleVO.getBoardId(), articleVO.getArticleId()));
		}
		return articleList;
	}

	@Override
	public ArticleVO readOneArticle(int boardId, String articleId) {
		ArticleVO articleVO = this.articleBiz.selectOneArticle(boardId, articleId);
		articleVO.setReplyList(this.replyBiz.selectAllReplies(boardId, articleId));
		articleVO.setRecommend(this.recommendBiz.selectRecommendCountByArticle(boardId, articleId));
		articleVO.setReport(this.reportBiz.selectReportCountByArticle(boardId, articleId));
		return articleVO;
	}
	
	@Override
	public boolean updateArticle(ArticleVO articleVO) {
		if(articleVO.getFileVOList() != null) {
			for(int i=0; i<articleVO.getFileVOList().size(); i++) {
				this.filesBiz.insertFile(articleVO.getFileVOList().get(i));
			}
		}
		return this.articleBiz.updateArticle(articleVO) > 0;
	}

	@Override
	public boolean deleteArticle(int boardId, String articleId) {
		if(this.filesBiz.selectAllFiles(boardId, articleId) != null) {
			this.filesBiz.deleteFilesByArticle(boardId, articleId);
		}
		
		if(this.replyBiz.selectAllReplies(boardId, articleId) != null) {
			this.replyBiz.deleteAllReplies(boardId, articleId);
		}
		
		this.recommendBiz.deleteAllRecommendsByArticle(boardId, articleId);
		this.reportBiz.deleteAllReportsByArticle(boardId, articleId);
		
		return this.articleBiz.deleteOneArticle(boardId, articleId) > 0;
	}

	@Override
	public boolean updateDeleteYN(int boardId, String articleId) {
		return this.articleBiz.updateDeleteYN(boardId, articleId) > 0;
	}

	@Override
	public boolean isRecommend(int boardId, String articleId, String email) {
		return this.recommendBiz.selectRecommendByArticle(boardId, articleId, email) > 0;
	}

	@Override
	public boolean isReport(int boardId, String articleId, String email) {
		return this.reportBiz.selectReportByArticle(boardId, articleId, email) > 0;
	}

	@Override
	public List<LinkVO> readLinkList(String fileId) {
		return this.linkBiz.selectAllLinksByArticle(fileId);
	}

	@Override
	public boolean updateViewCount(int boardId, String articleId) {
		return this.articleBiz.updateViewCount(boardId, articleId) > 0;
	}
	
	@Override
	public boolean isWriterLogin(String email, String name) {
		return this.sessBiz.selectMember(new SessVO(email, name)) > 0;
	}

	@Override
	public List<ArticleVO> readBestArticles(int boardId) {
		return this.articleBiz.selectBestArticles(boardId);
	}
}
