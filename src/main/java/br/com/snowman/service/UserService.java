package br.com.snowman.service;

import br.com.snowman.model.User;

/**
 * @author luiz
 *
 */
public interface UserService {
	User  currentUserInSession();
}
