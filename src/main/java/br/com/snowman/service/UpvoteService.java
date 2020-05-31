package br.com.snowman.service;

/**
 * @author luiz
 *
 */
public interface UpvoteService {
	void saveUserVote(long id);
	int  getCurrenteUpvote (long id);
}
