package client.login;

import client.base.*;
import client.misc.*;
import client.modelfacade.ModelFacade;
import client.servercommunicator.ServerException;
import client.servercommunicator.ServerFacade;
import shared.model.definitions.CatanColor;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.lang.reflect.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController {

	private IMessageView messageView;
	private IAction loginAction;
	
	/**
	 * LoginController constructor
	 * 
	 * @param view Login view
	 * @param messageView Message view (used to display error messages that occur during the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView) {

		super(view);
		
		this.messageView = messageView;
	}
	
	public ILoginView getLoginView() {
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		
		return loginAction;
	}

	@Override
	public void start() {
		
		getLoginView().showModal();
	}

	@Override
	public void signIn() {
		
		String username = getLoginView().getLoginUsername();
		String password = getLoginView().getLoginPassword();
		try {
			int userID = ServerFacade.get_instance().login(username, password);
			
			// If log in succeeded
			ModelFacade.setUserColor(CatanColor.RED);
			ModelFacade.setUserID(userID);
			ModelFacade.setUserName(username);
			
			getLoginView().closeModal();
			loginAction.execute();
		} catch (ServerException e) {
			messageView.setTitle("Error!");
			messageView.setMessage("Sign in failed.");
			messageView.showModal();
		}
	}

	@Override
	public void register() {
		String username = getLoginView().getRegisterUsername();
		int usernameLen = username.length();
		String password1 = getLoginView().getRegisterPassword();
		int passwordLen = password1.length();
		String password2 = getLoginView().getRegisterPasswordRepeat();
		if(username != null && password1 != null &&
				!(usernameLen < 3 || usernameLen > 7) &&
				!(passwordLen < 5) && password1.equals(password2) && 
				!password1.matches("^.*[^a-zA-Z0-9_-].*$"))
		{
			try {
				int userID = ServerFacade.get_instance().register(username, password1);
				
				// If register succeeded
				ModelFacade.setUserColor(CatanColor.RED);
				ModelFacade.setUserID(userID);
				ModelFacade.setUserName(username);
				
				getLoginView().closeModal();
				loginAction.execute();
			} catch (ServerException e) {
				messageView.setTitle("Error!");
				messageView.setMessage("Register failed.");
				messageView.showModal();
			}
		} else {
			messageView.setTitle("Warning!");
			messageView.setMessage("Invalid username or password.");
			messageView.showModal();
		}
	}
}

